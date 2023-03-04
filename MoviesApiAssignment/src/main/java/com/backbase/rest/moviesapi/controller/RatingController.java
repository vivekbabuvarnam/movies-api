package com.backbase.rest.moviesapi.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import com.backbase.rest.moviesapi.context.UserContext;
import com.backbase.rest.moviesapi.exception.ErrorResponse;
import com.backbase.rest.moviesapi.form.ImdbIdRatingForm;
import com.backbase.rest.moviesapi.form.TitleRatingForm;
import com.backbase.rest.moviesapi.orchestrator.MoviesRatingOrchestrator;
import com.backbase.rest.moviesapi.view.MovieDataView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/rating", produces = MediaType.APPLICATION_JSON_VALUE)
public class RatingController {

    @Resource
    private MoviesRatingOrchestrator moviesRatingOrchestrator;

    @Resource
    private UserContext userContext;

    @Operation(summary = "Post the Rating of the movie based on the Title; Role Required: ROLE_USER")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
                    @ApiResponse(responseCode = "201",description =" Returns the Resource information which got created/updated",
                                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = MovieDataView.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Exception",
                                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/title")
    public ResponseEntity<MovieDataView> postRatingWithTitle(@Valid @RequestBody TitleRatingForm titleRatingForm) {

        return new ResponseEntity<>(moviesRatingOrchestrator.giveRatingBasedOnTitle(userContext.getUserName(), titleRatingForm), HttpStatus.CREATED);
    }

    @Operation(summary = "Post the Rating of the movie based on the Imdb Id; Role Required: ROLE_USER")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
                    @ApiResponse(responseCode = "201",description =" Returns the Resource information which got created/updated",
                                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = MovieDataView.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Exception",
                                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/imdb")
    public ResponseEntity<MovieDataView> postRatingWithImdbId(@Valid @RequestBody ImdbIdRatingForm imdbIdRatingForm) {

        return new ResponseEntity<>(moviesRatingOrchestrator.giveRatingBasedOnImdb(userContext.getUserName(), imdbIdRatingForm), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete the rating given based on the Resource Id. It won't delete if the userKey of the resource not matches with the userKey; Role Required: ROLE_USER")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
                    @ApiResponse(responseCode = "200",description =" Empty success message if the delete was successful",
                                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = MovieDataView.class))),
                    @ApiResponse(responseCode = "500", description = "Internal Server Exception",
                                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/imdb/{id}")
    public ResponseEntity<Void> deleteRatingBasedOnId(@PathVariable("id") final Long id) {
        moviesRatingOrchestrator.deleteRatingOnId(userContext.getUserName(), id);
        return ResponseEntity.ok().build();
    }
}
