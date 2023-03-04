package com.backbase.rest.moviesapi.processor;

import javax.annotation.Resource;
import java.util.List;

import com.backbase.rest.moviesapi.director.LinkDirector;
import com.backbase.rest.moviesapi.entities.MovieRating;
import com.backbase.rest.moviesapi.repository.MovieRatingRepository;
import com.backbase.rest.moviesapi.service.MovieRatingService;
import com.backbase.rest.moviesapi.view.Link;
import com.backbase.rest.moviesapi.view.MovieDataView;
import org.springframework.stereotype.Component;

@Component
public class MovieInfoProcessor {

    @Resource
    private MovieRatingService movieRatingService;

    @Resource
    private LinkDirector linkDirector;

    public List<MovieRating> getListOfAllRatedMovies() {
        return movieRatingService.findAll();
    }

    public void addHaeteousLinks(String user, MovieDataView movieDataView) {

        movieDataView.setViewTopTenMovieLink(new Link(linkDirector.addTopTenMovieLink(user)));

        movieDataView.setAddRatingLink(new Link(linkDirector.addRatingLink(user)));

        movieDataView.setUploadBestPictureOscarWonCsvFeed(new Link(linkDirector.addCsvFileUploadLink()));
    }

}
