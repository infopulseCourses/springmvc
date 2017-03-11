package com.courses.mvc.controller.util;

import com.courses.mvc.domain.Role;
import com.courses.mvc.dto.UserDTO;
import com.courses.mvc.exception.AccessDeniedException;
import com.courses.mvc.exception.LoginException;
import com.courses.mvc.exception.NoSessionException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * @author Stepan
 */

public class AccessUtil {

    public static UserDTO verifyUserRole(Role role){
        ServletRequestAttributes attributes =(ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession();
        if(session == null){
            throw new NoSessionException();
        }
        UserDTO userDTO  = (UserDTO) session.getAttribute("user");
        if(userDTO == null){
            throw new LoginException();
        }
        if(userDTO.getRole() != role){
            throw new AccessDeniedException();
        }
        return userDTO;
    }
}
