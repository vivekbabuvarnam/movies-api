package com.backbase.rest.moviesapi.director;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import javax.annotation.Resource;

import com.backbase.rest.moviesapi.context.ApiUriContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.util.UriComponentsBuilder;

@ExtendWith(MockitoExtension.class)
public class LinkDirectorTest {
    @InjectMocks
    private LinkDirector linkDirector;

    @Mock
    private ApiUriContext uriContext;

    @Test
    public void testAddTopTenMovieLink() {
        UriComponentsBuilder path = UriComponentsBuilder.newInstance().host("http").port(8080).path("locahost");
        when(uriContext.getUriComponentsBuilder()).thenReturn(path);
        assertEquals("//http:8080/locahost/movie/top10movies",linkDirector.addTopTenMovieLink("123"));
    }

    @Test
    public void testAddRatingLink() {
        UriComponentsBuilder path = UriComponentsBuilder.newInstance().host("http").port(8080).path("locahost");
        when(uriContext.getUriComponentsBuilder()).thenReturn(path);
        assertEquals("//http:8080/locahost/rating/imdb",linkDirector.addRatingLink("123"));
    }

    @Test
    public void testDeleteRatingLink() {
        UriComponentsBuilder path = UriComponentsBuilder.newInstance().host("http").port(8080).path("locahost");
        when(uriContext.getUriComponentsBuilder()).thenReturn(path);
        assertEquals("//http:8080/locahost/rating/imdb/1",linkDirector.deleteRatingLink("123",1));
    }

    @Test
    public void testAddCsvFileUploadLink() {
        assertEquals("http://localhost:8080/upload/best-picture-oscar-csv-file/",linkDirector.addCsvFileUploadLink());
    }
}
