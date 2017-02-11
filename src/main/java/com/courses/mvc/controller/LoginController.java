package com.courses.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Stepan
 */
@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = RequestMethod.GET, name = "getLogin")
    public ModelAndView getLogin(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("formHandler","/login");
        modelAndView.setViewName("login");
        return modelAndView;
    }
}
