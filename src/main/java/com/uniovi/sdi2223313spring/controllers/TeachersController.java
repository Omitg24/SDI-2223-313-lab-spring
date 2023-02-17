package com.uniovi.sdi2223313spring.controllers;

import com.uniovi.sdi2223313spring.entities.Teacher;
import com.uniovi.sdi2223313spring.services.TeachersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeachersController {

    @Autowired
    private TeachersService teachersService;

    @RequestMapping("/teacher/list")
    public String getList() {
        return teachersService.getTeachers().toString();
    }

    @RequestMapping(value = "/teacher/add", method = RequestMethod.POST)
    public String setTeacher(@ModelAttribute Teacher teacher) {
        teachersService.addTeacher(teacher);
        return teacher.toString();
    }

    @RequestMapping("/teacher/details/{id}")
    public String getDetail(@PathVariable Long id) {
        return teachersService.getTeacher(id).toString();
    }

    @RequestMapping("/teacher/delete/{id}")
    public String deleteteacher(@PathVariable Long id) {
        teachersService.deleteTeacher(id);
        return "Teacher deleted";
    }

    @RequestMapping(value="/teacher/edit/{id}", method=RequestMethod.POST)
    public String setEdit(@ModelAttribute Teacher teacher, @PathVariable Long id){
        teacher.setId(id);
        teachersService.addTeacher(teacher);
        return "Teacher has been edited";
    }

    @RequestMapping(value = "/teacher/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        model.addAttribute("teacher", teachersService.getTeacher(id));
        return "Teacher edited";
    }
}