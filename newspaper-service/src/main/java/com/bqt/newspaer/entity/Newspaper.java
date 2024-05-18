package com.bqt.newspaer.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.Id;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(value = "newspaper")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Newspaper {
    @Id
    @Indexed()
    private String id;

    @Indexed()
    private String name;

    @Indexed()
    private Date datetime;

    private String author;

    @Indexed()
    private String topic;

    @Indexed()
    private String origin;

    private List<Paragraph> paragraphs;
}
