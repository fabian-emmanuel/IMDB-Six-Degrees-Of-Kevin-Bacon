package com.lunatech.imdb.model.principal;

import com.lunatech.imdb.model.name.Name;
import com.lunatech.imdb.model.title.Title;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "title_principals")
@IdClass(PrincipalIds.class)
public class Principal {
    @Id
    @Column(name = "tconst", insertable = false, updatable = false)
    private String tconst;

    @Id
    @Column(name = "ordering")
    private Long ordering;

    @Id
    @Column(name = "nconst", insertable = false, updatable = false)
    private String nconst;

    @ManyToOne
    @JoinColumn(name = "tconst", nullable = false)
    private Title title;

    @OneToOne
    @JoinColumn(name = "nconst", nullable = false)
    private Name name;

    @Column(name = "category")
    private String category;

    @Column(name = "job")
    private String job;

    @Column(name = "characters")
    private String characters;
}
