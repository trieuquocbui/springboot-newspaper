package com.bqt.newspaer.service;

import com.bqt.newspaer.payload.InfoPage;
import com.bqt.newspaer.payload.OriginRequest;
import com.bqt.newspaer.payload.OriginResponse;
import com.bqt.newspaer.payload.PaginationResponse;

import java.util.List;

public interface IOriginService {
    List<OriginResponse> getAllOrigin();

    OriginResponse createOrigin(OriginRequest originRequest);

    OriginResponse editOrigin(String originId, OriginRequest originRequest);

    void deleteOrigin(String originId);

    OriginResponse findOrigin(String originId);

    PaginationResponse<OriginResponse> findByOriginList(InfoPage infoPage);
}
