package com.bqt.newspaper.exception;

import org.springframework.http.HttpStatus;

public class EntityAlreadyException extends NewsPaperGlobalException{
    public EntityAlreadyException(String message, String code, HttpStatus httpStatus) {
        super(code, message,httpStatus);
    }
}
