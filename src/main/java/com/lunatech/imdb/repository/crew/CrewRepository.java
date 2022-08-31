package com.lunatech.imdb.repository.crew;

import com.lunatech.imdb.model.crew.Crew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CrewRepository extends JpaRepository<Crew, String> {
    @Query("select c from Crew c where c.tConst = ?1 " +
           "order by c.tConst desc")
    Optional<Crew> findCrewByTConst(String s);
}