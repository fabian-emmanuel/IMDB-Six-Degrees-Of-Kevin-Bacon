package com.lunatech.imdb.payload.search;

import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private String id;
    private String primaryName;
    private String birthYear;
    private String deathYear;
    @Singular
    private List<String> primaryProfessions;
    @Singular
    private List<String> knownForTitles;
}
