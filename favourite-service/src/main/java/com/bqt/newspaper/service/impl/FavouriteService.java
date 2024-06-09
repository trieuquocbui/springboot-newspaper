package com.bqt.newspaper.service.impl;

import com.bqt.newspaper.client.NewspaperClient;
import com.bqt.newspaper.constant.PaginationConstant;
import com.bqt.newspaper.entity.Favourite;
import com.bqt.newspaper.entity.Newspaper;
import com.bqt.newspaper.exception.EntityNotFoundException;
import com.bqt.newspaper.exception.GlobalCode;
import com.bqt.newspaper.payload.ApiResponse;
import com.bqt.newspaper.payload.FavouriteResponse;
import com.bqt.newspaper.payload.InfoPage;
import com.bqt.newspaper.payload.PaginationResponse;
import com.bqt.newspaper.repository.FavouriteRepository;
import com.bqt.newspaper.service.IFavouriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavouriteService implements IFavouriteService {

    private final FavouriteRepository favouriteRepository;

    private final NewspaperClient newspaperClient;

    protected FavouriteResponse convertToFavouriteResponse(Favourite favourite){
        FavouriteResponse favouriteResponse = new FavouriteResponse();
        Newspaper newspaper = new Newspaper();
        newspaper.setId(favourite.getNewspaper().getId());
        newspaper.setDatetime(favourite.getNewspaper().getDatetime());
        newspaper.setName(favourite.getNewspaper().getName());
        newspaper.setOrigin(favourite.getNewspaper().getOrigin());
        newspaper.setTopic(favourite.getNewspaper().getTopic());
        newspaper.setParagraphs(favourite.getNewspaper().getParagraphs());
        favouriteResponse.setId(favourite.getId());
        favouriteResponse.setUsername(favourite.getUsername());
        favouriteResponse.setNewspaper(favourite.getNewspaper());
        favouriteResponse.setDatetime(favourite.getDatetime());
        return favouriteResponse;
    }

    @Override
    public FavouriteResponse createFavouriteNewspaperForUser(String username, String newspaperId, Newspaper newspaper) {
        ApiResponse<Newspaper> apiResponse = newspaperClient.getNewspaper(newspaperId).getBody();

        if (apiResponse.getCode().equals(GlobalCode.SUCCESS) && !apiResponse.getData().getId().equals(newspaper.getId())){
            throw new EntityNotFoundException("Bài báo không tồn tại!",GlobalCode.ERROR_ENTITY_NOT_FOUND);
        }

        Favourite favourite = new Favourite();
        favourite.setUsername(username);
        favourite.setNewspaper(apiResponse.getData());
        favourite.setDatetime(new Date());
        return convertToFavouriteResponse(favouriteRepository.save(favourite));
    }

    @Override
    public String deleteFavouriteNewspaperForUser(String username, String newspaperId) {
        ApiResponse<Newspaper> apiResponse = newspaperClient.getNewspaper(newspaperId).getBody();

        if (!apiResponse.getCode().equals(GlobalCode.SUCCESS)){
            throw new EntityNotFoundException("Bài báo không tồn tại!",GlobalCode.ERROR_ENTITY_NOT_FOUND);
        }

        Favourite favourite = favouriteRepository.findByUsernameAndNewspaper(username,apiResponse.getData()).orElseThrow(
                () -> new EntityNotFoundException("Không tìm thấy bài báo yêu thích",GlobalCode.ERROR_ENTITY_NOT_FOUND));

        favouriteRepository.delete(favourite);

        return favourite.getId();
    }

    @Override
    public PaginationResponse<FavouriteResponse> getFavouriteNewspaperForUser(String username, InfoPage infoPage) {
        Sort sort = infoPage.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(infoPage.getSortBy()).ascending() : Sort.by(infoPage.getSortBy()).descending();

        Pageable pageable = PageRequest.of(infoPage.getPage() - 1, infoPage.getLimit(),sort);

        Example<Favourite> example;
        Favourite exampleNewspaper = new Favourite();
        exampleNewspaper.setUsername(username);

        ExampleMatcher matcher = ExampleMatcher.matchingAny()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase();

        example = Example.of(exampleNewspaper,matcher);

        Page<Favourite> resultPage = favouriteRepository.findAll(example,pageable);

        PaginationResponse<FavouriteResponse> paginationResponse = new PaginationResponse<>();

        List<FavouriteResponse> collect = resultPage.getContent().stream().map(
                favourite -> convertToFavouriteResponse(favourite)).collect(Collectors.toList());

        paginationResponse.setContent(collect);
        paginationResponse.setLastPage(resultPage.isLast());
        paginationResponse.setTotalElements(resultPage.getTotalElements());
        paginationResponse.setPageSize(resultPage.getSize());
        paginationResponse.setPageNumber(resultPage.getNumber());
        paginationResponse.setTotalPages(resultPage.getTotalPages());

        return paginationResponse;
    }

    @Override
    public List<FavouriteResponse> getAllFavouriteOfUser(String username) {
        List<Favourite> list = favouriteRepository.findAllByUsername(username);
        List<FavouriteResponse> response = new ArrayList<>();
        for (Favourite favourite : list){
            response.add(convertToFavouriteResponse(favourite));
        }
        return response;
    }
}
