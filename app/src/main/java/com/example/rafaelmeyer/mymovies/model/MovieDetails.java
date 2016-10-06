package com.example.rafaelmeyer.mymovies.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rafael.meyer on 10/6/16.
 */
public class MovieDetails {
    @SerializedName("Title")
    String title;
    @SerializedName("Year")
    String year;
    @SerializedName("rated")
    String rated;
    @SerializedName("Released")
    String released;
    @SerializedName("Runtime")
    String runtime;
    @SerializedName("Genre")
    String genre;
    @SerializedName("Director")
    String director;
    @SerializedName("Writer")
    String writer;
    @SerializedName("Actors")
    String actors;
    @SerializedName("Plot")
    String plot;
    @SerializedName("Language")
    String language;
    @SerializedName("Country")
    String country;
    @SerializedName("Awards")
    String awards;
    @SerializedName("Poster")
    String poster;
    @SerializedName("Metascore")
    String metascore;
    @SerializedName("imdbRating")
    String imdbRating;
    @SerializedName("imdbVotes")
    String imdbVotes;
    @SerializedName("imdbID")
    String imdbID;
    @SerializedName("Type")
    String type;
    @SerializedName("Response")
    String response;

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getRated() {
        return rated;
    }

    public String getReleased() {
        return released;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getGenre() {
        return genre;
    }

    public String getDirector() {
        return director;
    }

    public String getWriter() {
        return writer;
    }

    public String getActors() {
        return actors;
    }

    public String getPlot() {
        return plot;
    }

    public String getLanguage() {
        return language;
    }

    public String getCountry() {
        return country;
    }

    public String getAwards() {
        return awards;
    }

    public String getPoster() {
        return poster;
    }

    public String getMetascore() {
        return metascore;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public String getImdbVotes() {
        return imdbVotes;
    }

    public String getImdbID() {
        return imdbID;
    }

    public String getType() {
        return type;
    }

    public String getResponse() {
        return response;
    }
}
