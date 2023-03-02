package com.uniovi.sdi2223313spring.controllers;

import com.uniovi.sdi2223313spring.entities.Mark;
import com.uniovi.sdi2223313spring.entities.User;
import com.uniovi.sdi2223313spring.services.MarksService;
import com.uniovi.sdi2223313spring.services.UsersService;
import com.uniovi.sdi2223313spring.validators.MarksValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

@Controller
public class MarksController {

    @Autowired
    private HttpSession httpSession;

    @Autowired //Inyectar el servicio
    private MarksService marksService;

    // Inyectamos el servicio
    @Autowired
    private UsersService usersService;

    @Autowired
    private MarksValidator marksValidator;

    @RequestMapping("/mark/list")
    public String getList(Model model, Principal principal,
                          @RequestParam(value="", required = false) String searchText){
        String dni = principal.getName(); // DNI es el name de la autenticación
        User user = usersService.getUserByDni(dni);
        if (searchText != null && !searchText.isEmpty()) {
            model.addAttribute("markList", marksService.searchMarksByDescriptionAndNameForUser(searchText, user));
        } else {
            model.addAttribute("markList", marksService.getMarksForUser(user));
        }
        return "mark/list";
    }

    @RequestMapping(value = "/mark/add", method = RequestMethod.POST)
    public String setMark(@Validated Mark mark, BindingResult result, Model model) {
        marksValidator.validate(mark, result);
        if (result.hasErrors()) {
            model.addAttribute("usersList", usersService.getUsers());
            model.addAttribute("mark", mark);
            return "mark/add";
        }

        marksService.addMark(mark);
        return "redirect:/mark/list";
    }

    //Modificamos los siguientes metodos
    @RequestMapping(value = "/mark/add")
    public String getMark(Model model) {
        model.addAttribute("usersList", usersService.getUsers());
        model.addAttribute("mark", new Mark());
        return "mark/add";
    }

    @RequestMapping("/mark/details/{id}")
    public String getDetail(Model model, @PathVariable Long id) {
        model.addAttribute("mark", marksService.getMark(id));
        return "mark/details";
    }

    @RequestMapping("/mark/delete/{id}")
    public String deleteMark(@PathVariable Long id) {
        marksService.deleteMark(id);
        return "redirect:/mark/list";
    }

    @RequestMapping(value = "/mark/edit/{id}")
    public String getEdit(Model model, @PathVariable Long id) {
        model.addAttribute("mark", marksService.getMark(id));
        model.addAttribute("usersList", usersService.getUsers());
        return "mark/edit";
    }

    @RequestMapping(value = "/mark/edit/{id}", method = RequestMethod.POST)
    public String setEdit(@Validated Mark mark, BindingResult result, @PathVariable Long id, Model model) {
        marksValidator.validate(mark, result);
        if (result.hasErrors()) {
            model.addAttribute("usersList", usersService.getUsers());
            model.addAttribute("mark", mark);
            return "mark/edit";
        }
        Mark originalMark = marksService.getMark(id);
        // modificar solo score y description
        originalMark.setScore(mark.getScore());
        originalMark.setDescription(mark.getDescription());
        marksService.addMark(originalMark);
        return "redirect:/mark/details/" + id;
    }

    @RequestMapping("/mark/list/update")
    public String updateList(Model model, Principal principal) {
        String dni = principal.getName(); // DNI es el name de la autenticación
        User user = usersService.getUserByDni(dni);
        model.addAttribute("markList", marksService.getMarksForUser(user));
        return "mark/list :: tableMarks";
    }

    @RequestMapping(value = "/mark/{id}/resend", method = RequestMethod.GET)
    public String setResendTrue(@PathVariable Long id) {
        marksService.setMarkResend(true, id);
        return "redirect:/mark/list";
    }
    @RequestMapping(value = "/mark/{id}/noresend", method = RequestMethod.GET)
    public String setResendFalse(@PathVariable Long id) {
        marksService.setMarkResend(false, id);
        return "redirect:/mark/list";
    }
}
