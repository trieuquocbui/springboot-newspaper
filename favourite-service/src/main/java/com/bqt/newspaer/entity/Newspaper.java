package com.bqt.newspaer.entity;

import lombok.*;

import java.util.Date;
import java.util.List;

@Data
public class Newspaper {
    private String id;

    private String name;

    private Date datetime;

    private String topic;

    private String origin;

    private List<Paragraph> paragraphs;
}
