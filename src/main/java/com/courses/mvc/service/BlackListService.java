package com.courses.mvc.service;

import com.courses.mvc.dto.UserDTO;
import com.courses.mvc.exception.DAOException;
import com.courses.mvc.exception.RestException;
import com.courses.mvc.repository.BlackListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * @author Stepan
 */
@Service
public class BlackListService {

    @Autowired
    BlackListRepository blackListRepository;

    @Autowired
    MessageSource messageSource;


    public void banUser(UserDTO user) {
        try {
            blackListRepository.addUserToBlackListByCustomMethod(user);
        } catch (DAOException e) {
            throw new RestException(messageSource.getMessage("blacklist.cannotAddUser", null, Locale.ENGLISH));
        }
    }

    public void removeFromBan(UserDTO user) {
        try {
            blackListRepository.removeFromBlackListByCustomMethod(user);
        } catch (DAOException e) {
            throw new RestException(messageSource.getMessage("blacklist.cannotRemoveUser", null, Locale.ENGLISH));
        }
    }
}
