package com.bqt.newspaper.service;

import com.bqt.newspaper.entity.Newspaper;
import com.bqt.newspaper.payload.FavouriteResponse;
import com.bqt.newspaper.payload.InfoPage;
import com.bqt.newspaper.payload.PaginationResponse;

import java.util.List;

public interface IFavouriteService {
    FavouriteResponse createFavouriteNewspaperForUser(String username, String newspaperId, Newspaper newspaperRequest);

    String deleteFavouriteNewspaperForUser(String username, String newspaperId);

    PaginationResponse<FavouriteResponse> getFavouriteNewspaperForUser(String username, InfoPage infoPage);

    List<FavouriteResponse> getAllFavouriteOfUser(String username);
}
