package com.gti.CreditCardTransactions.exception;

public class BadRequestParmsException extends RuntimeException {
 
    private String message;
 
    public BadRequestParmsException() {}
 
    public BadRequestParmsException(String msg)
    {
        super(msg);
        this.message = msg;
    }
}