package com.uniovi.sdi2223313spring.services;

import com.uniovi.sdi2223313spring.entities.Mark;
import com.uniovi.sdi2223313spring.entities.User;
import com.uniovi.sdi2223313spring.repositories.MarksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class MarksService {

    @Autowired
    private MarksRepository marksRepository;

    /* Inyección de dependencias basada en constructor*/
    private final HttpSession httpSession;

    @Autowired
    public MarksService(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public List<Mark> getMarks() {
        List<Mark> marks = new ArrayList<>();
        marksRepository.findAll().forEach(marks::add);
        return marks;
    }

    public Mark getMark(Long id) {
        return marksRepository.findById(id).get();
    }

    public void addMark(Mark mark) {
        // Si en Id es null le asignamos el último + 1 de la lista
        marksRepository.save(mark);
    }

    public void deleteMark(Long id) {
        marksRepository.deleteById(id);
    }

    public void setMarkResend(boolean revised, Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String dni = auth.getName();
        Mark mark = marksRepository.findById(id).get();
        if (mark.getUser().getDni().equals(dni)) {
            marksRepository.updateResend(revised, id);
        }
    }

    public List<Mark> getMarksForUser(User user) {
        List<Mark> marks = new ArrayList<>();
        if (user.getRole().equals("ROLE_STUDENT")) {
            marks = marksRepository.findAllByUser(user);}
        if (user.getRole().equals("ROLE_PROFESSOR")) {
            marks = getMarks(); }
        return marks;
    }
}
