package com.backbase.rest.moviesapi.orchestrator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import javax.annotation.Resource;

import com.backbase.rest.moviesapi.config.EhCacheConfigurer;
import com.backbase.rest.moviesapi.domain.Movie.MovieData;
import com.backbase.rest.moviesapi.entities.MovieRating;
import com.backbase.rest.moviesapi.exception.NotFoundException;
import com.backbase.rest.moviesapi.form.ImdbIdRatingForm;
import com.backbase.rest.moviesapi.form.TitleRatingForm;
import com.backbase.rest.moviesapi.processor.MovieRatingProcessor;
import com.backbase.rest.moviesapi.repository.MovieRatingRepository;
import com.backbase.rest.moviesapi.service.MovieRatingService;
import com.backbase.rest.moviesapi.service.OpenMovieDatabaseService;
import com.backbase.rest.moviesapi.view.MovieDataView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MoviesRatingOrchestratorTest {
    @Mock
    private OpenMovieDatabaseService openMovieDatabaseService;

    @Mock
    private MovieRatingService movieRatingService;

    @Mock
    private MovieRatingProcessor movieRatingProcessor;

    @Mock
    private EhCacheConfigurer ehCacheConfigurer;

    @InjectMocks
    private MoviesRatingOrchestrator moviesRatingOrchestrator;

    @Test
    public void testGiveRatingBasedOnTitle() throws Exception {
        MovieData movieData = createMovieData();
        when(openMovieDatabaseService.getMovieDetailFromOmdbApi("TITLE", "title")).thenReturn(movieData);
        MovieDataView movieDataView = createMovieDataView();
        when(movieRatingProcessor.getMovieDataView(anyString(), any(), anyDouble())).thenReturn(movieDataView);
        TitleRatingForm titleRatingForm = new TitleRatingForm();
        titleRatingForm.setTitle("title");
        titleRatingForm.setRating(8.0);
        MovieDataView movieDataView1 = moviesRatingOrchestrator.giveRatingBasedOnTitle("vivek", titleRatingForm);

        assertEquals(movieDataView1.getImdbRating(), movieDataView.getImdbRating());
    }

    @Test
    public void testGiveRatingBasedOnImdb() throws Exception {
        MovieData movieData = createMovieData();
        when(openMovieDatabaseService.getMovieDetailFromOmdbApi("IMDB_ID", "1234")).thenReturn(movieData);
        MovieDataView movieDataView = createMovieDataView();
        when(movieRatingProcessor.getMovieDataView(anyString(), any(), anyDouble())).thenReturn(movieDataView);
        ImdbIdRatingForm imdbIdRatingForm = new ImdbIdRatingForm();
        imdbIdRatingForm.setImdbId("1234");
        imdbIdRatingForm.setRating(8.0);
        MovieDataView movieDataView1 = moviesRatingOrchestrator.giveRatingBasedOnImdb("vivek", imdbIdRatingForm);

        assertEquals(movieDataView1.getImdbRating(), movieDataView.getImdbRating());
    }


    @Test
    public void testDeleteRatingOnIdSuccess() throws Exception {
        when(movieRatingService.findById(1l)).thenReturn(java.util.Optional.of(new MovieRating()));
        when(movieRatingService.findResourceWithIdAndUser("vivek", 1l)).thenReturn(new MovieRating());
        doNothing().when(movieRatingService).deleteById(1l);
        doNothing().when(ehCacheConfigurer).evictTopTenImdbMoviesCacheValues();
        moviesRatingOrchestrator.deleteRatingOnId("vivek",1l);

    }

    @Test
    public void testDeleteRatingOnIdNotPresent() throws Exception {
        when(movieRatingService.findById(1l)).thenReturn(java.util.Optional.empty());
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            moviesRatingOrchestrator.deleteRatingOnId("vivek",1l);
        });
        assertEquals("The id You have sent is not present", exception.getMessage());
    }

    @Test
    public void testDeleteRatingOnIdNotLinkedToUser() throws Exception {
        when(movieRatingService.findById(1l)).thenReturn(java.util.Optional.of(new MovieRating()));
        when(movieRatingService.findResourceWithIdAndUser("vivek", 1l)).thenReturn(null);
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            moviesRatingOrchestrator.deleteRatingOnId("vivek",1l);
        });
        assertEquals("The Resource does not belong to this user. So You can delete it", exception.getMessage());

    }

    private MovieData createMovieData() {
        MovieData movieData = new MovieData();
        movieData.setAwardName("award");
        movieData.setBoxOffice("$123,456");
        movieData.setImdbId("1234");
        movieData.setImdbRating("7.0");
        movieData.setTitle("title");

        return movieData;

    }

    private MovieDataView createMovieDataView() {
        MovieDataView movieDataView = new MovieDataView();
        movieDataView.setAwardName("award");
        movieDataView.setBoxOffice("$123,456");
        movieDataView.setImdbId("1234");
        movieDataView.setImdbRating("7.0");
        movieDataView.setTitle("title");
        return movieDataView;

    }
}
