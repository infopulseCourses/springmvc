package com.courses.mvc.controller;

import com.courses.mvc.domain.Role;
import com.courses.mvc.dto.UserDTO;
import com.courses.mvc.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * @author Stepan
 */
@Controller
public class LoginController {

    @Autowired
    LoginService loginService;

    @RequestMapping(value = "/login", method = RequestMethod.GET, name = "getLogin")
    public ModelAndView getLogin() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("formHandler", "/login");
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam(value = "login") String login,
                        @RequestParam(value = "password") String password, HttpSession session) {
        UserDTO userDTO = loginService.verifyLogin(login, password);
        if (userDTO == null) {
            session.setAttribute("errorMessage", "User doesn't exist");
            return "redirect:login";
        }
        session.setAttribute("user", userDTO);
        if (userDTO.getRole() == Role.ADMIN) {
            return "redirect:admin";
        }
        session.setAttribute("sockPath","/sock");
        return "redirect:chat";
    }
}
