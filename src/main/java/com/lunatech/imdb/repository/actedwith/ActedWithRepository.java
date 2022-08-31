package com.lunatech.imdb.repository.actedwith;

import com.lunatech.imdb.model.actedwith.ActedWith;
import com.lunatech.imdb.payload.dos.IDegreeOfSeparation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ActedWithRepository extends JpaRepository<ActedWith, String> {

    String QUERY = """
            WITH RECURSIVE kevinbacon_table AS(
            SELECT
              f.actor_name,
              f.other_actor_name,
              0 AS bacon_no
            FROM acted_with AS f
            WHERE actor_name =:target
            
            UNION
            
            SELECT
              p.actor_name,
              f.other_actor_name,
              p.bacon_no + 1
            FROM acted_with AS f, kevinbacon_table AS p
            WHERE p.other_actor_name = f.actor_name
            AND p.bacon_no < 6
            )
            
            SELECT
              other_actor_name AS actor,
              actor_name AS connectedto,
              bacon_no AS bacon
            FROM kevinbacon_table
            WHERE other_actor_name =:source
            LIMIT 1
            """;
    @Query(value = QUERY, nativeQuery = true)
    IDegreeOfSeparation findDegreeOfSeparation(String source, String target);

    @Query("select count(a)>0 from ActedWith a where a.actor_name =:name or a.other_actor_name =:name")
    boolean existsByActorName(String name);
}
