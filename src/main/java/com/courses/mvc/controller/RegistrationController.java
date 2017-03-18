package com.courses.mvc.controller;

import com.courses.mvc.dto.UserDTO;
import com.courses.mvc.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * @author Stepan
 */
@Controller
public class RegistrationController {

    @Autowired
    RegistrationService registrationService;

    @RequestMapping(value = "/registration", method = RequestMethod.GET, name = "getRegistration")
    public ModelAndView getRegistration(@ModelAttribute("user") @Valid UserDTO user) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("registration");

        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView postRegistration(@ModelAttribute("user") @Valid UserDTO user) {
        ModelAndView modelAndView = new ModelAndView();

        registrationService.createUser(user);
        modelAndView.setViewName("login");

        return modelAndView;
    }
}
