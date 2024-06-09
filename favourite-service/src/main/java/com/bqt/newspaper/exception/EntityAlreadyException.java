package com.bqt.newspaper.exception;

public class EntityAlreadyException extends NewsPaperGlobalException{
    public EntityAlreadyException(String message, String code) {
        super(code, message);
    }
}
