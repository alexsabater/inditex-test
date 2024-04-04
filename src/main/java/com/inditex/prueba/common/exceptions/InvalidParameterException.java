package com.inditex.prueba.common.exceptions;

import java.io.Serial;

public class InvalidParameterException extends ApiCodeException {
    @Serial
    private static final long serialVersionUID = 262067662626644005L;
    public static final String MESSAGE = "Invalid parameters";
    public static final int ERROR_CODE = 400;

    public InvalidParameterException() {
        super(MESSAGE, ERROR_CODE);
    }
}
