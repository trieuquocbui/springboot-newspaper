package com.bqt.newspaer.service.impl;

import com.bqt.newspaer.entity.Topic;
import com.bqt.newspaer.event.TopicEvent;
import com.bqt.newspaer.exception.EntityAlreadyException;
import com.bqt.newspaer.exception.EntityCannotEditException;
import com.bqt.newspaer.exception.EntityNotFoundException;
import com.bqt.newspaer.exception.GlobalCode;
import com.bqt.newspaer.payload.*;
import com.bqt.newspaer.repository.NewspaperRepository;
import com.bqt.newspaer.repository.TopicRepository;
import com.bqt.newspaer.service.ITopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TopicService implements ITopicService {

    private final TopicRepository topicRepository;

    private final NewspaperRepository newspaperRepository;

    private final TopicEvent topicEvent;

    protected TopicResponse convertToTopicResponse(Topic topic){
        return new TopicResponse(topic.getId(),topic.getName());
    }

    @Override
    public TopicResponse createTopic(TopicRequest topicRequest) {
        boolean checkTopicId = topicRepository.existsById(topicRequest.getId());
        if(checkTopicId){
            throw new EntityAlreadyException("Mã chủ đề đã tồn tại!", GlobalCode.ERROR_ID_EXIST);
        }
        boolean checkTopicName = topicRepository.existsByName(topicRequest.getName());
        if(checkTopicName){
            throw new EntityAlreadyException("Tên chủ đề đã tồn tại!", GlobalCode.ERROR_NAME_EXIST);
        }
        Topic topic = new Topic();
        topic.setId(topicRequest.getId());
        topic.setName(topicRequest.getName());
        topic = topicRepository.save(topic);
        return convertToTopicResponse(topic);
    }

    @Override
    public void deleteTopic(String topicId) {
        Topic topic = topicRepository.findById(topicId).orElseThrow(() ->
                new EntityNotFoundException("Không tìm thấy chủ đề!",GlobalCode.ERROR_ENTITY_NOT_FOUND));

        Integer checkQuantity = newspaperRepository.findByTopic(topicId).size();

        if(checkQuantity > 0){
            throw new EntityCannotEditException("Không thể chỉnh sữa!",GlobalCode.ERROR_ENTITY_CANNOT_EDIT);
        }

        topicRepository.delete(topic);
    }

    @Override
    public TopicResponse editTopic(String topicId, TopicRequest topicRequest) {
        Topic topic = topicRepository.findById(topicId).orElseThrow(() ->
                new EntityNotFoundException("Không tìm thấy chủ đề",GlobalCode.ERROR_ENTITY_NOT_FOUND));

        boolean checkTopicName = topicRepository.existsByName(topicRequest.getName());
        if(checkTopicName){
            throw new EntityAlreadyException("Tên chủ đề đã tồn tại!", GlobalCode.ERROR_NAME_EXIST);
        }

        TopicOrOriginMessage topicName = new TopicOrOriginMessage(topic.getName(),topicRequest.getName());
        topicEvent.updateTopicToNewspapers(topicName);

        topic.setName(topicRequest.getName());
        topic = topicRepository.save(topic);
        return convertToTopicResponse(topic);
    }

    @Override
    public List<TopicResponse> getTopicAll() {
        List<Topic> topics = topicRepository.findAll();
        return topics.stream().map(topic -> convertToTopicResponse(topic)).collect(Collectors.toList());
    }

    @Override
    public TopicResponse findTopic(String topicId) {
        Topic topic = topicRepository.findById(topicId).orElseThrow(() ->
                new EntityNotFoundException("Không tìm thấy chủ đề",GlobalCode.ERROR_ENTITY_NOT_FOUND));
        return convertToTopicResponse(topic);
    }

    @Override
    public PaginationResponse<TopicResponse> findByTopicList(InfoPage infoPage) {
        Sort sort = infoPage.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(infoPage.getSortBy()).ascending() : Sort.by(infoPage.getSortBy()).descending();

        Pageable pageable = PageRequest.of(infoPage.getPage() - 1, infoPage.getLimit(),sort);

        Example<Topic> example;
        if (infoPage.getSearch() != null && !infoPage.getSearch().isEmpty()) {
            Topic exampleTopic = new Topic();
            exampleTopic.setId(infoPage.getSearch());
            exampleTopic.setName(infoPage.getSearch());

            ExampleMatcher matcher = ExampleMatcher.matchingAny()
                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                    .withIgnoreCase();

            example = Example.of(exampleTopic, matcher);
        } else {
            example = Example.of(new Topic());
        }

        Page<Topic> resultPage = this.topicRepository.findAll(example,pageable);

        PaginationResponse<TopicResponse> paginationResponse = new PaginationResponse<>();

        List<TopicResponse> collect = resultPage.getContent().stream().map(
                topic -> convertToTopicResponse(topic)).collect(Collectors.toList());

        paginationResponse.setContent(collect);
        paginationResponse.setLastPage(resultPage.isLast());
        paginationResponse.setTotalElements(resultPage.getTotalElements());
        paginationResponse.setPageSize(resultPage.getSize());
        paginationResponse.setPageNumber(resultPage.getNumber());
        paginationResponse.setTotalPages(resultPage.getTotalPages());

        return paginationResponse;
    }
}
