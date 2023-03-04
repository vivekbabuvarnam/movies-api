package com.backbase.rest.moviesapi.controller;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;

import com.backbase.rest.moviesapi.config.EhCacheConfigurer;
import com.backbase.rest.moviesapi.config.LoadAcademyAwardsDataFromCsvToCache;
import com.backbase.rest.moviesapi.domain.AcademyAwardsData;
import com.backbase.rest.moviesapi.exception.NotFoundException;
import com.opencsv.bean.CsvToBeanBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public class CsvFileUploadController {

    @Resource
    private LoadAcademyAwardsDataFromCsvToCache loadAcademyAwardsDataFromCsvToCache;

    @Resource
    private EhCacheConfigurer ehCacheConfigurer;

    @Operation(summary = "If any Changes in the Oscar CSV information, then you can upload the file here; Role Required: ROLE_ADMIN")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
                    @ApiResponse(responseCode = "200",description =" File Uploaded Successfully",
                                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE, schema = @Schema(implementation = File.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Exception",
                                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @PostMapping("/best-picture-oscar-csv-file")
    public String uploadCSVFile(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            log.error("Uploaded File content is empty");
            throw new NotFoundException("Not Found the file to be uploaded. Please send the file which needs to be uploaded!");
        }
        else {
            List<AcademyAwardsData> csvData;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

                csvData = new CsvToBeanBuilder(reader)
                                .withType(AcademyAwardsData.class)
                                .withIgnoreLeadingWhiteSpace(true)
                                .build()
                                .parse();

                ehCacheConfigurer.evictOscarWonBestPictureMovieCache();
                log.info("Best Picture Cache evicted");
                loadAcademyAwardsDataFromCsvToCache.processCsvDataToListOfTitleWinner(csvData);
                log.info("File data uploaded");
            }
            catch (Exception ex) {
                log.error("Exception: {} ", ex);
                throw new NotFoundException("Exception while uploading the file!");
            }
        }

        return "File Upload Successful!";
    }

}


