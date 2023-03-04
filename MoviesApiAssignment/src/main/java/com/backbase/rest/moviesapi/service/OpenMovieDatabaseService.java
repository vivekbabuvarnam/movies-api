package com.backbase.rest.moviesapi.service;

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

import com.backbase.rest.moviesapi.config.JacksonConfig;
import com.backbase.rest.moviesapi.domain.Movie.MovieData;
import com.backbase.rest.moviesapi.exception.NotFoundException;
import com.backbase.rest.moviesapi.exception.ServiceConsumptionException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;



public interface OpenMovieDatabaseService {
    MovieData getMovieDetailFromOmdbApi(String from, String dynamicVariable);
}
