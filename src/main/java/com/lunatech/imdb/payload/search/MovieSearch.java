package com.lunatech.imdb.payload.search;

import com.lunatech.imdb.model.crew.Crew;
import lombok.Value;

@Value
public class MovieSearch {
    String id;
    String titleType;
    Boolean isAdult;
    String primaryTitle;
    String originalTitle;
    String genres;
    Integer runtimeMinutes;
    String startYear;
    String endYear;
    Double averageRating;
    Long numVotes;
    Crew crew;
}
