package com.courses.mvc.dto;

import com.courses.mvc.domain.Role;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author Stepan
 */
@Getter
@Setter
public class UserDTO {

    @NotNull
    @Pattern(regexp = "\\w{3,}", message = "Name incorrect")
    private String name;

    @NotNull
    private String login;

    @NotNull
    private String password;

    private Role role;

}
