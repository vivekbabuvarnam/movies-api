package com.backbase.rest.moviesapi.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.annotation.Resource;

import com.backbase.rest.moviesapi.context.UserContext;
import com.backbase.rest.moviesapi.form.ImdbIdRatingForm;
import com.backbase.rest.moviesapi.form.TitleRatingForm;
import com.backbase.rest.moviesapi.orchestrator.MoviesRatingOrchestrator;
import com.backbase.rest.moviesapi.view.MovieDataView;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.pool.TypePool.Empty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class RatingControllerTest {

    @Mock
    private MoviesRatingOrchestrator moviesRatingOrchestrator;

    @InjectMocks
    private RatingController ratingController;

    @Mock
    private UserContext userContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ratingController)
                        //.setMessageConverters(simpleFilterProvider())
                        .build();
    }

    @Test
    public void testPostRatingWithTitle() throws Exception {
        MovieDataView movieDataView = new MovieDataView();
        when(userContext.getUserName()).thenReturn("test");
        when(moviesRatingOrchestrator.giveRatingBasedOnTitle(anyString(), any(TitleRatingForm.class))).thenReturn(movieDataView);
        mockMvc.perform(post("/rating/title").contentType(MediaType.APPLICATION_JSON).content("{\n" +
                        "    \"title\" : \"The Love Parade\",\n" +
                        "    \"rating\" : 8.0\n" +
                        "}")).andExpect(status().isCreated());
    }

    @Test
    public void testPostRatingWithImdbId() throws Exception {
        MovieDataView movieDataView = new MovieDataView();
        when(userContext.getUserName()).thenReturn("test");
        when(moviesRatingOrchestrator.giveRatingBasedOnImdb(anyString(), any(ImdbIdRatingForm.class))).thenReturn(movieDataView);
        mockMvc.perform(post("/rating/imdb").contentType(MediaType.APPLICATION_JSON).content("{\n" +
                        "    \"imdbId\": \"tt1174732\",\n" +
                        "    \"rating\": 7.5\n" +
                        "}")).andExpect(status().isCreated());
    }

    @Test
    public void testDeleteRatingBasedOnId() throws Exception {
        when(userContext.getUserName()).thenReturn("test");
        doNothing().when(moviesRatingOrchestrator).deleteRatingOnId("test", 1l);

        mockMvc.perform(delete("/rating/imdb/1")).andExpect(status().isOk());
    }


}
