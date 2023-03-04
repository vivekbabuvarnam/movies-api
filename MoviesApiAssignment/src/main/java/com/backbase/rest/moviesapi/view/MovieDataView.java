package com.backbase.rest.moviesapi.view;

import java.util.List;

import com.backbase.rest.moviesapi.domain.Movie.OscarData;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovieDataView {

    private String title;
    private List<OscarData> oscar;
    private String awardName;
    private String boxOffice;
    private String imdbId;
    private String imdbRating;
    private Link addRatingLink;
    private Link deleteRatingLink;
    private Link viewTopTenMovieLink;
    private Link uploadBestPictureOscarWonCsvFeed;

    public Link getUploadBestPictureOscarWonCsvFeed() {
        return uploadBestPictureOscarWonCsvFeed;
    }

    public void setUploadBestPictureOscarWonCsvFeed(Link uploadBestPictureOscarWonCsvFeed) {
        this.uploadBestPictureOscarWonCsvFeed = uploadBestPictureOscarWonCsvFeed;
    }

    public Link getDeleteRatingLink() {
        return deleteRatingLink;
    }

    public void setDeleteRatingLink(Link deleteRatingLink) {
        this.deleteRatingLink = deleteRatingLink;
    }

    public Link getAddRatingLink() {
        return addRatingLink;
    }

    public void setAddRatingLink(Link addRatingLink) {
        this.addRatingLink = addRatingLink;
    }

    public Link getViewTopTenMovieLink() {
        return viewTopTenMovieLink;
    }

    public void setViewTopTenMovieLink(Link viewTopTenMovieLink) {
        this.viewTopTenMovieLink = viewTopTenMovieLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }

    public List<OscarData> getOscar() {
        return oscar;
    }

    public void setOscar(List<OscarData> oscar) {
        this.oscar = oscar;
    }

    public String getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(String boxOffice) {
        this.boxOffice = boxOffice;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }
}
