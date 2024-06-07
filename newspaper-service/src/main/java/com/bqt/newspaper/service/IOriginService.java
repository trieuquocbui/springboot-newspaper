package com.bqt.newspaper.service;

import com.bqt.newspaper.payload.InfoPage;
import com.bqt.newspaper.payload.OriginRequest;
import com.bqt.newspaper.payload.OriginResponse;
import com.bqt.newspaper.payload.PaginationResponse;

import java.util.List;

public interface IOriginService {
    List<OriginResponse> getAllOrigin();

    OriginResponse createOrigin(OriginRequest originRequest);

    OriginResponse editOrigin(String originId, OriginRequest originRequest);

    void deleteOrigin(String originId);

    OriginResponse findOrigin(String originId);

    PaginationResponse<OriginResponse> findByOriginList(InfoPage infoPage);
}
