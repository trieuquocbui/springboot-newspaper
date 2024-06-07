package com.bqt.newspaper.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ErrorResponse  {
    private String code;
    private String message;
}
