package com.backbase.rest.moviesapi.controller;

import javax.annotation.Resource;
import java.util.List;

import com.backbase.rest.moviesapi.context.UserContext;
import com.backbase.rest.moviesapi.exception.ErrorResponse;
import com.backbase.rest.moviesapi.orchestrator.MovieInfoOrchestrator;
import com.backbase.rest.moviesapi.view.MovieDataView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.apache.catalina.connector.Request;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/movie", produces = MediaType.APPLICATION_JSON_VALUE)
public class MovieInfoController {

    @Resource
    private MovieInfoOrchestrator movieInfoOrchestrator;

    @Resource
    private UserContext userContext;

    @Operation(summary = "Gets info about the movie title with information on Whether it got Best Picture Oscar; Role Required: ROLE_USER")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
                    @ApiResponse(responseCode = "200",description =" Return the Picture information with Best Picture oscar info",
                                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = MovieDataView.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Exception",
                                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/bestpictureoscar/{title}")
    public ResponseEntity<MovieDataView> getBestPictureOscarDetails(@PathVariable("title") final String title) {
        return new ResponseEntity<>(movieInfoOrchestrator.getOscarInformation(userContext.getUserName(), title), HttpStatus.OK);
    }

    @Operation(summary = "Gets the top 10 movies based on the Rating given in this API. Also it sorts them in the descending order of Box office value, N/A in box office value will be kept at the; Role Required: ROLE_USER")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
                    @ApiResponse(responseCode = "200",description =" Return the top 10 movies based on the Rating given in this API sorted by box office value",
                                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = List.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Exception",
                                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/top10movies")
    public ResponseEntity<List<MovieDataView>> getTopTenMovies() {
        return new ResponseEntity<>(movieInfoOrchestrator.getTopTenMovies(userContext.getUserName()), HttpStatus.OK);
    }

}
