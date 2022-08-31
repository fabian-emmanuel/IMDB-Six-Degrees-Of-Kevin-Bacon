package com.lunatech.imdb.controller;

import com.lunatech.imdb.apiresponse.ApiDataResponse;
import com.lunatech.imdb.payload.dos.DegreeOfSeparationRequest;
import com.lunatech.imdb.payload.dos.DegreeOfSeparationResponse;
import com.lunatech.imdb.payload.search.SearchCriteria;
import com.lunatech.imdb.payload.search.SearchResponse;
import com.lunatech.imdb.service.dos.DegreeOfSeparationService;
import com.lunatech.imdb.service.title.TitleService;
import com.lunatech.imdb.util.ApiResponseUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Tag(name = "Search Controller")
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ImdbController {
    private final TitleService titleService;
    private final DegreeOfSeparationService degreeOfSeparationService;

    @GetMapping("/search")
    public ResponseEntity<ApiDataResponse<Page<SearchResponse>>> retrieveEngineerReviews(
            @RequestParam(name="page" ,defaultValue = "0") int pageNo,
            @RequestParam(name="size" ,defaultValue = "10") int pageSize,
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "genre", required = false) String genre) {

        List<SearchCriteria> searchCriteria = new ArrayList<>();

        if (!StringUtils.isEmpty(title)) {
            searchCriteria.add(new SearchCriteria("title", title));
        }

        if (!StringUtils.isEmpty(genre)) {
            searchCriteria.add(new SearchCriteria("genre", genre));
        }
        Page<SearchResponse> result = this.titleService.processSearchResult(pageNo, pageSize, searchCriteria);
        return ApiResponseUtil.response(HttpStatus.OK, result, "Resources retrieved successfully");
    }

    @PostMapping("/degree-of-separation")
    public ResponseEntity<ApiDataResponse<DegreeOfSeparationResponse>>
    retrieveDegreeOfSeparation(@RequestBody DegreeOfSeparationRequest degreeOfSeparationRequest) {

        DegreeOfSeparationResponse result = this.degreeOfSeparationService
                .processDegreeOfSeparation(degreeOfSeparationRequest);

        return ApiResponseUtil.response(HttpStatus.OK, result, "Resources retrieved successfully");
    }

}
