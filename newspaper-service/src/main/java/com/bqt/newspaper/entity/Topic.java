package com.bqt.newspaper.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "topic")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Topic {
    @Id
    @Indexed()
    private String id;

    @Indexed()
    private String name;
}
