package com.courses.mvc.aspect;

import com.courses.mvc.dto.GeneralResponseDTO;
import com.courses.mvc.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

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

    @ExceptionHandler(value = {AccessDeniedException.class, NoSessionException.class, LoginException.class})
    public ModelAndView getAccessError(RuntimeException exception){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        ServletRequestAttributes attributes =(ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession(false);

        if(exception instanceof AccessDeniedException)
            session.setAttribute("error","Access Denied");
        if(exception instanceof NoSessionException)
            session.setAttribute("error","No active session");
        if(exception instanceof LoginException)
            session.setAttribute("error","Login exception");

        return modelAndView;
    }

    @ExceptionHandler(value = RestException.class)
    public ResponseEntity<GeneralResponseDTO> getRestError(){
        GeneralResponseDTO fail = new GeneralResponseDTO();
        fail.operationFailed();
        return new ResponseEntity<>(fail, HttpStatus.OK);
    }

}
