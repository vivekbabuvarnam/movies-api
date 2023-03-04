package com.backbase.rest.moviesapi.service.impl;

import static java.time.temporal.ChronoUnit.SECONDS;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;

import com.backbase.rest.moviesapi.domain.Movie.MovieData;
import com.backbase.rest.moviesapi.exception.NotFoundException;
import com.backbase.rest.moviesapi.exception.ServiceConsumptionException;
import com.backbase.rest.moviesapi.service.OpenMovieDatabaseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OpenMovieDatabaseServiceImpl implements OpenMovieDatabaseService {

    public static final String TITLE = "TITLE";

    @Resource
    private ObjectMapper objectMapper;

    @Value("${dynamicinput.omdbapi.endpoint}")
    private String omdbApiEndpoint;

    public MovieData getMovieDetailFromOmdbApi(String from, String dynamicVariable) {

        //call other api with title and get output
        try {
            HttpGet httpGet = new HttpGet(omdbApiEndpoint);
            URIBuilder uriBuilder = new URIBuilder(httpGet.getURI())
                            .addParameter("r", "json")
                            .addParameter("plot", "full");

            if (TITLE.equalsIgnoreCase(from)) {
                uriBuilder.addParameter("t", dynamicVariable);
            }
            else {
                uriBuilder.addParameter("i", dynamicVariable);
            }

            final URI uri = uriBuilder.build();

            HttpRequest request = HttpRequest.newBuilder()
                            .uri(uri)
                            .timeout(Duration.of(10, SECONDS))
                            .GET()
                            .build();
            HttpResponse<String> responseString = HttpClient.newBuilder().build().send(request, BodyHandlers.ofString());
            if (responseString.body().equalsIgnoreCase("{\"Response\":\"False\",\"Error\":\"Incorrect IMDb ID.\"}") || responseString.body().equalsIgnoreCase(" {\"Response\":\"False\",\"Error\":\"Movie not found!\"}")
                            || responseString.body().equalsIgnoreCase("{\"Response\":\"False\",\"Error\":\"Movie not found!\"}")) {
                log.info("OMDBAPI Not having search Data");
                throw new NotFoundException("The Title You sent it not available. Please try with different Name");
            }
            return objectMapper.readValue(responseString.body(), MovieData.class);
        }
        catch (InterruptedException e) {
            log.error("Exception: {} ", e);
            throw new ServiceConsumptionException("Issue in calling OMDBAPI");
        }
        catch (IOException e) {
            log.error("Exception: {} ", e);
            throw new ServiceConsumptionException("Issue in calling OMDBAPI");
        }
        catch (URISyntaxException e) {
            log.error("Exception: {} ", e);
            throw new ServiceConsumptionException("Issue in calling OMDBAPI");
        }

    }

}
