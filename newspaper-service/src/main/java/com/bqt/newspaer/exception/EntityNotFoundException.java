package com.bqt.newspaer.exception;

import lombok.AllArgsConstructor;
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
