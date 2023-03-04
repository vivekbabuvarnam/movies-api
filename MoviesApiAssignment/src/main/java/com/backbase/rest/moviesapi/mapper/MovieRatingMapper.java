package com.backbase.rest.moviesapi.mapper;

import com.backbase.rest.moviesapi.domain.Movie.MovieData;
import com.backbase.rest.moviesapi.entities.MovieRating;
import com.backbase.rest.moviesapi.view.MovieDataView;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MovieRatingMapper {

    MovieRatingMapper MOVIE_RATING_MAPPER_INSTANCE = Mappers.getMapper(MovieRatingMapper.class);

    MovieRating toEntity(MovieData movieData);

    MovieData toDomain(MovieRating movieRating);

    MovieDataView toView(MovieRating movieRating);
}
