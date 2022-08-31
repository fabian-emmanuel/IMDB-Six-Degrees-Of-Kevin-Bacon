package com.lunatech.imdb.model.name;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "name_basics")
public class Name {
    @Id
    @Column(name = "nconst")
    private String nConst;

    @Column(name = "primaryname")
    private String primaryName;

    @Column(name = "birthyear")
    private String birthYear;

    @Column(name = "deathyear")
    private String deathYear;

    @Column(name = "primaryprofession")
    private String primaryProfession;

    @Column(name = "knownfortitles")
    private String knownForTitles;

    public List<String> getPrimaryProfessionsSplit() {
        return primaryProfession == null
                ? Collections.emptyList()
                : List.of(this.primaryProfession.split(","));
    }

    public List<String> getKnownForTitlesSplit() {
        return this.knownForTitles == null
                ? Collections.emptyList()
                : List.of(this.knownForTitles.split(","));
    }
}
