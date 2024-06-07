package com.bqt.newspaer.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewsPaperGlobalException extends RuntimeException{
    private String code;
    private String message;

    public NewsPaperGlobalException(String message) {
        super(message);
    }
}
