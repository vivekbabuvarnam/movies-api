package com.backbase.rest.moviesapi.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.backbase.rest.moviesapi.domain.AcademyAwardsData;
import com.backbase.rest.moviesapi.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.util.NestedServletException;

@ExtendWith(MockitoExtension.class)
public class LoadAcademyAwardsDataFromCsvToCacheTest {

    @InjectMocks
    private LoadAcademyAwardsDataFromCsvToCache loadAcademyAwardsDataFromCsvToCache;

    @Test
    public void testProcessCsvDataToListOfTitleWinner() throws Exception {
        List<AcademyAwardsData> csvData = testDataPrepare();
        List<String> strings = loadAcademyAwardsDataFromCsvToCache.processCsvDataToListOfTitleWinner(csvData);
        assertEquals(1,strings.size());
    }

    @Test
    public void testLoadBestPictureOscarWonMoviesFileFound() throws Exception {
        ReflectionTestUtils.setField(loadAcademyAwardsDataFromCsvToCache, "fileName", "src/main/resources/academy_awards.csv");

        List<String> strings = loadAcademyAwardsDataFromCsvToCache.loadBestPictureOscarWonMovies();

        assertFalse(strings.isEmpty());
    }

    @Test
    public void testLoadBestPictureOscarWonMoviesFileNotFound() throws Exception {
        ReflectionTestUtils.setField(loadAcademyAwardsDataFromCsvToCache, "fileName", "file.csv");

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            loadAcademyAwardsDataFromCsvToCache.loadBestPictureOscarWonMovies();
        });

        assertEquals("The File was not Found", exception.getMessage());
    }

    private List<AcademyAwardsData> testDataPrepare() {
        AcademyAwardsData academyAwardsDataCorrect=new AcademyAwardsData();
        academyAwardsDataCorrect.setWon("YES");
        academyAwardsDataCorrect.setCategory("Best Picture");
        AcademyAwardsData academyAwardsDataWrong=new AcademyAwardsData();
        academyAwardsDataWrong.setWon("NO");
        academyAwardsDataWrong.setCategory("Best Picture");
        AcademyAwardsData academyAwardsDataWrong1=new AcademyAwardsData();
        academyAwardsDataWrong1.setWon("YES");
        academyAwardsDataWrong1.setCategory("others");
        AcademyAwardsData academyAwardsDataWrongNullWon=new AcademyAwardsData();
        academyAwardsDataWrongNullWon.setWon(null);
        academyAwardsDataWrongNullWon.setCategory("others");
        AcademyAwardsData academyAwardsDataWrongNullCategory=new AcademyAwardsData();
        academyAwardsDataWrongNullCategory.setWon("YES");
        academyAwardsDataWrongNullCategory.setCategory(null);

        List<AcademyAwardsData> academyAwardsDataList = new ArrayList<>();
        academyAwardsDataList.add(academyAwardsDataCorrect);
        academyAwardsDataList.add(academyAwardsDataWrong);
        academyAwardsDataList.add(academyAwardsDataWrong1);
        academyAwardsDataList.add(academyAwardsDataWrongNullWon);
        academyAwardsDataList.add(academyAwardsDataWrongNullCategory);

        return academyAwardsDataList;

    }
}
