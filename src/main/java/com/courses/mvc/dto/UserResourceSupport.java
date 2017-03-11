package com.courses.mvc.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;

/**
 * @author Stepan
 */
@Getter
@Setter
public class UserResourceSupport extends ResourceSupport {

    String login;
}
