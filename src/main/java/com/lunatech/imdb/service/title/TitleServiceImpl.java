package com.lunatech.imdb.service.title;

import com.lunatech.imdb.exceptions.InvalidRequestException;
import com.lunatech.imdb.model.title.Title;
import com.lunatech.imdb.payload.search.SearchCriteria;
import com.lunatech.imdb.payload.search.SearchResponse;
import com.lunatech.imdb.repository.BaseRepository;
import com.lunatech.imdb.service.SearchResponsePopulator;
import com.lunatech.imdb.util.PageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

@Service
@Log
@RequiredArgsConstructor
public class TitleServiceImpl implements TitleService {
    private final BaseRepository baseRepository;
    private final SearchResponsePopulator searchResponsePopulator;

    @Override
    @Transactional
    public Page<SearchResponse> processSearchResult(int pageNo, int pageSize, List<SearchCriteria> searchCriteria) {
        String title;
        String genre;

        for (SearchCriteria criteria : searchCriteria) {
            if (criteria.getKey().equals("title") && StringUtils.isNotEmpty(String.valueOf(criteria.getValue()))){
                title = String.valueOf(criteria.getValue());
                title = capitalizeFirstLetterIfLowercase(title);
                return fetchResponseDetailsBasedOnTitle(pageNo, pageSize, title);
            }

            if (criteria.getKey().equals("genre") && StringUtils.isNotEmpty(String.valueOf(criteria.getValue()))){
                genre = String.valueOf(criteria.getValue());
                genre = capitalizeFirstLetterIfLowercase(genre);
                return fetchResponseDetailsBasedOnGenre(pageNo, pageSize, genre);
            }
        }
        throw new InvalidRequestException("Invalid search criteria");
    }

    private Page<SearchResponse> fetchResponseDetailsBasedOnTitle(int pageNo, int pageSize, String title) {
        return CompletableFuture
                .supplyAsync(() -> baseRepository.findByMovieTitle(title))
                .thenApplyAsync(titles -> {
                    Pageable pageable = PageUtil.normalisePageRequest(pageNo, pageSize);
                    return processSearchResponse(pageable, titles);
                })
                .handleAsync((data, ex) -> {
                    if (Objects.nonNull(ex)) {
                        log.log(Level.SEVERE, "Unexpected Error occurred", ex);
                    }
                    return data;
                }).join();
    }

    private Page<SearchResponse> fetchResponseDetailsBasedOnGenre(int pageNo, int pageSize, String genre) {
        return CompletableFuture
                .supplyAsync(() -> baseRepository.findByGenre(genre))
                .thenApplyAsync(titles -> {
                    Pageable pageable = PageUtil.normalisePageRequest(pageNo, pageSize);
                    return processSearchResponse(pageable, titles);
                })
                .handleAsync((data, ex) -> {
                    if (Objects.nonNull(ex)) {
                        log.log(Level.SEVERE, "Unexpected Error occurred", ex);
                    }
                    return data;
                })
                .join();
    }

    private Page<SearchResponse> processSearchResponse(Pageable pageable, List<Title> results) {

        if (results.isEmpty()) {
            return new PageImpl<>(Collections.emptyList(), pageable,0);
        }

        List<SearchResponse> response = results.stream().parallel()
                .map(searchResponsePopulator::populate)
                .toList();

        return new PageImpl<>(response, pageable, response.size());
    }

    private String capitalizeFirstLetterIfLowercase(String str){
        return Character.isLowerCase(str.charAt(0))
                ? str.substring(0, 1).toUpperCase() + str.substring(1)
                : str;
    }
}
