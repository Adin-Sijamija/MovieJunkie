package com.example.moviejunkie.SqlLite;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "MovieInfoSQL")
public class MovieInfoSQL implements Serializable {


    @PrimaryKey
    @NonNull
    private Integer id;


    private Integer budget;
    private String original_language;
    private Integer vote_average;
    private String release_date;
    private Integer runtime;
    private String overview;

    private String name1;
    private String character1;
    private byte[] profile_path1;

    private String name2;
    private String character2;
    private byte[] profile_path2;

    private String genres;


    public MovieInfoSQL(@NonNull Integer id, Integer budget, String original_language, Integer vote_average, String release_date, Integer runtime, String overview, String name1, String character1, byte[] profile_path1, String name2, String character2, byte[] profile_path2, String genres) {
        this.id = id;
        this.budget = budget;
        this.original_language = original_language;
        this.vote_average = vote_average;
        this.release_date = release_date;
        this.runtime = runtime;
        this.overview = overview;
        this.name1 = name1;
        this.character1 = character1;
        this.profile_path1 = profile_path1;
        this.name2 = name2;
        this.character2 = character2;
        this.profile_path2 = profile_path2;
        this.genres = genres;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public void setId(@NonNull Integer id) {
        this.id = id;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public void setVote_average(Integer vote_average) {
        this.vote_average = vote_average;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public void setCharacter1(String character1) {
        this.character1 = character1;
    }

    public void setProfile_path1(byte[] profile_path1) {
        this.profile_path1 = profile_path1;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public void setCharacter2(String character2) {
        this.character2 = character2;
    }

    public void setProfile_path2(byte[] profile_path2) {
        this.profile_path2 = profile_path2;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    public Integer getBudget() {
        return budget;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public Integer getVote_average() {
        return vote_average;
    }

    public String getRelease_date() {
        return release_date;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public String getOverview() {
        return overview;
    }

    public String getName1() {
        return name1;
    }

    public String getCharacter1() {
        return character1;
    }

    public byte[] getProfile_path1() {
        return profile_path1;
    }

    public String getName2() {
        return name2;
    }

    public String getCharacter2() {
        return character2;
    }

    public byte[] getProfile_path2() {
        return profile_path2;
    }
}
