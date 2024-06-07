package com.bqt.newspaer.exception;

public class EntityAlreadyException extends NewsPaperGlobalException {
    public EntityAlreadyException(String message, String code) {
        super(code, message);
    }
}
