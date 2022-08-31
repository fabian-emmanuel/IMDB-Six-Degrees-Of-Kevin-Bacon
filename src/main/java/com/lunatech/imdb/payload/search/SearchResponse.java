package com.lunatech.imdb.payload.search;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchResponse {
    // BASIC - INFO
    private String id;
    private String titleType;
    private String primaryTitle;
    private String originalTitle;
    private boolean isAdult;
    private String startYear;
    private String endYear;
    private Integer runtimeMinutes;
    private String genres;
    // RATING - INFO
    private Double averageRating;
    private Long numVotes;
    // CREW - INFO
    @Singular
    private List<Person> directors;
    @Singular
    private List<Person> writers;
    // PRINCIPAL - INFO
    @Singular
    private List<PrincipalPerson> principals;
}
