package com.backbase.rest.moviesapi.mapper;

import com.backbase.rest.moviesapi.domain.Movie.MovieData;
import com.backbase.rest.moviesapi.view.MovieDataView;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MovieDataMapper {

    MovieDataMapper MOVIE_DATA_MAPPER_INSTANCE = Mappers.getMapper(MovieDataMapper.class);

    MovieDataView toView(MovieData movieData);

    MovieData toDomain(MovieDataView movieDataView);
}
