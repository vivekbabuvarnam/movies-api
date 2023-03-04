package com.backbase.rest.moviesapi.form;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

public class TitleRatingForm {

    @NotBlank(message = "Title is mandatory")
    private String title;

    @DecimalMin(value = "0.0", inclusive = false, message = "Enter value more than 0.0")
    @DecimalMax(value = "10.0", inclusive = true, message = "Enter value less than 10.0")
    private Double rating;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
