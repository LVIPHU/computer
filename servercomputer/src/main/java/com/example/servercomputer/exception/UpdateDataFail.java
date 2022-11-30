package com.example.servercomputer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class UpdateDataFail extends Exception {
    private static final long serialVersionUID = 1L;

    public UpdateDataFail(String errorCode){super(errorCode);}
}
