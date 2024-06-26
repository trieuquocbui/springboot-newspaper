package com.bqt.newspaper.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewsPaperGlobalException extends RuntimeException{
    private String code;
    private String message;
    private HttpStatus httpStatus;
}
