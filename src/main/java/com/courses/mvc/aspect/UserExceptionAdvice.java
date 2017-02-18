package com.courses.mvc.aspect;

import com.courses.mvc.exception.UserServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Stepan
 */
@ControllerAdvice
public class UserExceptionAdvice {

    @ExceptionHandler(value = UserServiceException.class)
    public ModelAndView getRegistrationError() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        modelAndView.addObject("errorMessage", "User already exist");
        return modelAndView;
    }
}
