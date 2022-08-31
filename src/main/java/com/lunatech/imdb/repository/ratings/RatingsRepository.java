package com.lunatech.imdb.repository.ratings;

import com.lunatech.imdb.model.ratings.Ratings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingsRepository extends JpaRepository<Ratings, String> {
    @Query("select r from Ratings r where r.tConst = ?1 ")
    Optional<Ratings> findRatingsByTConst(String s);
}