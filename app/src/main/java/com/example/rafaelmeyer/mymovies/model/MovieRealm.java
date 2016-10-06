package com.example.rafaelmeyer.mymovies.model;

import io.realm.RealmObject;

/**
 * Created by rafael.meyer on 10/3/16.
 */
public class MovieRealm extends RealmObject {

    private String title;

    public String getTitle() { return this.title; }

    public void setTitle(String title) { this.title = title; }

    private String year;

    public String getYear() { return this.year; }

    public void setYear(String year) { this.year = year; }

    private String imdbID;

    public String getImdbID() { return this.imdbID; }

    public void setImdbID(String imdbID) { this.imdbID = imdbID; }

    private String type;

    public String getType() { return this.type; }

    public void setType(String type) { this.type = type; }

    private String poster;

    public String getPoster() { return this.poster; }

    public void setPoster(String poster) { this.poster = poster; }

}
