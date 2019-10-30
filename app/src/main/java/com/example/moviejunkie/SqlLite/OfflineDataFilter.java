package com.example.moviejunkie.SqlLite;

import java.util.ArrayList;

public class OfflineDataFilter {

    //Klasa za offline filtriranej podataka

    private Boolean Reset = false;

    public Integer vote_min;
    public Integer vote_max;
    public String release_date_min;
    public String release_date_max;
    public ArrayList<Integer> genre_ids;

    public OfflineDataFilter(Integer vote_min, Integer vote_max, String release_date_min, String release_date_max, ArrayList<Integer> genre_ids) {
        this.vote_min = vote_min;
        this.vote_max = vote_max;
        this.release_date_min = release_date_min;
        this.release_date_max = release_date_max;
        this.genre_ids = genre_ids;
    }

    public OfflineDataFilter() {
    }


    public void setReset(Boolean reset) {
        Reset = reset;
    }

    public boolean IsReset() {
        return Reset;
    }

    public Integer getVote_min() {
        return vote_min;
    }

    public void setVote_min(Integer vote_min) {
        this.vote_min = vote_min;
    }

    public Integer getVote_max() {
        return vote_max;
    }

    public void setVote_max(Integer vote_max) {
        this.vote_max = vote_max;
    }

    public String getRelease_date_min() {
        return release_date_min;
    }

    public void setRelease_date_min(String release_date_min) {
        this.release_date_min = release_date_min;
    }

    public String getRelease_date_max() {
        return release_date_max;
    }

    public void setRelease_date_max(String release_date_max) {
        this.release_date_max = release_date_max;
    }

    public ArrayList<Integer> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(ArrayList<Integer> genre_ids) {
        this.genre_ids = genre_ids;
    }
}
