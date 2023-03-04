package com.backbase.rest.moviesapi.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.annotation.Resource;

import com.backbase.rest.moviesapi.config.JacksonConfig;
import com.backbase.rest.moviesapi.domain.Movie.MovieData;
import com.backbase.rest.moviesapi.service.impl.OpenMovieDatabaseServiceImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.util.ReflectionTestUtils;
import java.net.http.HttpResponse;

@ExtendWith(MockitoExtension.class)
public class OpenMovieDatabaseServiceTest {


    @Mock(answer = Answers.RETURNS_MOCKS)
    private ObjectMapper objectMapper;

    @InjectMocks
    private OpenMovieDatabaseServiceImpl openMovieDatabaseService;

    @Test
    public void testGetMovieDetailFromOmdbApiWithTitle() throws JsonProcessingException {
        //when(objectMapper.readValue(anyString(), ArgumentMatchers.eq(MovieData.class))).thenCallRealMethod();
        ReflectionTestUtils.setField(openMovieDatabaseService, "omdbApiEndpoint", "http://www.omdbapi.com/?apikey=e5c7611");
        MovieData movieDetailFromOmdbApi = openMovieDatabaseService.getMovieDetailFromOmdbApi("TITLE", "An Education");

    }

    @Test
    public void testGetMovieDetailFromOmdbApiWithImdbId()
    {
        ReflectionTestUtils.setField(openMovieDatabaseService, "omdbApiEndpoint", "http://www.omdbapi.com/?apikey=e5c7611");
        MovieData movieDetailFromOmdbApi = openMovieDatabaseService.getMovieDetailFromOmdbApi("IMDB_ID", "tt1174732");

    }

}
