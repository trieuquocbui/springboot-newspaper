package com.bqt.newspaper.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NewsPaperGlobalException.class)
    public ResponseEntity<ErrorResponse> handlerBadRequestException(NewsPaperGlobalException newsPaperGlobalException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorResponse.builder()
                .code(newsPaperGlobalException.getCode())
                .message(newsPaperGlobalException.getMessage()).build()
        );
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity handleException(Exception e, Locale locale) {
        return ResponseEntity
                .badRequest()
                .body("Exception occur inside API " + e);
    }

}
