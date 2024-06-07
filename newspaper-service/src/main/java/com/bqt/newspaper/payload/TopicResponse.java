package com.bqt.newspaper.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopicResponse {
    private String id;
    private String name;
}
