package com.example.subscription_platform_challenge.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(SubscriptionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String subscriptionNotFoundHandler(SubscriptionNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(PaymentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String paymentNotFoundExceptionHandler(PaymentNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(SubscriptionNotInformedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String subscriptionNotInformedExceptionHandler(SubscriptionNotInformedException ex) {
        return ex.getMessage();
    }

}
