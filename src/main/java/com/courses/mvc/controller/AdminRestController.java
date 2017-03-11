package com.courses.mvc.controller;

import com.courses.mvc.controller.util.AccessUtil;
import com.courses.mvc.domain.Role;
import com.courses.mvc.dto.ResponseUserDTO;
import com.courses.mvc.dto.UserDTO;
import com.courses.mvc.dto.UserResourceSupport;
import com.courses.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Stepan
 */
@RestController
@RequestMapping(value = "/users")
public class AdminRestController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ResponseUserDTO> getAllUsers() {
        AccessUtil.verifyUserRole(Role.ADMIN);
        List<UserDTO> users = userService.getAllUsersExceptAdmins();
        List<UserResourceSupport> userResourceSupports = new ArrayList<>();

        for (UserDTO userDTO : users) {
            UserResourceSupport resourceSupport = new UserResourceSupport();
            resourceSupport.setLogin(userDTO.getLogin());
            LinkBuilder userLinkBuilder = linkTo(AdminRestController.class).slash("ban");
            Link userLink = null;

            if (userDTO.isBanned()) {
                userLink = userLinkBuilder.slash("remove").slash(userDTO.getId()).withRel("remove");
            } else {
                userLink = userLinkBuilder.slash("add").slash(userDTO.getId()).withRel("add");
            }

            resourceSupport.add(userLink);
            userResourceSupports.add(resourceSupport);
        }
        ResponseUserDTO result = new ResponseUserDTO();
        result.setUsers(userResourceSupports);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
