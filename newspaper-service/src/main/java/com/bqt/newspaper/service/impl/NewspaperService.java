package com.bqt.newspaper.service.impl;


import com.bqt.newspaper.client.FileClient;
import com.bqt.newspaper.constant.PaginationConstant;
import com.bqt.newspaper.entity.Newspaper;
import com.bqt.newspaper.entity.Origin;
import com.bqt.newspaper.entity.Paragraph;
import com.bqt.newspaper.entity.Topic;
import com.bqt.newspaper.event.FileEvent;
import com.bqt.newspaper.event.NotificationEvent;
import com.bqt.newspaper.exception.*;
import com.bqt.newspaper.payload.*;
import com.bqt.newspaper.repository.NewspaperRepository;
import com.bqt.newspaper.repository.OriginRepository;
import com.bqt.newspaper.repository.TopicRepository;
import com.bqt.newspaper.service.INewspaperService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewspaperService implements INewspaperService {

    private final NewspaperRepository newspaperRepository;

    private final TopicRepository topicRepository;

    private final OriginRepository originRepository;

    private final NotificationEvent notificationEvent;

    private final FileEvent fileEvent;

    private final MongoTemplate mongoTemplate;

    private final FileClient fileClient;

    private final SimpMessagingTemplate simpMessagingTemplate;

    protected NewspaperResponse convertToNewspaperResponse(Newspaper newspaper){
        NewspaperResponse newspaperResponse = new NewspaperResponse();
        newspaperResponse.setId(newspaper.getId());
        newspaperResponse.setDatetime(newspaper.getDatetime());
        newspaperResponse.setName(newspaper.getName());
        newspaperResponse.setOrigin(newspaper.getOrigin());
        newspaperResponse.setTopic(newspaper.getTopic());
        newspaperResponse.setParagraphs(newspaper.getParagraphs());
        return newspaperResponse;
    }

    @Transactional
    @Override
    public NewspaperResponse createNewspaper(MultipartFile[] files, NewspaperRequest newspaperRequest) {
        Topic topic = topicRepository.findByName(newspaperRequest.getTopic()).orElseThrow(
                () -> new EntityNotFoundException("Không tìm thấy chủ đề",GlobalCode.ERROR_ENTITY_NOT_FOUND));

        Origin origin = originRepository.findByName(newspaperRequest.getOrigin()).orElseThrow(
                () -> new EntityNotFoundException("Không tìm thấy nguồn",GlobalCode.ERROR_ENTITY_NOT_FOUND));

        boolean checkName = newspaperRepository.existsByName(newspaperRequest.getName());

        if(checkName){
            throw new EntityAlreadyException("Tên bài báo đã tồn tại",GlobalCode.ERROR_NAME_EXIST);
        }

        String image = "";

        if(files != null){
            FileResponse fileResponses = fileClient.uploadFiles(files).getBody();
            List<Paragraph> paragraphs = new ArrayList<>();
            int i = 0;
            for ( Paragraph paragraph : newspaperRequest.getParagraphs()){
                if(paragraph.getThumbnail() != null){
                    paragraph.setThumbnail(fileResponses.getNames().get(i));
                    if(i == 0){
                        image = fileResponses.getNames().get(i);
                    }
                    ++i;
                }
                paragraphs.add(paragraph);
            }
            newspaperRequest.setParagraphs(paragraphs);
        }

        Newspaper newspaper = new Newspaper();
        newspaper.setName(newspaperRequest.getName());
        newspaper.setDatetime(newspaperRequest.getDatetime());
        newspaper.setTopic(topic.getName());
        newspaper.setOrigin(origin.getName());
        newspaper.setParagraphs(newspaperRequest.getParagraphs());
        newspaper = newspaperRepository.save(newspaper);

        NotificationRequest notificationRequest = new NotificationRequest();
        notificationRequest.setContent(newspaper.getName());
        notificationRequest.setNewspaperId(newspaper.getId());
        notificationRequest.setDateTime(newspaper.getDatetime());
        notificationRequest.setImage(image);

        notificationEvent.createNotification(notificationRequest);

        simpMessagingTemplate.convertAndSend("/newspaper/notification", notificationRequest);

        return convertToNewspaperResponse(newspaper);
    }

    @Override
    public void deleteNewspaper(String newspaperId) {
        Newspaper newspaper = newspaperRepository.findById(newspaperId).orElseThrow(
                () -> new EntityNotFoundException("Không tìm thấy bài báo", GlobalCode.ERROR_ENTITY_NOT_FOUND));

        Date startDate = newspaper.getDatetime();
        Date endDate = new Date();
        long startTimeInMillis = startDate.getTime();
        long endTimeInMillis = endDate.getTime();
        long differenceInMilliseconds = endTimeInMillis - startTimeInMillis;
        long numberOfDays = differenceInMilliseconds / (1000 * 60 * 60 * 24);

        if(numberOfDays > 5){
            throw new EntityCannotEditException("Không thể xoá!",GlobalCode.ERROR_ENTITY_CANNOT_EDIT);
        }

        List<String> fileNames = newspaper.getParagraphs().stream()
                .filter(paragraph ->  paragraph.getThumbnail() != null)
                .map(paragraph -> paragraph.getThumbnail()).collect(Collectors.toList());

        if(fileNames.size() != 0){
            fileEvent.deleteFile(fileNames);
        }

        notificationEvent.deleteNotification(newspaperId);

        simpMessagingTemplate.convertAndSend("/newspaper/delete/notification/", newspaperId);

        newspaperRepository.delete(newspaper);
    }

    @Transactional
    @Override
    public NewspaperResponse editNewspaper(String newspaperId, NewspaperRequest newspaperRequest, MultipartFile[] files) {
        Newspaper newspaper = newspaperRepository.findById(newspaperId).orElseThrow(
                () -> new EntityNotFoundException("Không tìm thấy bài báo", GlobalCode.ERROR_ENTITY_NOT_FOUND));

        if(!newspaperRequest.getName().equals(newspaper.getName())){
            boolean checkName = newspaperRepository.existsByName(newspaperRequest.getName());

            if(checkName){
                throw new EntityAlreadyException("Tên bài báo đã tồn tại",GlobalCode.ERROR_NAME_EXIST);
            }
        }

        if(newspaper.getOrigin() != newspaperRequest.getOrigin()){
            Topic topic = topicRepository.findByName(newspaperRequest.getTopic()).orElseThrow(
                    () -> new EntityNotFoundException("Không tìm thấy chủ đề",GlobalCode.ERROR_ENTITY_NOT_FOUND)
            );
            newspaper.setTopic(topic.getName());
        }

        if(newspaperRequest.getTopic() != newspaperRequest.getTopic()){
            Origin origin = originRepository.findByName(newspaperRequest.getOrigin()).orElseThrow(
                    () -> new EntityNotFoundException("Không tìm thấy nguồn",GlobalCode.ERROR_ENTITY_NOT_FOUND)
            );
            newspaper.setOrigin(origin.getName());
        }

        List<String> thumbnails = newspaperRequest.getParagraphs().stream()
                .filter(paragraph -> paragraph.getThumbnail() != null)
                .map(paragraph -> paragraph.getThumbnail()).collect(Collectors.toList());

        List<String> removeThumbnails = new ArrayList<>();
        for (Paragraph paragraph: newspaper.getParagraphs()){
            if(paragraph.getThumbnail() != null && !thumbnails.contains(paragraph.getThumbnail())){
                removeThumbnails.add(paragraph.getThumbnail());
            }
        }

        if(!removeThumbnails.isEmpty()){
            fileEvent.deleteFile(removeThumbnails);
        }

        if(files != null){
            FileResponse fileResponses = fileClient.uploadFiles(files).getBody();
            List<Paragraph> paragraphs = new ArrayList<>();
            int i = 0;
            for ( Paragraph paragraph : newspaperRequest.getParagraphs()){
                if(paragraph.getThumbnail() != null && paragraph.getThumbnail().contains(".")){
                    paragraph.setThumbnail(fileResponses.getNames().get(i));
                    ++i;
                }
                paragraphs.add(paragraph);
            }
            newspaperRequest.setParagraphs(paragraphs);
        }

        newspaper.setDatetime(newspaperRequest.getDatetime());
        newspaper.setName(newspaperRequest.getName());
        newspaper.setParagraphs(newspaperRequest.getParagraphs());
        newspaper = newspaperRepository.save(newspaper);
        return convertToNewspaperResponse(newspaper);
    }

    @Override
    public NewspaperResponse findNewspaper(String newspaperId) {
        Newspaper newspaper = newspaperRepository.findById(newspaperId).orElseThrow(
                () -> new EntityNotFoundException("Không tìm thấy bài báo", GlobalCode.ERROR_ENTITY_NOT_FOUND));

        return convertToNewspaperResponse(newspaper);
    }

    @Override
    public PaginationResponse<NewspaperResponse> findByNewspaperList(InfoPage infoPage) {
        Sort sort = infoPage.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(infoPage.getSortBy()).ascending() : Sort.by(infoPage.getSortBy()).descending();

        Pageable pageable = PageRequest.of(infoPage.getPage() - 1, infoPage.getLimit(),sort);

        Example<Newspaper> example;
        Newspaper exampleNewspaper = new Newspaper();
        if(!infoPage.getTopic().equals(PaginationConstant.DEFAULT_ALL)){
            exampleNewspaper.setTopic(infoPage.getTopic());
        }
        if(!infoPage.getOrigin().equals(PaginationConstant.DEFAULT_ALL)){
            exampleNewspaper.setOrigin(infoPage.getOrigin());
        }
        if (infoPage.getSearch() != null && !infoPage.getSearch().isEmpty()) {
            exampleNewspaper.setName(infoPage.getSearch());

            ExampleMatcher matcher = ExampleMatcher.matchingAny()
                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                    .withIgnoreCase();

            example = Example.of(exampleNewspaper,matcher);
        }else{
            example = Example.of(exampleNewspaper);
        }
        Page<Newspaper> resultPage = newspaperRepository.findAll(example,pageable);

        
        PaginationResponse<NewspaperResponse> paginationResponse = new PaginationResponse<>();

        List<NewspaperResponse> collect = resultPage.getContent().stream().map(
                newspaper -> convertToNewspaperResponse(newspaper)).collect(Collectors.toList());

        paginationResponse.setContent(collect);
        paginationResponse.setLastPage(resultPage.isLast());
        paginationResponse.setTotalElements(resultPage.getTotalElements());
        paginationResponse.setPageSize(resultPage.getSize());
        paginationResponse.setPageNumber(resultPage.getNumber());
        paginationResponse.setTotalPages(resultPage.getTotalPages());

        return paginationResponse;
    }

    @Override
    public PaginationResponse<NewspaperResponse> findByNewspaperListByDate(InfoPage infoPage) throws ParseException {
        Sort sort = infoPage.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(infoPage.getSortBy()).ascending() : Sort.by(infoPage.getSortBy()).descending();

        Pageable pageable = PageRequest.of(infoPage.getPage() - 1, infoPage.getLimit(),sort);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date startDay = dateFormat.parse(infoPage.getTime());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDay);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date endDay = calendar.getTime();

        Page<Newspaper> resultPage = this.newspaperRepository.findByDatetimeBetween(startDay,endDay,pageable);

        PaginationResponse<NewspaperResponse> paginationResponse = new PaginationResponse<>();

        List<NewspaperResponse> collect = resultPage.getContent().stream().map(
                newspaper -> convertToNewspaperResponse(newspaper)).collect(Collectors.toList());

        paginationResponse.setContent(collect);
        paginationResponse.setLastPage(resultPage.isLast());
        paginationResponse.setTotalElements(resultPage.getTotalElements());
        paginationResponse.setPageSize(resultPage.getSize());
        paginationResponse.setPageNumber(resultPage.getNumber());
        paginationResponse.setTotalPages(resultPage.getTotalPages());

        return paginationResponse;
    }

    @Override
    public List<NewspaperResponse> getAllNewNewspaper() {
        Date startDay = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDay);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date endDay = calendar.getTime();

        List<NewspaperResponse> list = this.newspaperRepository.findByDatetimeBetween(startDay,endDay).stream().map(
                newspaper -> convertToNewspaperResponse(newspaper)).collect(Collectors.toList());

        if(list.size() == 0){
            calendar.setTime(startDay);
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            startDay = calendar.getTime();
            list = this.newspaperRepository.findByDatetimeBetween(startDay,endDay).stream().map(
                    newspaper -> convertToNewspaperResponse(newspaper)).collect(Collectors.toList());
        }

        return list;
    }

    @Override
    public List<NewspaperResponse> getRandNewNewspaper(int limit) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.sample(limit)
        );
        AggregationResults<Newspaper> results = mongoTemplate.aggregate(aggregation, "newspaper", Newspaper.class);
        return results.getMappedResults().stream().map(
                newspaper -> convertToNewspaperResponse(newspaper)).collect(Collectors.toList());
    }
}
