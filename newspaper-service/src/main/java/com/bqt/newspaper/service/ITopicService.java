package com.bqt.newspaper.service;

import com.bqt.newspaper.payload.InfoPage;
import com.bqt.newspaper.payload.PaginationResponse;
import com.bqt.newspaper.payload.TopicRequest;
import com.bqt.newspaper.payload.TopicResponse;

import java.util.List;

public interface ITopicService {
    TopicResponse createTopic(TopicRequest topicRequest);

    void deleteTopic(String topicId);

    TopicResponse editTopic(String topicId, TopicRequest topicRequest);

    List<TopicResponse> getTopicAll();

    TopicResponse findTopic(String topicId);

    PaginationResponse<TopicResponse> findByTopicList(InfoPage infoPage);
}
