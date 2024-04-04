package com.inditex.prueba.common.exceptions;

public class UnexpectedAPIErrorException extends ApiCodeException {
    public static final String MESSAGE = "Unexpected API error";
    public static final int ERROR_CODE = 500;

    public UnexpectedAPIErrorException() {
        super(MESSAGE, ERROR_CODE);
    }
}
