package com.backbase.rest.moviesapi.domain;

import com.opencsv.bean.CsvBindByPosition;

public class AcademyAwardsData {

    @CsvBindByPosition(position = 0)
    private String year;

    @CsvBindByPosition(position = 1)
    private String category;

    @CsvBindByPosition(position = 2)
    private String nominee;

    @CsvBindByPosition(position = 3)
    private String additionalInfo;

    @CsvBindByPosition(position = 4)
    private String won;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNominee() {
        return nominee;
    }

    public void setNominee(String nominee) {
        this.nominee = nominee;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public String getWon() {
        return won;
    }

    public void setWon(String won) {
        this.won = won;
    }

}
