package com.example.rafaelmeyer.mymovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rafael.meyer on 10/3/16.
 */
public class Movie implements Parcelable {

    @SerializedName("Title")
    private String title;

    public String getTitle() { return this.title; }

    public void setTitle(String title) { this.title = title; }

    @SerializedName("Year")
    private String year;

    public String getYear() { return this.year; }

    public void setYear(String year) { this.year = year; }

    @SerializedName("imdbID")
    private String imdbID;

    public String getImdbID() { return this.imdbID; }

    public void setImdbID(String imdbID) { this.imdbID = imdbID; }

    @SerializedName("Type")
    private String type;

    public String getType() { return this.type; }

    public void setType(String type) { this.type = type; }

    @SerializedName("Poster")
    private String poster;

    public String getPoster() { return this.poster; }

    public void setPoster(String poster) { this.poster = poster; }

    public Movie(String title, String year, String type, String poster) {
        this.title = title;
        this.year = year;
        this.type = type;
        this.poster = poster;
    }

    public Movie(String title, String year, String imdbID, String type, String poster) {
        this.title = title;
        this.year = year;
        this.imdbID = imdbID;
        this.type = type;
        this.poster = poster;
    }

    protected Movie(Parcel source) {
        title = source.readString();
        year = source.readString();
        imdbID = source.readString();
        type = source.readString();
        poster = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(year);
        dest.writeString(imdbID);
        dest.writeString(type);
        dest.writeString(poster);
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
