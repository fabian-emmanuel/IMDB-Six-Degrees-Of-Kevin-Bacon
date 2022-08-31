package com.lunatech.imdb.repository.name;

import com.lunatech.imdb.model.name.Name;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NameRepository extends JpaRepository<Name, String> {
    @Query("select n from Name n where n.nConst = ?1 " +
           "order by n.nConst desc")
    Optional<Name> findNameByNConst(String s);
}