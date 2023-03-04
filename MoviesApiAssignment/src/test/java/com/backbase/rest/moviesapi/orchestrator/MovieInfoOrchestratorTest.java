package com.backbase.rest.moviesapi.orchestrator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import com.backbase.rest.moviesapi.config.EhCacheConfigurer;
import com.backbase.rest.moviesapi.director.LinkDirector;
import com.backbase.rest.moviesapi.domain.Movie.MovieData;
import com.backbase.rest.moviesapi.entities.MovieRating;
import com.backbase.rest.moviesapi.exception.NotFoundException;
import com.backbase.rest.moviesapi.processor.MovieInfoProcessor;
import com.backbase.rest.moviesapi.service.OpenMovieDatabaseService;
import com.backbase.rest.moviesapi.view.MovieDataView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.util.NestedServletException;

@ExtendWith(MockitoExtension.class)
public class MovieInfoOrchestratorTest {

    @Mock
    private OpenMovieDatabaseService openMovieDatabaseService;

    @Mock
    private MovieInfoProcessor movieInfoProcessor;

    @Mock
    private EhCacheConfigurer ehCacheConfigurer;

    @Mock
    private LinkDirector linkDirector;

    @InjectMocks
    private MovieInfoOrchestrator movieInfoOrchestrator;

    @Test
    public void testGetOscarInformationPresent() throws Exception {
        MovieData movieData = createMovieData();
        when(openMovieDatabaseService.getMovieDetailFromOmdbApi("TITLE","title")).thenReturn(movieData);
        when(ehCacheConfigurer.getOscarWonBestPictureMovieAbstractCache("title")).thenReturn(true);
        MovieDataView oscarInformation = movieInfoOrchestrator.getOscarInformation("vivek", "title");
        assertEquals("award", oscarInformation.getAwardName());
        assertEquals("$123,456", oscarInformation.getBoxOffice());
        assertEquals("1234", oscarInformation.getImdbId());
        assertEquals("7.0", oscarInformation.getImdbRating());
        assertEquals("title", oscarInformation.getTitle());
        assertFalse(oscarInformation.getOscar().isEmpty());
    }
    @Test
    public void testGetOscarInformationAbsent() throws Exception {
        MovieData movieData = createMovieData();
        when(openMovieDatabaseService.getMovieDetailFromOmdbApi("TITLE","title")).thenReturn(movieData);
        when(ehCacheConfigurer.getOscarWonBestPictureMovieAbstractCache("title")).thenReturn(false);
        MovieDataView oscarInformation = movieInfoOrchestrator.getOscarInformation("vivek", "title");
        assertEquals("award", oscarInformation.getAwardName());
        assertEquals("$123,456", oscarInformation.getBoxOffice());
        assertEquals("1234", oscarInformation.getImdbId());
        assertEquals("7.0", oscarInformation.getImdbRating());
        assertEquals("title", oscarInformation.getTitle());
        assertNull(oscarInformation.getOscar());
    }

    @Test
    public void testGetOscarInformationMovieNotPresentInOmdb() throws Exception {
        when(openMovieDatabaseService.getMovieDetailFromOmdbApi("TITLE","title")).thenReturn(null);
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            movieInfoOrchestrator.getOscarInformation("vivek", "title");
        });
        assertEquals("The Title You sent it not available. Please try with different Name", exception.getMessage());
    }

    @Test
    public void testGetTopTenMovies() throws Exception {
        List<MovieRating> lstMovieRating = createMovieRatingData();
        when(movieInfoProcessor.getListOfAllRatedMovies()).thenReturn(lstMovieRating);
        List<MovieDataView> topTenMovies = movieInfoOrchestrator.getTopTenMovies("1223");
        assertNotNull(topTenMovies);
        assertEquals("4321", topTenMovies.get(0).getImdbId());
        assertEquals("1234", topTenMovies.get(1).getImdbId());
        assertEquals("8", topTenMovies.get(2).getImdbId());
        assertEquals("N/A", topTenMovies.get(2).getBoxOffice());
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

    private MovieData createMovieData() {
        MovieData movieData = new MovieData();
        movieData.setAwardName("award");
        movieData.setBoxOffice("$123,456");
        movieData.setImdbId("1234");
        movieData.setImdbRating("7.0");
        movieData.setTitle("title");

        return movieData;

    }

}
