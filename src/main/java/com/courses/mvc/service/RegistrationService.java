package com.courses.mvc.service;

import com.courses.mvc.domain.Role;
import com.courses.mvc.domain.User;
import com.courses.mvc.domain.UserRole;
import com.courses.mvc.dto.UserDTO;
import com.courses.mvc.exception.UserServiceException;
import com.courses.mvc.repository.UserRepository;
import com.courses.mvc.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author Stepan
 */
@Service
public class RegistrationService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Transactional
    public void createUser(UserDTO newUser) {
        try{
            UserRole userRole = new UserRole();
            userRole.setRole(Role.USER);
            userRoleRepository.save(userRole);

            User user = convertUserDTOToUser(newUser);
            user.setRole(userRole);
            userRepository.save(user);
        }catch (JpaSystemException e){
            throw new UserServiceException();
        }
    }

    private User convertUserDTOToUser(UserDTO newUser) {
        User u = new User();
        u.setName(newUser.getName());
        u.setLogin(newUser.getLogin());
        u.setPassword(newUser.getPassword());
        return u;
    }

}
