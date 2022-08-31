package com.lunatech.imdb.model.actedwith;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "acted_with")
@IdClass(ActedWithIds.class)
public class ActedWith {
    @Id
    private String actor_id;
    @Id
    private String other_actor_id;
    private String actor_name;
    private String movie_title;
    private String other_actor_name;
}
