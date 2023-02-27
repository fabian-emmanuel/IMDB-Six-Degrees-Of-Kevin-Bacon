package com.lunatech.imdb.service.title;

import com.lunatech.imdb.exceptions.InvalidRequestException;
import com.lunatech.imdb.model.crew.Crew;
import com.lunatech.imdb.model.name.Name;
import com.lunatech.imdb.model.principal.Principal;
import com.lunatech.imdb.payload.search.Person;
import com.lunatech.imdb.payload.search.PrincipalPerson;
import com.lunatech.imdb.payload.search.SearchCriteria;
import com.lunatech.imdb.payload.search.SearchResponse;
import com.lunatech.imdb.payload.search.MovieSearch;
import com.lunatech.imdb.repository.name.NameRepository;
import com.lunatech.imdb.repository.principal.PrincipalRepository;
import com.lunatech.imdb.repository.title.TitleRepository;
import com.lunatech.imdb.util.PageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static com.lunatech.imdb.payload.search.SearchResponse.*;

@Service
@RequiredArgsConstructor
public class TitleServiceImpl implements TitleService {
    private final NameRepository nameRepository;
    private final PrincipalRepository principalRepository;
    private final TitleRepository titleRepository;

    @Value("${records-limit}")
    private int recordsLimit;


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
        Collection<MovieSearch> mv = this.titleRepository.searchMovieByTitle(title, Pageable.ofSize(recordsLimit)).getContent();
        return processSearchResponse(pageNo, pageSize, mv);
    }

    private Page<SearchResponse> fetchResponseDetailsBasedOnGenre(int pageNo, int pageSize, String genre) {
        Collection<MovieSearch> mv = this.titleRepository.searchMovieByGenre(genre, Pageable.ofSize(recordsLimit)).getContent();
        return processSearchResponse(pageNo, pageSize, mv);
    }

    private PageImpl<SearchResponse> processSearchResponse(int pageNo, int pageSize, Collection<MovieSearch> mv) {
        List<SearchResponse> searchResponseList = new ArrayList<>();

        SearchResponseBuilder resultBuilder = SearchResponse.builder();

        populateSearchResponse(mv, searchResponseList, resultBuilder);

        return getPaginatedSearchResponses(pageNo, pageSize, searchResponseList);
    }
    private void populateSearchResponse(Collection<MovieSearch> mv, List<SearchResponse> searchResponseList, SearchResponseBuilder resultBuilder) {
        mv.forEach(item -> {
            populateBasicInfo(resultBuilder, item);
            populateCrewInfo(resultBuilder, item);
            populatePrincipalInfo(resultBuilder, item);
            searchResponseList.add(resultBuilder.build());
        });
    }

    private static void populateBasicInfo(SearchResponseBuilder resultBuilder, MovieSearch item) {
        resultBuilder
                .id(item.getId())
                .titleType(item.getTitleType())
                .primaryTitle(item.getPrimaryTitle())
                .originalTitle(item.getOriginalTitle())
                .isAdult(item.getIsAdult())
                .startYear(item.getStartYear())
                .endYear(item.getEndYear())
                .runtimeMinutes(item.getRuntimeMinutes())
                .averageRating(item.getAverageRating())
                .numVotes(item.getNumVotes())
                .genres(item.getGenres());
    }

    private void populatePrincipalInfo(SearchResponseBuilder resultBuilder, MovieSearch item) {
        List<Principal> principals = principalRepository.findPrincipalsByTconst(item.getId());

        principals.forEach(principal -> {
            PrincipalPerson.PrincipalPersonBuilder principalPersonBuilder = PrincipalPerson.builder()
                    .titleId(principal.getTconst())
                    .ordering(principal.getOrdering())
                    .category(principal.getCategory())
                    .job(principal.getJob())
                    .characters(principal.getCharacters());

            Optional.of(principal.getName())
                    .ifPresent(name -> {
                        Person person = mapNameToPerson(name);
                        principalPersonBuilder.personInfo(person);
                    });

            resultBuilder.principal(principalPersonBuilder.build());
        });
    }

    private void populateCrewInfo(SearchResponseBuilder resultBuilder, MovieSearch item) {
        Optional<Crew> crew = Optional.of(item.getCrew());
        crew.ifPresent(c -> {
            var directors = getAllNamesByIds(c.getDirectorsIds())
                    .parallelStream()
                    .map(this::mapNameToPerson)
                    .toList();

            var writers = getAllNamesByIds(c.getWritersIds())
                    .parallelStream()
                    .map(this::mapNameToPerson)
                    .toList();

            resultBuilder.directors(directors);
            resultBuilder.writers(writers);

        });
    }

    private List<Name> getAllNamesByIds(List<String> ids) {
        return nameRepository.findAllById(ids);
    }

    private static PageImpl<SearchResponse> getPaginatedSearchResponses(int pageNo, int pageSize, List<SearchResponse> searchResponseList) {
        Pageable pageable = PageUtil.normalisePageRequest(pageNo, pageSize);

        if (searchResponseList.isEmpty()) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        return new PageImpl<>(searchResponseList, pageable, searchResponseList.size());
    }

    private String capitalizeFirstLetterIfLowercase(String str){
        return Character.isLowerCase(str.charAt(0))
                ? str.substring(0, 1).toUpperCase() + str.substring(1)
                : str;
    }

    private Person mapNameToPerson(Name n) {
        return Person.builder()
                .id(n.getNConst())
                .primaryName(n.getPrimaryName())
                .birthYear(n.getBirthYear())
                .deathYear(n.getDeathYear())
                .primaryProfessions(n.getPrimaryProfessionsSplit())
                .knownForTitles(n.getKnownForTitlesSplit())
                .build();
    }

}
