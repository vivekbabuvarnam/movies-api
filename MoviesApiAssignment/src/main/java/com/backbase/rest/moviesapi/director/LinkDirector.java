package com.backbase.rest.moviesapi.director;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import javax.annotation.Resource;

import com.backbase.rest.moviesapi.context.ApiUriContext;
import com.backbase.rest.moviesapi.controller.MovieInfoController;
import com.backbase.rest.moviesapi.controller.RatingController;
import com.backbase.rest.moviesapi.form.ImdbIdRatingForm;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@Component
public class LinkDirector {

    @Resource
    private ApiUriContext uriContext;

    public String addTopTenMovieLink(String user) {
        return MvcUriComponentsBuilder.relativeTo(uriContext.getUriComponentsBuilder())
                        .withMethodCall(on(MovieInfoController.class).getTopTenMovies()) //
                        .build() //
                        .toString();
    }

    public String addRatingLink(String user) {
        return MvcUriComponentsBuilder.relativeTo(uriContext.getUriComponentsBuilder())
                        .withMethodCall(on(RatingController.class).postRatingWithImdbId(new ImdbIdRatingForm())) //
                        .build() //
                        .toString();
    }

    public String deleteRatingLink(String user, long id) {
        return MvcUriComponentsBuilder.relativeTo(uriContext.getUriComponentsBuilder())
                        .withMethodCall(on(RatingController.class).deleteRatingBasedOnId(id)) //
                        .build() //
                        .toString();
    }

    public String addCsvFileUploadLink() {
        return "http://localhost:8080/upload/best-picture-oscar-csv-file/";
    }

}
