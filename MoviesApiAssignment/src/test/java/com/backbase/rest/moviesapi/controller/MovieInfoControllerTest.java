package com.backbase.rest.moviesapi.controller;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import com.backbase.rest.moviesapi.context.UserContext;
import com.backbase.rest.moviesapi.orchestrator.MovieInfoOrchestrator;
import com.backbase.rest.moviesapi.view.MovieDataView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.apache.commons.collections4.Get;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class MovieInfoControllerTest {

    @Mock
    private MovieInfoOrchestrator movieInfoOrchestrator;

    @InjectMocks
    private MovieInfoController movieInfoController;

    @Mock
    private UserContext userContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(movieInfoController)
                        .setMessageConverters(simpleFilterProvider())
                        .build();
    }

    @Test
    public void testGetBestPictureOscarDetails() throws Exception {
        when(userContext.getUserName()).thenReturn("test");
        String userKey = "test";
        String movieTitle ="Test Title";
        MovieDataView movieDataView = new MovieDataView();
        when(movieInfoOrchestrator.getOscarInformation(userKey, movieTitle)).thenReturn(movieDataView);
        mockMvc.perform(get("/movie/bestpictureoscar/Test Title")).andExpect(status().isOk());
    }

    @Test
    public void testGetTopTenMovies() throws Exception {
        when(userContext.getUserName()).thenReturn("test");
        String userKey = "test";
        MovieDataView movieDataView = new MovieDataView();
        List<MovieDataView> movieDataViewList= new ArrayList<>();
        movieDataViewList.add(movieDataView);
        when(movieInfoOrchestrator.getTopTenMovies(userKey)).thenReturn(movieDataViewList);
        mockMvc.perform(get("/movie/top10movies")).andExpect(status().isOk());
    }

    private MappingJackson2HttpMessageConverter simpleFilterProvider() {
        ObjectMapper objectMapper = new ObjectMapper();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);
        return converter;
    }

}
