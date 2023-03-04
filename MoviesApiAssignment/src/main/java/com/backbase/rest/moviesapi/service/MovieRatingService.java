package com.backbase.rest.moviesapi.service;

import java.util.List;
import java.util.Optional;

import com.backbase.rest.moviesapi.entities.MovieRating;

public interface MovieRatingService {
    MovieRating findUniqueValue(String uniqueValue);

    MovieRating findResourceWithIdAndUser(String userKey, Long id);

    Optional<MovieRating> findById(Long id);

    void deleteById(Long id);

    List<MovieRating> findAll();

    MovieRating save(MovieRating movieRating);
}
