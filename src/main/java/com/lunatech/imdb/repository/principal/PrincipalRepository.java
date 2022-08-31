package com.lunatech.imdb.repository.principal;

import com.lunatech.imdb.model.principal.Principal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrincipalRepository extends JpaRepository<Principal, String> {
    @Query("select p from Principal p " +
            "left join fetch p.name n " +
            "where p.tconst = ?1 " +
            "order by p.tconst desc, p.ordering asc ")
    List<Principal> findPrincipalsByTconst(String s);

}