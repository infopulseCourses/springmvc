package com.courses.mvc.repository;

import com.courses.mvc.dto.UserDTO;

/**
 * @author Stepan
 */
public interface BlackListRepositoryCustom {

    void addUserToBlackListByCustomMethod(UserDTO user);

    void removeFromBlackListByCustomMethod(UserDTO user);
}
