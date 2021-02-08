package com.javastream.melles_crm.config;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WrongIdRequestException {

    @ExceptionHandler(IllegalStateException.class)
    public String handleIllegalStateException(Model model) {
        return "notFound";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Model model) {
        return "notFound";
    }
}
