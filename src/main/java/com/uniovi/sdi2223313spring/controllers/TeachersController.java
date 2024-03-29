package com.uniovi.sdi2223313spring.controllers;

import com.uniovi.sdi2223313spring.entities.Teacher;
import com.uniovi.sdi2223313spring.services.TeachersService;
import com.uniovi.sdi2223313spring.validators.TeachersValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TeachersController {

    @Autowired
    private TeachersService teachersService;

    @Autowired
    private TeachersValidator teachersValidator;

    @RequestMapping("/teacher/list")
    public String getList(Model model, Pageable pageable) {
        Page<Teacher> teachers = teachersService.getTeachers(pageable);
        model.addAttribute("teacherList", teachers.getContent());
        model.addAttribute("page", teachers);
        return "teacher/list";
    }

    @RequestMapping(value = "/teacher/add", method = RequestMethod.POST)
    public String setTeacher(@Validated Teacher teacher, BindingResult result, Model model) {
        teachersValidator.validate(teacher, result);
        if (result.hasErrors()) {
            model.addAttribute("teacher", teacher);
            return "/teacher/add";
        }
        teachersService.addTeacher(teacher);
        return "redirect:/teacher/list";
    }

    @RequestMapping(value = "/teacher/add")
    public String getTeacher(Model model) {
        model.addAttribute("teacher", new Teacher());
        return "teacher/add";
    }

    @RequestMapping("/teacher/details/{id}")
    public String getDetail(Model model, @PathVariable Long id) {
        model.addAttribute("teacher", teachersService.getTeacher(id));
        return "teacher/details";
    }

    @RequestMapping("/teacher/delete/{id}")
    public String deleteTeacher(@PathVariable Long id) {
        teachersService.deleteTeacher(id);
        return "redirect:/teacher/list";
    }

    @RequestMapping(value = "/teacher/edit/{id}", method = RequestMethod.POST)
    public String setEdit(@Validated Teacher teacher, BindingResult result, @PathVariable Long id, Model model) {
        teachersValidator.validate(teacher, result);
        if (result.hasErrors()) {
            model.addAttribute("teacher", teacher);
            return "/teacher/edit";
        }
        teacher.setId(id);
        teachersService.addTeacher(teacher);
        return "redirect:/teacher/details/" + id;
    }

    @RequestMapping(value = "/teacher/edit/{teacher}")
    public String getEdit(Model model, @PathVariable Teacher teacher) {
        model.addAttribute("teacher", teacher);
        return "teacher/edit";
    }
}
