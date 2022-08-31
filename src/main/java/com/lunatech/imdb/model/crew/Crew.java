package com.lunatech.imdb.model.crew;

import com.lunatech.imdb.model.title.Title;
import lombok.*;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity
@ToString
@Getter
@Setter
@Table(name = "title_crew")
@AllArgsConstructor
@NoArgsConstructor
public class Crew {
    @Id
    @Column(name = "tconst")
    private String tConst;

    @OneToOne
    @JoinColumn(name = "tconst", nullable = false)
    private Title title;

    @Column(name = "directors")
    private String directors;

    @Column(name = "writers")
    private String writers;

    public List<String> getDirectorsIds() {
        return directors == null ? Collections.emptyList() : List.of(directors.split(","));
    }

    public List<String> getWritersIds() {
        return writers == null ? Collections.emptyList() : List.of(writers.split(","));
    }
}
