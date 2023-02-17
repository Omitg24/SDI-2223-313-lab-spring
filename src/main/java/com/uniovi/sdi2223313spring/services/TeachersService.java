package com.uniovi.sdi2223313spring.services;

import com.uniovi.sdi2223313spring.entities.Teacher;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class TeachersService {
    private List<Teacher> teachersList = new LinkedList<>();

    @PostConstruct
    public void init() {
        teachersList.add(new Teacher(1L, "123456789A", "Enol", "García González", "Inteligencia Artificial"));
        teachersList.add(new Teacher(2L, "987654321B", "María del Carmen", "Suárez Torrente", "Lenguajes y Sistemas Informáticos"));
    }
    public List<Teacher> getTeachers() {
        return teachersList;
    }
    public Teacher getTeacher(Long id) {
        return teachersList.stream()
                .filter(Teacher -> Teacher.getId().equals(id)).findFirst().get();
    }
    public void addTeacher(Teacher Teacher) {
        // Si en Id es null le asignamos el ultimo + 1 de la lista
        if (Teacher.getId() == null) {
            Teacher.setId(teachersList.get(teachersList.size() - 1).getId() + 1);
        }
        teachersList.add(Teacher);
    }
    public void deleteTeacher(Long id) {
        teachersList.removeIf(Teacher -> Teacher.getId().equals(id));
    }
}
