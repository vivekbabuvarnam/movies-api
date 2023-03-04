package com.backbase.rest.moviesapi.repository;

import com.backbase.rest.moviesapi.entities.MovieRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRatingRepository extends JpaRepository<MovieRating, Long> {

    @Query(value = "from MovieRating mr where mr.uniqueValue = :uniqueValue")
    MovieRating findUniqueValue(String uniqueValue);

    @Query(value = "from MovieRating mr where mr.id = :id AND mr.userKey= :userKey")
    MovieRating findResourceWithIdAndUser(String userKey, Long id);
}
