package com.backbase.rest.moviesapi.config;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class EhCacheConfigurer {

    @Resource
    private LoadAcademyAwardsDataFromCsvToCache loadAcademyAwardsDataFromCsvToCache;

    @Cacheable(value = "oscarWonBestPictureMovie")
    public List<String> getOscarWonBestPictureMovie()  {
        return loadAcademyAwardsDataFromCsvToCache.loadBestPictureOscarWonMovies();
    }

    @Cacheable(value = "oscarWonBestPictureMovieExtract", key = "{#title}")
    public Boolean getOscarWonBestPictureMovieAbstractCache(String title) {
        return this.getOscarWonBestPictureMovie().stream().anyMatch(name -> title.equals(name));
    }

    @CacheEvict(value = "topTenImdbMovies", allEntries = true)
    public void evictTopTenImdbMoviesCacheValues() {
    }

    @CacheEvict(value = "oscarWonBestPictureMovie", allEntries = true)
    public void evictOscarWonBestPictureMovieCache() {
    }
}
