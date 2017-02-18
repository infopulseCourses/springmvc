package com.courses.mvc.service;

import com.courses.mvc.domain.User;
import com.courses.mvc.dto.UserDTO;
import com.courses.mvc.repository.UserRepository;
import com.courses.mvc.service.util.UserConverterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Stepan
 */
@Service
public class LoginService {

    @Autowired
    UserRepository repository;

    public UserDTO verifyLogin(String login, String password) {
        User user = repository.findUserByLogin(login);
        if (user != null && user.getPassword().equals(password)) {
            return UserConverterUtil.convertUserToUserDTO(user);
        }
        return null;
    }
}
