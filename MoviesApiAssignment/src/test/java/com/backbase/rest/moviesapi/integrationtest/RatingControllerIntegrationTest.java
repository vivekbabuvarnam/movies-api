package com.backbase.rest.moviesapi.integrationtest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Map;

import com.backbase.rest.moviesapi.MoviesapiApplication;
import com.backbase.rest.moviesapi.form.ImdbIdRatingForm;
import com.backbase.rest.moviesapi.form.TitleRatingForm;
import com.backbase.rest.moviesapi.repository.MovieRatingRepository;
import com.backbase.rest.moviesapi.view.MovieDataView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.util.UriComponentsBuilder;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {
                "spring.profiles.active=it" }, classes = MoviesapiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("it")
public class RatingControllerIntegrationTest {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    @Autowired
    private MovieRatingRepository movieRatingRepository;

    private String token;

    @BeforeEach
    public void setup() throws JsonProcessingException {
        IntegrationTestUtility.initTable(movieRatingRepository);

        //Generate JWT Token
        HttpHeaders headers = new HttpHeaders();
        headers.set("username", "vivek");
        headers.set("password", "admin");

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(createURLWithPort("/login"),
                        headers,
                        String.class);
        assertEquals(200, responseEntity.getStatusCodeValue());
        Map map = new ObjectMapper().readValue(responseEntity.getBody(), Map.class);
        token = map.get("access_token").toString();


    }

    @Test
    void postRatingWithTitle() {
        TitleRatingForm titleRatingForm = new TitleRatingForm();
        titleRatingForm.setRating(8.0);
        titleRatingForm.setTitle("Black Swan");
        HttpHeaders headerToEndpoint = new HttpHeaders();
        headerToEndpoint.set("Authorization", "Bearer "+token);
        HttpEntity request = new HttpEntity(titleRatingForm, headerToEndpoint);
        ResponseEntity<MovieDataView> responseEntity = restTemplate.exchange(createURLWithPort("/rating/title"),
                        HttpMethod.POST,
                        request,
                        MovieDataView.class);
        assertAll("Validating response",
                        () -> assertEquals(201, responseEntity.getStatusCodeValue()),
                        () -> assertNull(responseEntity.getBody().getOscar()),
                        () -> assertEquals("Black Swan", responseEntity.getBody().getTitle()),
                        () -> assertEquals("8.0", responseEntity.getBody().getImdbRating())
        );
    }

    @Test
    void postRatingWithImdbId() throws Exception {
        ImdbIdRatingForm imdbIdRatingForm = new ImdbIdRatingForm();
        imdbIdRatingForm.setRating(7.0);
        imdbIdRatingForm.setImdbId("tt0379725");
        HttpHeaders headerToEndpoint = new HttpHeaders();
        headerToEndpoint.set("Authorization", "Bearer "+token);
        HttpEntity request = new HttpEntity(imdbIdRatingForm, headerToEndpoint);
        ResponseEntity<MovieDataView> responseEntity = restTemplate.exchange(createURLWithPort("/rating/imdb"),
                        HttpMethod.POST,
                        request,
                        MovieDataView.class);
        assertAll("Validating response",
                        () -> assertEquals(201, responseEntity.getStatusCodeValue()),
                        () -> assertNull(responseEntity.getBody().getOscar()),
                        () -> assertEquals("tt0379725", responseEntity.getBody().getImdbId()),
                        () -> assertEquals("7.0", responseEntity.getBody().getImdbRating())
        );
    }

    @Test
    void deleteRatingBasedOnIdNotAvailable() throws Exception {
        ImdbIdRatingForm imdbIdRatingForm = new ImdbIdRatingForm();
        imdbIdRatingForm.setRating(7.0);
        imdbIdRatingForm.setImdbId("tt0379725");
        HttpHeaders headerToEndpoint = new HttpHeaders();
        headerToEndpoint.set("Authorization", "Bearer "+token);
        HttpEntity request = new HttpEntity(imdbIdRatingForm, headerToEndpoint);
        ResponseEntity<Void> responseEntity = restTemplate.exchange(createURLWithPort("/rating/imdb/100"), HttpMethod.DELETE, request, Void.class);
        assertAll("Validating response",
                        () -> assertEquals(404, responseEntity.getStatusCodeValue())
        );
    }

    @Test
    void deleteRatingBasedOnIdUserNothMatchingWithResource() throws Exception {
        ImdbIdRatingForm imdbIdRatingForm = new ImdbIdRatingForm();
        imdbIdRatingForm.setRating(7.0);
        imdbIdRatingForm.setImdbId("tt0379725");
        HttpHeaders headerToEndpoint = new HttpHeaders();
        headerToEndpoint.set("Authorization", "Bearer "+token);
        HttpEntity request = new HttpEntity(imdbIdRatingForm, headerToEndpoint);
        ResponseEntity<Void> responseEntity = restTemplate.exchange(createURLWithPort("/rating/imdb/2"), HttpMethod.DELETE, request, Void.class);
        assertAll("Validating response",
                        () -> assertEquals(404, responseEntity.getStatusCodeValue())
        );
    }

    @Test
    void deleteRatingBasedOnIdSuccess() throws Exception {
        ImdbIdRatingForm imdbIdRatingForm = new ImdbIdRatingForm();
        imdbIdRatingForm.setRating(7.0);
        imdbIdRatingForm.setImdbId("tt0379725");
        HttpHeaders headerToEndpoint = new HttpHeaders();
        headerToEndpoint.set("Authorization", "Bearer "+token);
        HttpEntity request = new HttpEntity(imdbIdRatingForm, headerToEndpoint);
        ResponseEntity<Void> responseEntity = restTemplate.exchange(createURLWithPort("/rating/imdb/1"), HttpMethod.DELETE, request, Void.class);
        assertAll("Validating response",
                        () -> assertEquals(200, responseEntity.getStatusCodeValue())
        );
    }

    private String createURLWithPort(String uri) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + uri);
        return builder.build().toUriString();
    }
}
