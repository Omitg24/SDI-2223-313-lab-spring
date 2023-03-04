package com.uniovi.sdi2223313spring.services;

import com.uniovi.sdi2223313spring.entities.Teacher;
import com.uniovi.sdi2223313spring.repositories.TeachersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TeachersService {
    @Autowired
    private TeachersRepository teachersRepository;

    public Page<Teacher> getTeachers(Pageable pageable) {
        Page<Teacher> teachers = teachersRepository.findAll(pageable);
        return teachers;
    }

    public Teacher getTeacher(Long id) {
        return teachersRepository.findById(id).get();
    }

    public void addTeacher(Teacher teacher) {
        // Si en Id es null le asignamos el último + 1 de la lista
        teacher.setDni(teacher.getDni().toLowerCase());
        teachersRepository.save(teacher);
    }

    public Teacher getTeacherByDni(String dni) {
        return teachersRepository.findByDni(dni);
    }

    public void deleteTeacher(Long id) {
        teachersRepository.deleteById(id);
    }
}
