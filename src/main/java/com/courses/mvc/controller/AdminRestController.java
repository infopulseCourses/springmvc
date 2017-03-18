package com.courses.mvc.controller;

import com.courses.mvc.controller.util.AccessUtil;
import com.courses.mvc.domain.Role;
import com.courses.mvc.dto.*;
import com.courses.mvc.service.BlackListService;
import com.courses.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * @author Stepan
 */
@RestController
@RequestMapping(value = "/users")
public class AdminRestController {

    @Autowired
    private UserService userService;

    @Autowired
    BlackListService blackListService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ResponseUserDTO> getAllUsers() {
        AccessUtil.verifyUserRole(Role.ADMIN);
        List<UserDTO> users = userService.getAllUsersExceptAdmins();
        List<UserResourceSupport> userResourceSupports = new ArrayList<>();

        for (UserDTO userDTO : users) {
            UserResourceSupport resourceSupport = new UserResourceSupport();
            resourceSupport.setLogin(userDTO.getLogin());
            /*LinkBuilder userLinkBuilder = linkTo(AdminRestController.class).slash("ban");
              Link userLink = null;

            if (userDTO.isBanned()) {
                userLink = userLinkBuilder.slash("remove").slash(userDTO.getId()).withRel("remove");
            } else {
                userLink = userLinkBuilder.slash("add").slash(userDTO.getId()).withRel("add");
            }
            resourceSupport.add(userLink);*/

            ResponseEntity<GeneralResponseDTO> linkToAddToBan = methodOn(AdminRestController.class).addToBan(userDTO);
            ResponseEntity<GeneralResponseDTO> linkToRemoveFromBan = methodOn(AdminRestController.class).removeFromBan(userDTO);
            Link userLink = null;
            if (userDTO.isBanned()) {
                userLink = linkTo(linkToRemoveFromBan).withRel("remove");
            } else {
                userLink = linkTo(linkToAddToBan).withRel("add");
            }
            resourceSupport.add(userLink);
            userResourceSupports.add(resourceSupport);
        }
        ResponseUserDTO result = new ResponseUserDTO();
        result.setUsers(userResourceSupports);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/ban/add", method = RequestMethod.POST)
    public ResponseEntity<GeneralResponseDTO> addToBan(@RequestBody UserDTO user) {
        AccessUtil.verifyUserRole(Role.ADMIN);
        System.out.println("user " + user.getLogin());
        blackListService.banUser(user);
        GeneralResponseDTO responseDTO = new GeneralResponseDTO();
        responseDTO.operationSuccessfull();
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/ban/remove", method = RequestMethod.POST)
    public ResponseEntity<GeneralResponseDTO> removeFromBan(@RequestBody UserDTO user) {
        AccessUtil.verifyUserRole(Role.ADMIN);
        blackListService.removeFromBan(user);
        GeneralResponseDTO responseDTO = new GeneralResponseDTO();
        responseDTO.operationSuccessfull();
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
