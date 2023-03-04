package com.backbase.rest.moviesapi.service.impl;

import javax.annotation.Resource;

import java.util.List;
import java.util.Optional;

import com.backbase.rest.moviesapi.entities.MovieRating;
import com.backbase.rest.moviesapi.repository.MovieRatingRepository;
import com.backbase.rest.moviesapi.service.MovieRatingService;
import org.springframework.stereotype.Service;

@Service
public class MovieRatingServiceImpl implements MovieRatingService {

    @Resource
    private MovieRatingRepository movieRatingRepository;

    @Override
    public MovieRating findUniqueValue(String uniqueValue) {
        return movieRatingRepository.findUniqueValue(uniqueValue);
    }

    @Override
    public MovieRating findResourceWithIdAndUser(String userKey, Long id) {
        return movieRatingRepository.findResourceWithIdAndUser(userKey, id);
    }

    @Override
    public Optional<MovieRating> findById(Long id) {
        return movieRatingRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        movieRatingRepository.deleteById(id);
    }

    @Override
    public List<MovieRating> findAll() {
        return movieRatingRepository.findAll();
    }

    @Override
    public MovieRating save(MovieRating movieRating) {
        return movieRatingRepository.save(movieRating);
    }
}
