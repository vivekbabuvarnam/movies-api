package com.backbase.rest.moviesapi.form;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

public class ImdbIdRatingForm {
    @NotBlank(message = "ImdbID is mandatory")
    private String imdbId;

    @DecimalMin(value = "0.0", inclusive = false, message = "Enter value more than 0.0")
    @DecimalMax(value = "10.0", inclusive = true, message = "Enter value less than 10.0")
    private Double rating;

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
