package com.bqt.newspaer.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "origin")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Origin {
    @Id
    @Indexed()
    private String id;

    @Indexed()
    private String name;
}
