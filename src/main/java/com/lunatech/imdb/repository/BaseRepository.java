package com.lunatech.imdb.repository;

import com.lunatech.imdb.model.title.Title;
import com.lunatech.imdb.repository.title.TitleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BaseRepository {
    private final TitleRepository titleRepository;

    @Value("${records-limit}")
    private int recordsLimit;


    public List<Title> findByMovieTitle(String title) {
        return this.titleRepository.searchByTitle(title, Pageable.ofSize(recordsLimit));
    }

    public List<Title> findByGenre(String genre) {
        return this.titleRepository.findByGenre(genre, Pageable.ofSize(recordsLimit));
    }
}
