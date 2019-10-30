package com.example.moviejunkie.Classes;

public class MovieInfoData {

    public Integer id;
    private Integer budget;
    private String original_language;
    private Integer vote_average;
    private String release_date;
    private Integer runtime;

    public Integer getRuntime() {
        return runtime;
    }

    public MovieInfoData(Integer id, Integer budget, String original_language, Integer vote_average, String release_date, Integer runtime) {
        this.id = id;
        this.budget = budget;
        this.original_language = original_language;
        this.vote_average = vote_average;
        this.release_date = release_date;
        this.runtime = runtime;
    }

    public MovieInfoData() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public Integer getVote_average() {
        return vote_average;
    }

    public void setVote_average(Integer vote_average) {
        this.vote_average = vote_average;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }
}
