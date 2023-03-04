package com.backbase.rest.moviesapi.config;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@ExtendWith(MockitoExtension.class)
public class EhCacheConfigurerTest {
    @InjectMocks
    private EhCacheConfigurer ehCacheConfigurer;

    @Mock
    private LoadAcademyAwardsDataFromCsvToCache loadAcademyAwardsDataFromCsvToCache;

    @Test
    public void testGetOscarWonBestPictureMovie() {
        when(loadAcademyAwardsDataFromCsvToCache.loadBestPictureOscarWonMovies()).thenReturn(new ArrayList<>());
        assertTrue(ehCacheConfigurer.getOscarWonBestPictureMovie().isEmpty());
    }

    @Test
    public void testGetOscarWonBestPictureMovieAbstractCache() {
        when(loadAcademyAwardsDataFromCsvToCache.loadBestPictureOscarWonMovies()).thenReturn(new ArrayList<>());
        when(ehCacheConfigurer.getOscarWonBestPictureMovie()).thenReturn(new ArrayList<>());
        assertFalse(ehCacheConfigurer.getOscarWonBestPictureMovieAbstractCache("Test"));
    }


}
