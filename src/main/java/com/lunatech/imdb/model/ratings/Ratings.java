package com.lunatech.imdb.model.ratings;

import com.lunatech.imdb.model.title.Title;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "title_ratings")
@AllArgsConstructor
@NoArgsConstructor
public class Ratings {
    @Id
    @Column(name = "tconst")
    private String tConst;

    @OneToOne
    @JoinColumn(name = "tconst", nullable = false)
    private Title title;

    @Column(name = "averagerating")
    private Double averageRating;

    @Column(name = "numvotes")
    private Long numVotes;

}
