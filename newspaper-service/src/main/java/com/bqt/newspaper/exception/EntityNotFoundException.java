package com.bqt.newspaper.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EntityNotFoundException extends NewsPaperGlobalException{
    public EntityNotFoundException(String message, String code) {
        super(code, message);
    }
}
