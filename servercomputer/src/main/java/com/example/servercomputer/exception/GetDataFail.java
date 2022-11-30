package com.example.servercomputer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class GetDataFail extends Exception{
    private static final long serialVersionUID = 1L;
    public GetDataFail(String code){super(code);}
}
