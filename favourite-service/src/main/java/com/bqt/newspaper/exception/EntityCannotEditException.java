package com.bqt.newspaper.exception;

public class EntityCannotEditException extends NewsPaperGlobalException{
    public EntityCannotEditException(String message, String code) {
        super(code, message);
    }
}
