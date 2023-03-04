package com.backbase.rest.moviesapi.orchestrator;

import static java.util.Comparator.reverseOrder;
import static java.util.Map.Entry;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.backbase.rest.moviesapi.config.EhCacheConfigurer;
import com.backbase.rest.moviesapi.director.LinkDirector;
import com.backbase.rest.moviesapi.domain.Movie.MovieData;
import com.backbase.rest.moviesapi.domain.Movie.OscarData;
import com.backbase.rest.moviesapi.entities.MovieRating;
import com.backbase.rest.moviesapi.exception.NotFoundException;
import com.backbase.rest.moviesapi.mapper.MovieDataMapper;
import com.backbase.rest.moviesapi.mapper.MovieRatingMapper;
import com.backbase.rest.moviesapi.processor.MovieInfoProcessor;
import com.backbase.rest.moviesapi.service.OpenMovieDatabaseService;
import com.backbase.rest.moviesapi.view.Link;
import com.backbase.rest.moviesapi.view.MovieDataView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MovieInfoOrchestrator {

    @Resource
    private OpenMovieDatabaseService openMovieDatabaseService;

    @Resource
    private MovieInfoProcessor movieInfoProcessor;

    @Resource
    private EhCacheConfigurer ehCacheConfigurer;

    @Resource
    private LinkDirector linkDirector;

    public MovieDataView getOscarInformation(String user, String title) throws NotFoundException {

        MovieData movieDetailBasedOnTitle = openMovieDatabaseService.getMovieDetailFromOmdbApi("TITLE", title);

        if (movieDetailBasedOnTitle != null) {
            MovieDataView movieDataView = MovieDataMapper.MOVIE_DATA_MAPPER_INSTANCE.toView(movieDetailBasedOnTitle);
            Boolean bestPictureOscarWon = ehCacheConfigurer.getOscarWonBestPictureMovieAbstractCache(title);

            if (bestPictureOscarWon) {
                OscarData oscarData = new OscarData();
                oscarData.setOscarName("BestPicture");
                oscarData.setWon(true);
                movieDataView.setOscar(Arrays.asList(oscarData));
            }
            movieInfoProcessor.addHaeteousLinks(user, movieDataView);
            return movieDataView;
        }
        else {
            log.error("Title passed is not present!");
            throw new NotFoundException("The Title You sent it not available. Please try with different Name");
        }

    }

    @Cacheable(value = "topTenImdbMovies")
    public List<MovieDataView> getTopTenMovies(String user) {
        List<MovieRating> listOfAllRatedMovies = movieInfoProcessor.getListOfAllRatedMovies();

        Map<String, Optional<MovieRating>> imdbIdWithObject = listOfAllRatedMovies.stream().collect(Collectors.groupingBy(MovieRating::getImdbId, Collectors.reducing((id, id1) -> id1)));

        Map<String, Double> imdbWithAverage = listOfAllRatedMovies.stream()
                        .collect(Collectors.groupingBy(MovieRating::getImdbId, Collectors.averagingDouble(MovieRating::getImdbRating)));

        LinkedHashMap<String, Double> imdbWithAverageSortedDesc = imdbWithAverage.entrySet().stream().sorted(Entry.comparingByValue(reverseOrder())).collect(Collectors.toMap(Entry::getKey, Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        List<MovieRating> sortedValue = imdbWithAverageSortedDesc.keySet().stream().limit(10).map(key -> imdbIdWithObject.get(key).get()).sorted().collect(Collectors.toList());

        List<MovieDataView> lstMovieDataView = new ArrayList<>();
        sortedValue.forEach(obj -> {
            MovieDataView movieDataView = MovieRatingMapper.MOVIE_RATING_MAPPER_INSTANCE.toView(obj);
            movieDataView.setAddRatingLink(new Link(linkDirector.addRatingLink(user)));
            lstMovieDataView.add(movieDataView);
        });

        return lstMovieDataView;
    }
}
