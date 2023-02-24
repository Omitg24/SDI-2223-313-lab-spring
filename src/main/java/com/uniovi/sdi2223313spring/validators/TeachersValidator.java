package com.uniovi.sdi2223313spring.validators;

import com.uniovi.sdi2223313spring.entities.Teacher;
import com.uniovi.sdi2223313spring.services.TeachersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class TeachersValidator implements Validator {

    @Autowired
    private TeachersService teachersService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Teacher.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Teacher teacher = (Teacher) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dni", "Error.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nombre", "Error.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "apellidos", "Error.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "categoria", "Error.empty");
        if (teacher.getDni().length() != 9 || !Character.isLetter(teacher.getDni().charAt(teacher.getDni().length() - 1))) {
            errors.rejectValue("dni", "Error.teacher.dni.format");
        }
        if (teachersService.getTeacherByDni(teacher.getDni()) != null) {
            errors.rejectValue("dni", "Error.teacher.dni.duplicate");
        }
    }
}
