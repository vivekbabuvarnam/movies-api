package com.backbase.rest.moviesapi.processor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.annotation.Resource;

import com.backbase.rest.moviesapi.config.EhCacheConfigurer;
import com.backbase.rest.moviesapi.director.LinkDirector;
import com.backbase.rest.moviesapi.domain.Movie.MovieData;
import com.backbase.rest.moviesapi.entities.MovieRating;
import com.backbase.rest.moviesapi.exception.NotFoundException;
import com.backbase.rest.moviesapi.orchestrator.MoviesRatingOrchestrator;
import com.backbase.rest.moviesapi.repository.MovieRatingRepository;
import com.backbase.rest.moviesapi.service.MovieRatingService;
import com.backbase.rest.moviesapi.view.MovieDataView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MovieRatingProcessorTest {

    @Mock
    private MovieRatingService movieRatingService;

    @Mock
    private LinkDirector linkDirector;

    @Mock
    private EhCacheConfigurer ehCacheConfigurer;

    @InjectMocks
    private MovieRatingProcessor movieRatingProcessor;

    @Spy
    private MovieDataView movieDataView;

    @Test
    public void testIsRatingUniqueForUserKeyAndImdbId() throws Exception {
        MovieRating movieRating= createMovieRating();
        when(movieRatingService.findUniqueValue("unique")).thenReturn(movieRating);
        MovieRating unique = movieRatingProcessor.isRatingUniqueForUserKeyAndImdbId("unique");
        assertEquals(unique.getImdbId(),movieRating.getImdbId());

    }

    @Test
    public void testIsRatingUniqueForUserKeyAndImdbIdNull() throws Exception {
        when(movieRatingService.findUniqueValue("unique")).thenReturn(null);
        MovieRating unique = movieRatingProcessor.isRatingUniqueForUserKeyAndImdbId("unique");
        assertNull(unique);

    }
    @Test
    public void testAddDeleteRatingLink() throws Exception {
        when(linkDirector.deleteRatingLink(anyString(),anyLong())).thenReturn("localhost");
        movieRatingProcessor.addDeleteRatingLink(anyString(),anyLong(), movieDataView);
        verify(movieDataView, times(1)).setDeleteRatingLink(any());

    }


    @Test
    public void testGetMovieDataView() throws Exception {
        MovieRating movieRating= createMovieRating();
        doNothing().when(ehCacheConfigurer).evictTopTenImdbMoviesCacheValues();
        when(movieRatingService.save(any())).thenReturn(movieRating);
        when(movieRatingService.findUniqueValue("1234vivek")).thenReturn(movieRating);
        MovieDataView movieDataView = movieRatingProcessor.getMovieDataView("vivek",createMovieData(),1d);
        assertNotNull(movieDataView);
    }

    @Test
    public void testGetMovieDataViewExisting() throws Exception {
        MovieRating movieRating= createMovieRating();
        doNothing().when(ehCacheConfigurer).evictTopTenImdbMoviesCacheValues();
        when(movieRatingService.save(any())).thenReturn(movieRating);
        when(movieRatingService.findUniqueValue("1234vivek")).thenReturn(null);
        MovieDataView movieDataView = movieRatingProcessor.getMovieDataView("vivek",createMovieData(),1d);
        assertNotNull(movieDataView);

    }

    @Test
    public void testGetMovieDataViewNotFound() throws Exception {

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            movieRatingProcessor.getMovieDataView("vivek",null,1d);
        });
        assertEquals("The Title You sent it not available. Please try with different Name", exception.getMessage());


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

    private MovieRating createMovieRating(){
        MovieRating movieRating1 = new MovieRating();
        movieRating1.setBoxOffice("$123,456");
        movieRating1.setImdbId("1234");
        movieRating1.setImdbRating(7.0);
        movieRating1.setTitle("title1");
        movieRating1.setUniqueValue("1234abc");
        movieRating1.setUserKey("abc");
        movieRating1.setId(1);

        return movieRating1;
    }

}
