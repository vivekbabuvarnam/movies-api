package com.backbase.rest.moviesapi.orchestrator;

import javax.annotation.Resource;

import com.backbase.rest.moviesapi.config.EhCacheConfigurer;
import com.backbase.rest.moviesapi.domain.Movie.MovieData;
import com.backbase.rest.moviesapi.exception.NotFoundException;
import com.backbase.rest.moviesapi.form.ImdbIdRatingForm;
import com.backbase.rest.moviesapi.form.TitleRatingForm;
import com.backbase.rest.moviesapi.processor.MovieRatingProcessor;
import com.backbase.rest.moviesapi.repository.MovieRatingRepository;
import com.backbase.rest.moviesapi.service.MovieRatingService;
import com.backbase.rest.moviesapi.service.OpenMovieDatabaseService;
import com.backbase.rest.moviesapi.view.MovieDataView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MoviesRatingOrchestrator {

    @Resource
    private OpenMovieDatabaseService openMovieDatabaseService;

    @Resource
    private MovieRatingService movieRatingService;

    @Resource
    private MovieRatingProcessor movieRatingProcessor;

    @Resource
    private EhCacheConfigurer ehCacheConfigurer;

    public MovieDataView giveRatingBasedOnTitle(String userKey, TitleRatingForm titleRatingForm) throws NotFoundException {
        MovieData movieDetailBasedOnTitle = openMovieDatabaseService.getMovieDetailFromOmdbApi("TITLE", titleRatingForm.getTitle());
        Double rating = titleRatingForm.getRating();

        return movieRatingProcessor.getMovieDataView(userKey, movieDetailBasedOnTitle, rating);
    }

    public MovieDataView giveRatingBasedOnImdb(String userKey, ImdbIdRatingForm imdbIdRatingForm) throws NotFoundException {
        MovieData movieDetailBasedOnImdb = openMovieDatabaseService.getMovieDetailFromOmdbApi("IMDB_ID", imdbIdRatingForm.getImdbId());
        Double rating = imdbIdRatingForm.getRating();
        return movieRatingProcessor.getMovieDataView(userKey, movieDetailBasedOnImdb, rating);

    }

    public void deleteRatingOnId(String userKey, Long id) {
        if (movieRatingService.findById(id).isPresent()) {
            if(movieRatingService.findResourceWithIdAndUser(userKey, id) != null) { // delete it only if the id belongs to this user
                movieRatingService.deleteById(id);
                ehCacheConfigurer.evictTopTenImdbMoviesCacheValues();//clearing the cache so that new top movie will calculated at real time for next
            }
            else
            {
                log.error("The Resource does not belong to this user. So no delete!");
                throw new NotFoundException("The Resource does not belong to this user. So You can delete it");
            }
        }
        else {
            log.error("The id You have sent is not present!");
            throw new NotFoundException("The id You have sent is not present");
        }
    }
}
