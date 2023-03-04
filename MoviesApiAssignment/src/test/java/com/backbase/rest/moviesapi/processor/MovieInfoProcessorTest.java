package com.backbase.rest.moviesapi.processor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import com.backbase.rest.moviesapi.director.LinkDirector;
import com.backbase.rest.moviesapi.entities.MovieRating;
import com.backbase.rest.moviesapi.repository.MovieRatingRepository;
import com.backbase.rest.moviesapi.service.MovieRatingService;
import com.backbase.rest.moviesapi.view.MovieDataView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MovieInfoProcessorTest {
    @Mock
    private MovieRatingService movieRatingService;

    @Mock
    private LinkDirector linkDirector;

    @InjectMocks
    private MovieInfoProcessor movieInfoProcessor;

    @Test
    public void testGetListOfAllRatedMovies() throws Exception {
        List<MovieRating> movieRatingData = createMovieRatingData();
        when(movieRatingService.findAll()).thenReturn(movieRatingData);
        List<MovieRating> listOfAllRatedMovies = movieInfoProcessor.getListOfAllRatedMovies();
        assertEquals(listOfAllRatedMovies.size(),movieRatingData.size());

    }

    @Test
    public void testAddHaeteousLinks() throws Exception {
        movieInfoProcessor.addHaeteousLinks("vivek",createMovieDataView());
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
    private List<MovieRating> createMovieRatingData() {
        List<MovieRating> lstMovieRating = new ArrayList<>();
        MovieRating movieRating1 = new MovieRating();
        movieRating1.setBoxOffice("$123,456");
        movieRating1.setImdbId("1234");
        movieRating1.setImdbRating(7.0);
        movieRating1.setTitle("title1");
        movieRating1.setUniqueValue("1234abc");
        movieRating1.setUserKey("abc");
        movieRating1.setId(1);

        MovieRating movieRating11 = new MovieRating();
        movieRating11.setBoxOffice("$123,979");
        movieRating11.setImdbId("1234");
        movieRating11.setImdbRating(6.5);
        movieRating11.setTitle("title1");
        movieRating11.setUniqueValue("1234xyz");
        movieRating11.setUserKey("xyz");
        movieRating11.setId(5);

        MovieRating movieRating2 = new MovieRating();
        movieRating2.setBoxOffice("$225,897");
        movieRating2.setImdbId("4321");
        movieRating2.setImdbRating(6.0);
        movieRating2.setTitle("title2");
        movieRating2.setUniqueValue("4311cba");
        movieRating2.setUserKey("cba");
        movieRating2.setId(2);

        MovieRating movieRating3 = new MovieRating();
        movieRating3.setBoxOffice("N/A");
        movieRating3.setImdbId("8");
        movieRating3.setImdbRating(8.0);
        movieRating3.setTitle("title3");
        movieRating3.setUniqueValue("9874xys");
        movieRating3.setUserKey("xys");
        movieRating3.setId(3);

        lstMovieRating.add(movieRating1);
        lstMovieRating.add(movieRating11);
        lstMovieRating.add(movieRating2);
        lstMovieRating.add(movieRating3);

        return lstMovieRating;
    }
}
