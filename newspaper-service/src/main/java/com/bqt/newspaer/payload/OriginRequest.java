package com.bqt.newspaer.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OriginRequest {
    private String id;
    private String name;
}
