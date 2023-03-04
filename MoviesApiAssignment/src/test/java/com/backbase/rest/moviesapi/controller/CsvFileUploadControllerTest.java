package com.backbase.rest.moviesapi.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.annotation.Resource;

import java.io.File;

import com.backbase.rest.moviesapi.config.EhCacheConfigurer;
import com.backbase.rest.moviesapi.config.LoadAcademyAwardsDataFromCsvToCache;
import com.backbase.rest.moviesapi.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.util.NestedServletException;

@ExtendWith(MockitoExtension.class)
public class CsvFileUploadControllerTest {

    @Mock
    private LoadAcademyAwardsDataFromCsvToCache loadAcademyAwardsDataFromCsvToCache;

    @Mock
    private EhCacheConfigurer ehCacheConfigurer;

    @InjectMocks
    private CsvFileUploadController csvFileUploadController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(csvFileUploadController)
                        //.setMessageConverters(simpleFilterProvider())
                        .build();
    }

    @Test
    public void testUploadCSVFile() throws Exception {

        MockMultipartFile file
                        = new MockMultipartFile(
                        "file",
                        "hello.txt",
                        MediaType.TEXT_PLAIN_VALUE,
                        "Hello, World!".getBytes()
        );
        doNothing().when(ehCacheConfigurer).evictOscarWonBestPictureMovieCache();
        mockMvc.perform(multipart("/upload/best-picture-oscar-csv-file").file(file)).andExpect(status().isOk());
    }

    @Test
    public void testUploadEmptyCSVFile() throws Exception {

        MockMultipartFile file
                        = new MockMultipartFile(
                        "file",
                        "hello.txt",
                        MediaType.TEXT_PLAIN_VALUE,
                        "".getBytes()
        );

        NestedServletException exception = assertThrows(NestedServletException.class, () -> {
            mockMvc.perform(multipart("/upload/best-picture-oscar-csv-file").file(file));
        });
        assertTrue(exception.getMessage().contains("Not Found the file to be uploaded. Please send the file which needs to be uploaded!"));
    }

    @Test
    public void testCSVFileReadError() throws Exception {

        MockMultipartFile file
                        = new MockMultipartFile(
                        "file",
                        "hello.txt",
                        MediaType.TEXT_PLAIN_VALUE,
                        "Hello, World!".getBytes()
        );
        when(loadAcademyAwardsDataFromCsvToCache.processCsvDataToListOfTitleWinner(anyList())).thenThrow(Exception.class);

        NestedServletException exception = assertThrows(NestedServletException.class, () -> {
            mockMvc.perform(multipart("/upload/best-picture-oscar-csv-file").file(file));
        });
        assertTrue(exception.getMessage().contains("Request processing failed; nested exception is com.backbase.rest.moviesapi.exception.NotFoundException: Exception while uploading the file!"));
    }

}
