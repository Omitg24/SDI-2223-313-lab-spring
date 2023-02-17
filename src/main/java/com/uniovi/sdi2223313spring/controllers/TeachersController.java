package com.uniovi.sdi2223313spring.controllers;

import com.uniovi.sdi2223313spring.entities.Teacher;
import com.uniovi.sdi2223313spring.services.TeachersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TeachersController {

    @Autowired
    private TeachersService teachersService;

    @RequestMapping("/teacher/list")
    public String getList(Model model) {
        model.addAttribute("teacherList", teachersService.getTeachers());
        return "teacher/list";
    }

    @RequestMapping(value = "/teacher/add", method = RequestMethod.POST)
    public String setTeacher(@ModelAttribute Teacher teacher) {
        teachersService.addTeacher(teacher);
        return "redirect:/teacher/list";
    }

    @RequestMapping(value = "/teacher/add")
    public String getTeacher() {
        return "teacher/add";
    }

    @RequestMapping("/teacher/details/{id}")
    public String getDetail(Model model, @PathVariable Long id) {
        model.addAttribute("teacher", teachersService.getTeacher(id));
        return "teacher/details";
    }

    @RequestMapping("/teacher/delete/{id}")
    public String deleteteacher(@PathVariable Long id) {
        teachersService.deleteTeacher(id);
        return "redirect:/teacher/list";
    }

    @RequestMapping(value="/teacher/edit/{id}", method=RequestMethod.POST)
    public String setEdit(@ModelAttribute Teacher teacher, @PathVariable Long id){
        teacher.setId(id);
        teachersService.addTeacher(teacher);
        return "redirect:/teacher/details/"+id;
    }

    @RequestMapping(value = "/teacher/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        model.addAttribute("teacher", teachersService.getTeacher(id));
        return "teacher/edit";
    }
}
