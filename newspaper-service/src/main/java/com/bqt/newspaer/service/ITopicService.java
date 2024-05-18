package com.bqt.newspaer.service;

import com.bqt.newspaer.payload.InfoPage;
import com.bqt.newspaer.payload.PaginationResponse;
import com.bqt.newspaer.payload.TopicRequest;
import com.bqt.newspaer.payload.TopicResponse;

import java.util.List;

public interface ITopicService {
    TopicResponse createTopic(TopicRequest topicRequest);

    void deleteTopic(String topicId);

    TopicResponse editTopic(String topicId, TopicRequest topicRequest);

    List<TopicResponse> getTopicAll();

    TopicResponse findTopic(String topicId);

    PaginationResponse<TopicResponse> findByTopicList(InfoPage infoPage);
}
