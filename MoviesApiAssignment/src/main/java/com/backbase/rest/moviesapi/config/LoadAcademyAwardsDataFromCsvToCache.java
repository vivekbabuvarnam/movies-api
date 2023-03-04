package com.backbase.rest.moviesapi.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.backbase.rest.moviesapi.domain.AcademyAwardsData;
import com.backbase.rest.moviesapi.exception.NotFoundException;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LoadAcademyAwardsDataFromCsvToCache {

    @Value("${dynamicinput.csv.file.location}")
    private String fileName;

    @CachePut(value = "oscarWonBestPictureMovie")
    public List<String> loadBestPictureOscarWonMovies()  {

        log.info("oscarWonBestPictureMovie Cache is refreshing");

        List<AcademyAwardsData> csvData;
        List<String> oscarWonBestPictureMovieNominee = new ArrayList<>();

        {
            try {
                csvData = new CsvToBeanBuilder(new FileReader(fileName))
                                .withType(AcademyAwardsData.class)
                                .build()
                                .parse();

                oscarWonBestPictureMovieNominee = processCsvDataToListOfTitleWinner(csvData);

            }
            catch (FileNotFoundException e) {
                throw new NotFoundException("The File was not Found");
            }
            catch (Exception exception) {
                throw new NotFoundException("Error processing the File!!");
            }
        }
        return oscarWonBestPictureMovieNominee;
    }

    public List<String> processCsvDataToListOfTitleWinner(List<AcademyAwardsData> csvData) throws Exception{
        return csvData.stream().filter(movie -> null != movie.getWon() && movie.getWon().equalsIgnoreCase("YES"))
                        .filter(oscarWonMovie -> null != oscarWonMovie.getCategory() && oscarWonMovie.getCategory().equalsIgnoreCase("Best Picture"))
                        .map(oscarWonBestPictureMovie -> oscarWonBestPictureMovie.getNominee())
                        .collect(Collectors.toList());
    }

}
