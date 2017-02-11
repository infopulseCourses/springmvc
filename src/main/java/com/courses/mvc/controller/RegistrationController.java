package com.courses.mvc.controller;

import com.courses.mvc.dto.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Stepan
 */
@Controller
public class RegistrationController {

    @RequestMapping(value = "/registration", method = RequestMethod.GET, name = "getRegistration")
    public ModelAndView getRegistration(@ModelAttribute("user") @Validated UserDTO user){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("formHandler","/registration");
        modelAndView.setViewName("registration");

        return modelAndView;
    }

}
