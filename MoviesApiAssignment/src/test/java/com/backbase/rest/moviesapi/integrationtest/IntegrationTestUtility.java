package com.backbase.rest.moviesapi.integrationtest;

import com.backbase.rest.moviesapi.entities.MovieRating;
import com.backbase.rest.moviesapi.repository.MovieRatingRepository;

public class IntegrationTestUtility {
    public static void initTable(MovieRatingRepository movieRatingRepository) {

        MovieRating movieRating1 = new MovieRating();
        movieRating1.setId(1);
        movieRating1.setUniqueValue("tt0947798abc");
        movieRating1.setUserKey("vivek");
        movieRating1.setTitle("Black Swan");
        movieRating1.setImdbId("tt0947798");
        movieRating1.setImdbRating(7.5);
        movieRating1.setBoxOffice("$106,954,678");
        movieRatingRepository.save(movieRating1);

        MovieRating movieRating2 = new MovieRating();
        movieRating2.setId(2);
        movieRating2.setUniqueValue("tt0964517xyz");
        movieRating2.setUserKey("xys");
        movieRating2.setTitle("The Fighter");
        movieRating2.setImdbId("tt0964517");
        movieRating2.setImdbRating(6.5);
        movieRating2.setBoxOffice("$93,617,009");
        movieRatingRepository.save(movieRating2);

        MovieRating movieRating3 = new MovieRating();
        movieRating3.setId(3);
        movieRating3.setUniqueValue("tt0947798def");
        movieRating3.setUserKey("def");
        movieRating3.setTitle("Black Swan");
        movieRating3.setImdbId("tt0947798");
        movieRating3.setImdbRating(8.5);
        movieRating3.setBoxOffice("$106,954,678");
        movieRatingRepository.save(movieRating3);

        MovieRating movieRating4 = new MovieRating();
        movieRating4.setId(4);
        movieRating4.setUniqueValue("tt0964517def");
        movieRating4.setUserKey("def");
        movieRating4.setTitle("The Fighter");
        movieRating4.setImdbId("tt0964517");
        movieRating4.setImdbRating(8.5);
        movieRating4.setBoxOffice("$93,617,009");
        movieRatingRepository.save(movieRating4);

        MovieRating movieRating5 = new MovieRating();
        movieRating5.setId(5);
        movieRating5.setUniqueValue("tt0379725ghi");
        movieRating5.setUserKey("ghi");
        movieRating5.setTitle("Capote");
        movieRating5.setImdbId("tt0379725");
        movieRating5.setImdbRating(6.0);
        movieRating5.setBoxOffice("$28,750,530");
        movieRatingRepository.save(movieRating5);

        MovieRating movieRating6 = new MovieRating();
        movieRating6.setId(6);
        movieRating6.setUniqueValue("tt0379725abc");
        movieRating6.setUserKey("abc");
        movieRating6.setTitle("Capote");
        movieRating6.setImdbId("tt0379725");
        movieRating6.setImdbRating(7.0);
        movieRating6.setBoxOffice("$28,750,530");
        movieRatingRepository.save(movieRating6);

        MovieRating movieRating7 = new MovieRating();
        movieRating7.setId(7);
        movieRating7.setUniqueValue("tt0947798a");
        movieRating7.setUserKey("a");
        movieRating7.setTitle("Black Swan");
        movieRating7.setImdbId("tt0947798");
        movieRating7.setImdbRating(4.5);
        movieRating7.setBoxOffice("$106,954,678");
        movieRatingRepository.save(movieRating7);

    }
}

