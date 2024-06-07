package com.bqt.newspaper.payload;

import com.bqt.newspaper.entity.Paragraph;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class NewspaperResponse {
    private String id;

    private String name;

    private Date datetime;
    
    private String topic;

    private String origin;

    private List<Paragraph> paragraphs;
}
