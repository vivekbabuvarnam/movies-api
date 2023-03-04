package com.backbase.rest.moviesapi.domain.Movie;

public class OscarData {
    private String oscarName;
    private boolean won;

    public String getOscarName() {
        return oscarName;
    }

    public void setOscarName(String oscarName) {
        this.oscarName = oscarName;
    }

    public boolean isWon() {
        return won;
    }

    public void setWon(boolean won) {
        this.won = won;
    }
}
