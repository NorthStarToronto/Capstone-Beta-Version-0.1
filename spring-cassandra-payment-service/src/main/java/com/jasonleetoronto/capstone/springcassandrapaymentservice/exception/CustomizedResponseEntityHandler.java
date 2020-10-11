package com.jasonleetoronto.capstone.springcassandrapaymentservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;

/* Create Customized Response Entity Exception Handler applicable to all controller-wide */

@ControllerAdvice
@RestController
public class CustomizedResponseEntityHandler extends ResponseEntityExceptionHandler {

    /* Override Exception Handler Method */

    /* General Default Exception Handler which returns Exception Response with Exception Response Entity and Http Status */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ExceptionResponse  exceptionResponse = new ExceptionResponse(LocalDate.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /* Transaction Not Found Exception Handler*/
    @ExceptionHandler(TransactionNotFoundException.class)
    public final ResponseEntity<Object> handleTransactionNotFoundExceptions(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDate.now(), ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }
}
