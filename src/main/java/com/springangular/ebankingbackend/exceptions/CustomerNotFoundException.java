package com.springangular.ebankingbackend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No Customer found in Database!")
public class CustomerNotFoundException extends RuntimeException { // RuntimeException est non surveillée
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
