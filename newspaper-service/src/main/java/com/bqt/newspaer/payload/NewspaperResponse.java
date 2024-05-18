package com.bqt.newspaer.payload;

import com.bqt.newspaer.entity.Origin;
import com.bqt.newspaer.entity.Paragraph;
import com.bqt.newspaer.entity.Topic;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class NewspaperResponse {
    private String id;

    private String name;

    private Date datetime;

    private String author;

    private String topic;

    private String origin;

    private List<Paragraph> paragraphs;
}
