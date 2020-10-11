package com.jasonleetoronto.capstone.springcassandrapaymentservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Supplier;

/* Create Custom Transaction Not Found Exception Class with a default Http Status of 404 Not Found */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class TransactionNotFoundException extends RuntimeException {

    public TransactionNotFoundException(String message) {
        super(message);
    }

}
