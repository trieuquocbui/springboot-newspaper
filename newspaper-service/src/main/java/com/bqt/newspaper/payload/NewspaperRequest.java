package com.bqt.newspaper.payload;

import com.bqt.newspaper.entity.Paragraph;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class NewspaperRequest {

    private String name;

    private String topic;

    private String origin;

    private Date datetime;

    private List<Paragraph> paragraphs;
}
