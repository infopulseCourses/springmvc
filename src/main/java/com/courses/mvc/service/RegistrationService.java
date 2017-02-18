package com.courses.mvc.service;

import com.courses.mvc.domain.Role;
import com.courses.mvc.domain.User;
import com.courses.mvc.domain.UserRole;
import com.courses.mvc.dto.UserDTO;
import com.courses.mvc.exception.UserServiceException;
import com.courses.mvc.repository.UserRepository;
import com.courses.mvc.repository.UserRoleRepository;
import com.courses.mvc.service.util.UserConverterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Stepan
 */
@Service
public class RegistrationService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class )
    public void createUser(UserDTO newUser) {
        try {
            UserRole userRole = new UserRole();
            userRole.setRole(Role.USER);
            userRoleRepository.save(userRole);

            User user = UserConverterUtil.convertUserDTOToUser(newUser);
            user.setRole(userRole);
            userRepository.save(user);
        } catch (JpaSystemException e) {
            throw new UserServiceException("User already exist");
        }
    }

}
