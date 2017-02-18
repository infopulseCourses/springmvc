package com.courses.mvc.aspect;

import com.courses.mvc.exception.UserServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author Stepan
 */
@ControllerAdvice
public class UserExceptionAdvice {

    @ExceptionHandler(value = UserServiceException.class)
    public String getRegistrationError(UserServiceException e) {
        ServletRequestAttributes attributes =(ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession(false);
        session.setAttribute("errorMessage", e.getMessage());

        return "redirect:registration";
    }
}
