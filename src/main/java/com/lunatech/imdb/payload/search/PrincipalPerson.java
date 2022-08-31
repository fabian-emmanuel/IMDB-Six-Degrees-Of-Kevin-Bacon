package com.lunatech.imdb.payload.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PrincipalPerson {
    private String titleId;
    private Long ordering;
    private Person personInfo;
    private String category;
    private String job;
    private String characters;
}
