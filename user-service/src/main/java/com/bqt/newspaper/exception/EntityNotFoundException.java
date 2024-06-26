package com.bqt.newspaper.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class EntityNotFoundException extends NewsPaperGlobalException{
    public EntityNotFoundException(String message, String code, HttpStatus httpStatus) {
        super(code, message,httpStatus);
    }
}
