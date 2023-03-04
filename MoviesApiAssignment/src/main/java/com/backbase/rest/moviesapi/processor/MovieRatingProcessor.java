package com.backbase.rest.moviesapi.processor;

import static com.backbase.rest.moviesapi.mapper.MovieRatingMapper.MOVIE_RATING_MAPPER_INSTANCE;

import javax.annotation.Resource;

import com.backbase.rest.moviesapi.config.EhCacheConfigurer;
import com.backbase.rest.moviesapi.director.LinkDirector;
import com.backbase.rest.moviesapi.domain.Movie.MovieData;
import com.backbase.rest.moviesapi.entities.MovieRating;
import com.backbase.rest.moviesapi.exception.NotFoundException;
import com.backbase.rest.moviesapi.repository.MovieRatingRepository;
import com.backbase.rest.moviesapi.service.MovieRatingService;
import com.backbase.rest.moviesapi.view.Link;
import com.backbase.rest.moviesapi.view.MovieDataView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MovieRatingProcessor {

    @Resource
    private MovieRatingService movieRatingService;

    @Resource
    private LinkDirector linkDirector;

    @Resource
    private EhCacheConfigurer ehCacheConfigurer;

    public MovieDataView getMovieDataView(String userKey, MovieData movieDetailBasedOnTitle, Double rating) {
        if (movieDetailBasedOnTitle != null) {
            String uniqueValue = movieDetailBasedOnTitle.getImdbId() + userKey;
            MovieRating uniquenessOfRating = isRatingUniqueForUserKeyAndImdbId(uniqueValue);
            /*if(!uniquenessOfRating)
            {
                throw new NotUniqueException("You have already given the rating for this movie!");
            }*/

            //To be stored in DB
            MovieRating updatedMovieRating;
            if (uniquenessOfRating == null) {
                MovieRating movieRating = MOVIE_RATING_MAPPER_INSTANCE.toEntity(movieDetailBasedOnTitle);
                movieRating.setImdbRating(rating);
                movieRating.setUserKey(userKey);
                movieRating.setUniqueValue(uniqueValue);
                updatedMovieRating = movieRatingService.save(movieRating);
                uniquenessOfRating = movieRating;
            }
            else {
                //to be updated in DB
                log.info("Record already exists, So going to update the record!");
                uniquenessOfRating.setImdbRating(rating);
                updatedMovieRating = movieRatingService.save(uniquenessOfRating);
            }

            MovieDataView movieDataView = MOVIE_RATING_MAPPER_INSTANCE.toView(uniquenessOfRating);
            addDeleteRatingLink(userKey, updatedMovieRating.getId(), movieDataView);
            ehCacheConfigurer.evictTopTenImdbMoviesCacheValues();//clearing the cache so that new top movie will calculated at real time for next
            return movieDataView;
        }
        else {
            log.error("The Title You sent it not available. Please try with different Name");
            throw new NotFoundException("The Title You sent it not available. Please try with different Name");
        }
    }

    public MovieRating isRatingUniqueForUserKeyAndImdbId(String uniqueValue) {
        return movieRatingService.findUniqueValue(uniqueValue);

    }

    public void addDeleteRatingLink(String user, long id, MovieDataView movieDataView) {
        movieDataView.setDeleteRatingLink(new Link(linkDirector.deleteRatingLink(user, id)));
    }

}
