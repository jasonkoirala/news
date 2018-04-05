package com.example.jason.newsportal.Models;

/**
 * Created by jason on 14/08/2017.
 */

public class SportsNewsModel {

    private String sportsNewsHeading;
    private String sportsNewsSource;
    private String newsSportsImageURL;
    private String sportNewsDescription;
    private String sportsNewsURL;

    public String getSportsNewsURL() {
        return sportsNewsURL;
    }

    public void setSportsNewsURL(String sportsNewsURL) {
        this.sportsNewsURL = sportsNewsURL;
    }

    public String getNewsSportsImageURL() {
        return newsSportsImageURL;
    }

    public void setNewsSportsImageURL(String newsSportsImageURL) {
        this.newsSportsImageURL = newsSportsImageURL;
    }

    public String getSportsNewsHeading() {
        return sportsNewsHeading;
    }

    public void setSportsNewsHeading(String sportsNewsHeading) {
        this.sportsNewsHeading = sportsNewsHeading;
    }

    public String getSportsNewsSource() {
        return sportsNewsSource;
    }

    public void setSportsNewsSource(String sportsNewsSource) {
        this.sportsNewsSource = sportsNewsSource;
    }

    public String getSportNewsDescription() {
        return sportNewsDescription;
    }

    public void setSportNewsDescription(String sportNewsDescription) {
        this.sportNewsDescription = sportNewsDescription;
    }
}
