package com.bqt.newspaper.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopicOrOriginMessage {
    private String oldName;
    private String newName;
}
