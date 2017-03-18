package com.courses.mvc.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Stepan
 */
public class GeneralResponseDTO {

    @Getter
    @Setter
    boolean success;

    public void operationSuccessfull() {
        success = true;
    }

    public void operationFailed() {
        success = false;
    }
}
