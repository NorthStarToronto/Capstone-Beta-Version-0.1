package com.jasonleetoronto.capstone.springcassandrapaymentservice.exception;

import java.time.LocalDate;

/* Create Enterprise-wide Generic Error Response with 3X common attributes */
public class ExceptionResponse {

    private LocalDate date;
    private String message;
    private String details;

    protected ExceptionResponse() {
    }

    public ExceptionResponse(LocalDate date, String message, String details) {
        super();
        this.date = date;
        this.message = message;
        this.details = details;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
