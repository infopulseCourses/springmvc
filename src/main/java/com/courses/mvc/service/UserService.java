package com.courses.mvc.service;

import com.courses.mvc.domain.User;
import com.courses.mvc.dto.UserDTO;
import com.courses.mvc.repository.UserRepository;
import com.courses.mvc.service.util.UserConverterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stepan
 */
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<UserDTO> getAllUsersExceptAdmins() {
        List<UserDTO> result = new ArrayList<>();
        List<User> users = userRepository.getAllUsersExceptAdmins();
        for (User u : users) {
            UserDTO userDTO = UserConverterUtil.convertUserToUserDTO(u);
            userDTO.setBanned(u.getBlackList() != null);
            result.add(userDTO);
        }

        return result;
    }
}
