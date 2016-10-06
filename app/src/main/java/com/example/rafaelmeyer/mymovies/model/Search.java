package com.example.rafaelmeyer.mymovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by rafael.meyer on 10/3/16.
 */
public class Search {

    @SerializedName("Search")
    private List<Movie> search;

    public Search(List<Movie> search) {
        this.search = search;
    }

    public List<Movie> getSearch() { return this.search; }

    public void setSearch(List<Movie> search) { this.search = search; }

    private String totalResults;

    public String getTotalResults() { return this.totalResults; }

    public void setTotalResults(String totalResults) { this.totalResults = totalResults; }

    private String Response;

    public String getResponse() { return this.Response; }

    public void setResponse(String Response) { this.Response = Response; }

}
