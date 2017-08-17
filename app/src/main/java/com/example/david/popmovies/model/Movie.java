package com.example.david.popmovies.model;

import java.io.Serializable;
import java.util.Date;


public class Movie implements Serializable{

    /**
     * Original title of the movie
     */
    private String title;
    /**
     * Poster image URL
     */
    private String imageURL;
    /**
     * Movie plot
     */
    private String plot;
    /**
     * User rating
     */
    private double rating;
    /**
     * Release date
     */
    private Date releaseDate;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
}
