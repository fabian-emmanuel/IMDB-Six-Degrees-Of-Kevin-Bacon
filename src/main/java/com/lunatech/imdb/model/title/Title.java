package com.lunatech.imdb.model.title;

import com.lunatech.imdb.model.crew.Crew;
import com.lunatech.imdb.model.principal.Principal;
import com.lunatech.imdb.model.ratings.Ratings;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "title_basics")
public class Title {
    @Id
    @Column(name = "tconst")
    private String tConst;

    @Column(name = "titletype")
    private String titleType;

    @Column(name = "primarytitle")
    private String primaryTitle;

    @Column(name = "originaltitle")
    private String originalTitle;

    @Column(name = "isadult")
    private Boolean isAdult;

    @Column(name = "startyear")
    private String startYear;

    @Column(name = "endyear")
    private String endYear;

    @Column(name = "runtimeminutes")
    private Integer runtimeMinutes;

    @Column(name = "genres")
    private String genres;
}
