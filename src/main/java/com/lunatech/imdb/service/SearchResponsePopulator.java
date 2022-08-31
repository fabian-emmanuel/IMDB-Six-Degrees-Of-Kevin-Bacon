package com.lunatech.imdb.service;

import com.lunatech.imdb.model.name.Name;
import com.lunatech.imdb.model.title.Title;
import com.lunatech.imdb.model.crew.Crew;
import com.lunatech.imdb.model.principal.Principal;
import com.lunatech.imdb.model.ratings.Ratings;
import com.lunatech.imdb.payload.search.Person;
import com.lunatech.imdb.payload.search.PrincipalPerson;
import com.lunatech.imdb.payload.search.PrincipalPerson.PrincipalPersonBuilder;
import com.lunatech.imdb.payload.search.SearchResponse;
import com.lunatech.imdb.payload.search.SearchResponse.SearchResponseBuilder;
import com.lunatech.imdb.repository.name.NameRepository;
import com.lunatech.imdb.repository.crew.CrewRepository;
import com.lunatech.imdb.repository.principal.PrincipalRepository;
import com.lunatech.imdb.repository.ratings.RatingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SearchResponsePopulator {
    private final RatingsRepository ratingsRepository;
    private final CrewRepository crewRepository;
    private final NameRepository nameRepository;
    private final PrincipalRepository principalRepository;


    public SearchResponse populate(Title basic) {
        final String tconst = basic.getTConst();

        SearchResponseBuilder titleBuilder = addBasicInfo(basic, tconst);

        addRatingInfo(tconst, titleBuilder);
        addCrewInfo(tconst, titleBuilder);
        addPrincipalInfo(tconst, titleBuilder);

        return titleBuilder.build();
    }

    private SearchResponseBuilder addBasicInfo(Title result, String tconst) {
        return SearchResponse.builder()
                .id(tconst)
                .titleType(result.getTitleType())
                .primaryTitle(result.getPrimaryTitle())
                .originalTitle(result.getOriginalTitle())
                .isAdult(result.getIsAdult())
                .startYear(result.getStartYear())
                .endYear(result.getEndYear())
                .runtimeMinutes(result.getRuntimeMinutes())
                .genres(result.getGenres());
    }

    private void addRatingInfo(String tconst, SearchResponseBuilder titleBuilder) {
        final Optional<Ratings> ratings = ratingsRepository.findRatingsByTConst(tconst);
        ratings.ifPresent(rating -> {
            titleBuilder.averageRating(rating.getAverageRating());
            titleBuilder.numVotes(rating.getNumVotes());
        });
    }

    private void addCrewInfo(String tconst, SearchResponseBuilder resultBuilder) {
        final Optional<Crew> crew = crewRepository.findCrewByTConst(tconst);
        crew.ifPresent(c -> {
                c.getDirectorsIds()
                        .forEach(directorId -> nameRepository
                                .findNameByNConst(directorId)
                                .ifPresent(name -> {
                                    Person person = mapNameToPerson(name);
                                    resultBuilder.director(person);
                                }));

                c.getWritersIds()
                        .forEach(writerId -> nameRepository
                                .findNameByNConst(writerId)
                                .ifPresent(name -> {
                                    Person person = mapNameToPerson(name);
                                    resultBuilder.writer(person);
                                }));
        });
    }

    private void addPrincipalInfo(String tconst, SearchResponseBuilder titleBuilder) {
        List<Principal> principals = principalRepository.findPrincipalsByTconst(tconst);

        principals.forEach(principal -> {

            PrincipalPersonBuilder principalPersonBuilder = PrincipalPerson.builder()
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

            titleBuilder.principal(principalPersonBuilder.build());
        });
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
