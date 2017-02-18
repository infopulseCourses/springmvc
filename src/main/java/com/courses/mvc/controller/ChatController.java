package com.courses.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Stepan
 */
@Controller
public class ChatController {

    @RequestMapping(value = "/chat", method = RequestMethod.GET)
    public ModelAndView getAdmin(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("chat");
        return modelAndView;
    }
}
