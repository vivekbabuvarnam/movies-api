package com.backbase.rest.moviesapi.integrationtest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import com.backbase.rest.moviesapi.MoviesapiApplication;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponentsBuilder;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {
                "spring.profiles.active=it" }, classes = MoviesapiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("it")
@AutoConfigureMockMvc
public class CsvFileUploadControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

    private String bearer;

    TestRestTemplate restTemplate = new TestRestTemplate();

    @BeforeEach
    public void setup() throws JsonProcessingException {

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
        bearer ="Bearer "+token;
    }

    @Test
    void uploadCSVFile() throws Exception {

        MockMultipartFile file
                        = new MockMultipartFile(
                        "file",
                        "hello.txt",
                        MediaType.TEXT_PLAIN_VALUE,
                        ("1, 'tt0947798abc', 'abc', 'Black Swan', 'tt0947798', '7.5', '$106,954,678'").getBytes());
        mockMvc.perform(multipart("/upload/best-picture-oscar-csv-file").file(file).header("Authorization",bearer)).andExpect(status().isOk());
    }

    private String createURLWithPort(String uri) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + uri);
        return builder.build().toUriString();
    }
}
