package com.bqt.newspaper.service;

import com.bqt.newspaper.payload.InfoPage;
import com.bqt.newspaper.payload.NewspaperRequest;
import com.bqt.newspaper.payload.NewspaperResponse;
import com.bqt.newspaper.payload.PaginationResponse;
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
