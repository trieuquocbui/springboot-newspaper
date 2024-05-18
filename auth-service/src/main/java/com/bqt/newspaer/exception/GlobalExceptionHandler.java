package com.bqt.newspaer.exception;

import com.bqt.newspaer.payload.ApiResponse;
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

//    @ExceptionHandler(Exception.class)
//    protected ResponseEntity<ApiResponse<String>> handleException(Exception e, Locale locale) {
//        ApiResponse<String> apiResponse = new ApiResponse<>("1000",e.getMessage(),null);
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
//    }

}
