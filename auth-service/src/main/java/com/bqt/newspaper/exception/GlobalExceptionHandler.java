package com.bqt.newspaper.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NewsPaperGlobalException.class)
    public ResponseEntity<ErrorResponse> handlerException(NewsPaperGlobalException newsPaperGlobalException) {
        return ResponseEntity.status(newsPaperGlobalException.getHttpStatus()).body(
                ErrorResponse.builder()
                .code(newsPaperGlobalException.getCode())
                .message(newsPaperGlobalException.getMessage()).build()
        );
    }
}
