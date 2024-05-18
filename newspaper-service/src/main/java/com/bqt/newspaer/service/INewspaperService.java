package com.bqt.newspaer.service;

import com.bqt.newspaer.payload.InfoPage;
import com.bqt.newspaer.payload.NewspaperRequest;
import com.bqt.newspaer.payload.NewspaperResponse;
import com.bqt.newspaer.payload.PaginationResponse;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;

public interface INewspaperService {
    NewspaperResponse createNewspaper(MultipartFile[] files, NewspaperRequest newspaperRequest);

    void deleteNewspaper(String newspaperId);

    NewspaperResponse editNewspaper(String newspaperId, NewspaperRequest newspaperRequest, MultipartFile[] files);

    NewspaperResponse findNewspaper(String newspaperId);

    PaginationResponse<NewspaperResponse> findByNewspaperList(InfoPage infoPage);

    PaginationResponse<NewspaperResponse> findByNewspaperListByDate(InfoPage infoPage) throws ParseException;

    List<NewspaperResponse> getAllNewNewspaper();

    List<NewspaperResponse> getRandNewNewspaper(int limit);
}
