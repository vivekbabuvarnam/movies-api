package com.backbase.rest.moviesapi.integrationtest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.backbase.rest.moviesapi.MoviesapiApplication;
import com.backbase.rest.moviesapi.repository.MovieRatingRepository;
import com.backbase.rest.moviesapi.view.MovieDataView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
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
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.util.UriComponentsBuilder;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {
                "spring.profiles.active=it" }, classes = MoviesapiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("it")
public class MovieInfoControllerIntegrationTest {

    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();
    @Autowired
    private MovieRatingRepository movieRatingRepository;

    private HttpEntity request;

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
        String token = map.get("access_token").toString();

        HttpHeaders headerToEndpoint = new HttpHeaders();
        headerToEndpoint.set("Authorization", "Bearer "+token);
        request = new HttpEntity(headerToEndpoint);
    }

    @Test
    void getBestPictureOscarDetails() throws Exception {

        ResponseEntity<MovieDataView> responseEntity = restTemplate.exchange(createURLWithPort("/movie/bestpictureoscar/Test Title"),
                        HttpMethod.GET,
                        request,
                        MovieDataView.class);
        assertAll("Validating response",
                        () -> assertEquals(200, responseEntity.getStatusCodeValue()),
                        () -> assertNull(responseEntity.getBody().getOscar()),
                        () -> assertEquals("Test Title", responseEntity.getBody().getTitle()),
                        () -> assertEquals("tt13010072", responseEntity.getBody().getImdbId())
        );
    }

    @Test
    void getTopTenMovies() throws Exception {
        ResponseEntity<List> responseEntity = restTemplate.exchange(createURLWithPort("/movie/top10movies"),
                        HttpMethod.GET,
                        request,
                        List.class);
        assertAll("Validating response",
                        () -> assertEquals(200, responseEntity.getStatusCodeValue()),
                        () -> assertNotNull(responseEntity.getBody()),
                        () -> assertEquals("Black Swan",((LinkedHashMap)responseEntity.getBody().get(0)).get("title")),
                        () -> assertEquals("The Fighter",((LinkedHashMap)responseEntity.getBody().get(1)).get("title")),
                        () -> assertEquals("Capote",((LinkedHashMap)responseEntity.getBody().get(2)).get("title"))
        );
    }

    private String createURLWithPort(String uri) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + uri);
        return builder.build().toUriString();
    }

}
