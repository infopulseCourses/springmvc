package com.courses.mvc.service.util;

import com.courses.mvc.domain.User;
import com.courses.mvc.dto.UserDTO;

/**
 * @author Stepan
 */
public class UserConverterUtil {

    public static User convertUserDTOToUser(UserDTO newUser) {
        User u = new User();
        u.setName(newUser.getName());
        u.setLogin(newUser.getLogin());
        u.setPassword(newUser.getPassword());
        return u;
    }

    public static UserDTO convertUserToUserDTO(User newUser) {
        UserDTO u = new UserDTO();
        u.setName(newUser.getName());
        u.setLogin(newUser.getLogin());
        u.setRole(newUser.getRole().getRole());
        u.setPassword(newUser.getPassword());
        u.setId(newUser.getId());

        return u;
    }

}
