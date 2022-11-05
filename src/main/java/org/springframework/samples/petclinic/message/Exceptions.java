package org.springframework.samples.petclinic.message;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class Exceptions {

    @ExceptionHandler
    public String handleException(Exception e) {
        return e.getMessage();
    }
}
