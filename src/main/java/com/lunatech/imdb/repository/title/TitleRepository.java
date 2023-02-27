package com.lunatech.imdb.repository.title;

import com.lunatech.imdb.model.title.Title;
import com.lunatech.imdb.payload.search.MovieSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TitleRepository extends JpaRepository<Title, String>{
    @Query(value = "SELECT new com.lunatech.imdb.payload.search.MovieSearch(tb.tConst, tb.titleType, tb.isAdult, tb.primaryTitle, tb.originalTitle, tb.genres, tb.runtimeMinutes, tb.startYear, tb.endYear, r.averageRating, r.numVotes, tc ) " +
                   "from Title tb " +
                   "JOIN FETCH Crew tc ON tb.tConst = tc.tConst " +
                   "JOIN FETCH Ratings r ON tb.tConst = r.tConst " +
                   "where tb.primaryTitle LIKE :title% or tb.originalTitle LIKE :title%")
    Page<MovieSearch> searchMovieByTitle(String title, Pageable pageable);

    @Query(value = "SELECT new com.lunatech.imdb.payload.search.MovieSearch(tb.tConst, tb.titleType, tb.isAdult, tb.primaryTitle, tb.originalTitle, tb.genres, tb.runtimeMinutes, tb.startYear, tb.endYear, r.averageRating, r.numVotes, tc ) " +
                   "from Title tb " +
                   "JOIN FETCH Crew tc ON tb.tConst = tc.tConst " +
                   "JOIN Ratings r ON tb.tConst = r.tConst " +
                   "where tb.genres like %:genre% " +
                   "order by r.numVotes desc, r.averageRating desc ")
    Page<MovieSearch> searchMovieByGenre(String genre, Pageable pageable);

}