package com.example.moviejunkie.Classes;

import com.example.moviejunkie.Helper.CardPosterAdapter;
import com.example.moviejunkie.SqlLite.MovieSQL;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Movie implements Serializable {

    private int id;
    private String original_title;

    private ArrayList<Integer>genre_ids;

    private String poster_path;
    private String title;
    private String vote_average;

    public String getOverview() {
        return overview;
    }

    private int vote_count;
    private String overview;

    private String name;

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public String getIdString() {
        return String.valueOf(id);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFirstGenre(){
        return genre_ids.get(0);
    }


    public Movie() {
    }

    public ArrayList<Integer> GetGenreIds(){return genre_ids;}




    public Movie(int id, String original_title, ArrayList<Integer> genre_ids, String poster_path, String title, String vote_average, int vote_count, String overview, String name) {
        this.id = id;
        this.original_title = original_title;
        this.genre_ids = genre_ids;
        this.poster_path = poster_path;
        this.title = title;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.overview = overview;
        this.name = name;
    }

    public Movie(int id, String original_title, String title, String vote_average, int vote_count, String overview) {
        this.id = id;
        this.original_title = original_title;
        this.title = title;
        this.vote_average = vote_average;
        this.vote_count = vote_count;
        this.overview=overview;
    }

    public Movie(int id) {
        this.id = id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getTitle() {
        return title;
    }
}
