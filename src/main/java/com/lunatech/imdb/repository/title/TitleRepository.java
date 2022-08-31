package com.lunatech.imdb.repository.title;

import com.lunatech.imdb.model.title.Title;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TitleRepository extends JpaRepository<Title, String>{
    @Query("select t from Title t " +
            "where t.primaryTitle like %:title% " +
            "or t.originalTitle like %:title% " +
            "order by t.tConst desc ")
    List<Title> searchByTitle(String title, Pageable pageable);

    @Query("select t from Title t " +
            "inner join Ratings r on t.tConst = r.tConst " +
            "where t.genres like %:genre% " +
            "order by r.numVotes desc, r.averageRating desc ")
    List<Title> findByGenre(String genre, Pageable pageable);

}