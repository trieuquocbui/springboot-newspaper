package com.bqt.newspaper.service.impl;

import com.bqt.newspaper.entity.Origin;
import com.bqt.newspaper.event.OriginEvent;
import com.bqt.newspaper.exception.EntityAlreadyException;
import com.bqt.newspaper.exception.EntityCannotEditException;
import com.bqt.newspaper.exception.EntityNotFoundException;
import com.bqt.newspaper.exception.GlobalCode;
import com.bqt.newspaper.payload.*;
import com.bqt.newspaper.repository.NewspaperRepository;
import com.bqt.newspaper.repository.OriginRepository;
import com.bqt.newspaper.service.IOriginService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OriginService implements IOriginService {

    private final OriginRepository originRepository;

    private final NewspaperRepository newspaperRepository;

    private final OriginEvent originEvent;

    protected OriginResponse convertToOriginResponse(Origin origin){
        return new OriginResponse(origin.getId(),origin.getName());
    }

    @Override
    public List<OriginResponse> getAllOrigin() {
        List<Origin> topics = originRepository.findAll();
        return topics.stream().map(origin -> convertToOriginResponse(origin)).collect(Collectors.toList());
    }

    @Override
    public OriginResponse createOrigin(OriginRequest originRequest) {
        boolean checkOriginId = originRepository.existsById(originRequest.getId());
        if(checkOriginId){
            throw new EntityAlreadyException("Mã nguồn đã tồn tại", GlobalCode.ERROR_ID_EXIST);
        }
        boolean checkOriginName = originRepository.existsByName(originRequest.getName());
        if(checkOriginName){
            throw new EntityAlreadyException("Tên nguồn đã tồn tại", GlobalCode.ERROR_NAME_EXIST);
        }
        Origin origin = new Origin();
        origin.setId(originRequest.getId());
        origin.setName(originRequest.getName());
        origin = originRepository.save(origin);
        return convertToOriginResponse(origin);
    }

    @Override
    public OriginResponse editOrigin(String originId, OriginRequest originRequest) {
        Origin origin = originRepository.findById(originId).orElseThrow(() ->
                new EntityNotFoundException("Không tìm thầy nguồn",GlobalCode.ERROR_ENTITY_NOT_FOUND));

        boolean checkTopicName = originRepository.existsByName(originRequest.getName());

        if(checkTopicName){
            throw new EntityAlreadyException("Tên nguồn đã tồn tại", GlobalCode.ERROR_NAME_EXIST);
        }

        TopicOrOriginMessage topicOrOriginMessage = new TopicOrOriginMessage(origin.getName(),originRequest.getName());
        originEvent.updateOriginToNewspapers(topicOrOriginMessage);

        origin.setName(originRequest.getName());
        origin = originRepository.save(origin);

        return convertToOriginResponse(origin);
    }

    @Override
    public void deleteOrigin(String originId) {
        Origin origin = originRepository.findById(originId).orElseThrow(() ->
                new EntityNotFoundException("Không tìm thấy nguồn",GlobalCode.ERROR_ENTITY_NOT_FOUND));

        Integer checkSize = newspaperRepository.findByOrigin(origin.getName()).size();

        if(checkSize > 0){
            throw new EntityCannotEditException("Nguồn không thể chỉnh sửa",GlobalCode.ERROR_ENTITY_CANNOT_EDIT);
        }

        originRepository.delete(origin);
    }

    @Override
    public OriginResponse findOrigin(String originId) {
        Origin origin = originRepository.findById(originId).orElseThrow(() ->
                new EntityNotFoundException("Không tìm thấy nguồn",GlobalCode.ERROR_ENTITY_NOT_FOUND));
        return convertToOriginResponse(origin);
    }

    @Override
    public PaginationResponse<OriginResponse> findByOriginList(InfoPage infoPage) {
        Sort sort = infoPage.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(infoPage.getSortBy()).ascending() : Sort.by(infoPage.getSortBy()).descending();

        Pageable pageable = PageRequest.of(infoPage.getPage() - 1, infoPage.getLimit(),sort);

        Example<Origin> example;
        if (infoPage.getSearch() != null && !infoPage.getSearch().isEmpty()) {
            Origin exampleOrigin = new Origin();
            exampleOrigin.setId(infoPage.getSearch());
            exampleOrigin.setName(infoPage.getSearch());

            ExampleMatcher matcher = ExampleMatcher.matchingAny()
                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                    .withIgnoreCase();

            example = Example.of(exampleOrigin, matcher);
        } else {
            example = Example.of(new Origin());
        }

        Page<Origin> resultPage = this.originRepository.findAll(example,pageable);

        PaginationResponse<OriginResponse> paginationResponse = new PaginationResponse<>();

        List<OriginResponse> collect = resultPage.getContent().stream().map(
                topic -> convertToOriginResponse(topic)).collect(Collectors.toList());

        paginationResponse.setContent(collect);
        paginationResponse.setLastPage(resultPage.isLast());
        paginationResponse.setTotalElements(resultPage.getTotalElements());
        paginationResponse.setPageSize(resultPage.getSize());
        paginationResponse.setPageNumber(resultPage.getNumber());
        paginationResponse.setTotalPages(resultPage.getTotalPages());

        return paginationResponse;
    }
}
