package org.springframework.samples.petclinic.capacity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Exception {

    @ExceptionHandler(java.lang.Exception.class)
    public String handleException(java.lang.Exception e) {
        return e.getMessage();
    }
}
