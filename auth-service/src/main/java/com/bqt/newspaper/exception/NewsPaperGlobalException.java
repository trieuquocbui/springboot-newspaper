package com.bqt.newspaper.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class NewsPaperGlobalException extends RuntimeException{
    private String code;
    private String message;
    private HttpStatus httpStatus;
}
