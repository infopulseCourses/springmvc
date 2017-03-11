package com.courses.mvc.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author Stepan
 */
@Getter
@Setter
public class ResponseUserDTO {

    List<UserResourceSupport> users;
}
