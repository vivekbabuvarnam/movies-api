package com.backbase.rest.moviesapi.entities;

// user key and imdbId combo needs to be unique

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "movierating")
public class MovieRating implements Comparable<MovieRating> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_rating_id_seq_gen")
    @SequenceGenerator(name = "movie_rating_id_seq_gen", sequenceName = "movie_rating_id_seq", allocationSize = 1)
    private long id;

    @Column(name = "unique_value", nullable = false)
    private String uniqueValue;

    @Column(name = "user_key", nullable = false)
    private String userKey;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "imdb_id", nullable = false)
    private String imdbId;

    @Column(name = "imdb_rating", nullable = false)
    private Double imdbRating;

    @Column(name = "box_office", nullable = false)
    private String boxOffice;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUniqueValue() {
        return uniqueValue;
    }

    public void setUniqueValue(String uniqueValue) {
        this.uniqueValue = uniqueValue;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public Double getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(Double imdbRating) {
        this.imdbRating = imdbRating;
    }

    public String getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(String boxOffice) {
        this.boxOffice = boxOffice;
    }

    @Override
    public int compareTo(MovieRating movieRating) {
        String currentBoxOffice = this.getBoxOffice();
        String nextBoxOffice = movieRating.getBoxOffice();

        if (currentBoxOffice.equalsIgnoreCase("N/A") && nextBoxOffice.equalsIgnoreCase("N/A")) {
            return 0;
        }
        else if (currentBoxOffice.equalsIgnoreCase("N/A")) {
            return +1;
        }
        else if (nextBoxOffice.equalsIgnoreCase("N/A")) {
            return -1;
        }
        else {
            Long current = Long.valueOf(currentBoxOffice.replace("$", "").replace(",", ""));
            Long next = Long.valueOf(nextBoxOffice.replace("$", "").replace(",", ""));
            return (int) (next - current);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        MovieRating that = (MovieRating) o;
        return id == that.id &&
                        uniqueValue.equals(that.uniqueValue) &&
                        userKey.equals(that.userKey) &&
                        title.equals(that.title) &&
                        imdbId.equals(that.imdbId) &&
                        imdbRating.equals(that.imdbRating) &&
                        boxOffice.equals(that.boxOffice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uniqueValue, userKey, title, imdbId, imdbRating, boxOffice);
    }
}
