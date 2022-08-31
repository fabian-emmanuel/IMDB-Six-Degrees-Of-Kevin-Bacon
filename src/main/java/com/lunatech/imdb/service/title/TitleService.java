package com.lunatech.imdb.service.title;

import com.lunatech.imdb.payload.search.SearchCriteria;
import com.lunatech.imdb.payload.search.SearchResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TitleService {
    Page<SearchResponse> processSearchResult(int pageNo, int page, List<SearchCriteria> searchCriteria);
}
