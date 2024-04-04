package com.inditex.prueba.price.domain.exceptions;

import com.inditex.prueba.common.exceptions.ApiCodeException;

import java.io.Serial;

public class PriceNotFoundException extends ApiCodeException {
    @Serial
    private static final long serialVersionUID = -3363607545123045784L;
    public static final String MESSAGE = "A price could not be found for the given criteria.";
    public static final int ERROR_CODE = 404;

    public PriceNotFoundException(){
        super(String.format(MESSAGE),ERROR_CODE);
    }
}

