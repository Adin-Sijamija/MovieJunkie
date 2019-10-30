package com.example.moviejunkie.SqlLite;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "MovieSQL")
public class MovieSQL {


    @PrimaryKey(autoGenerate = true)
    @NonNull private int id;

    private int movie_id;
    private String original_title;
    private String poster_path;
    private String title;
    private byte[] poster;
    private Integer status;
    private int user_id;


    public MovieSQL(int movie_id, String original_title, String poster_path, String title, byte[] poster, Integer status, int user_id) {
        this.movie_id = movie_id;
        this.original_title = original_title;
        this.poster_path = poster_path;
        this.title = title;
        this.poster = poster;
        this.status = status;
        this.user_id = user_id;
    }


    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setPoster(byte[] poster) {
        this.poster = poster;
    }

    public Integer getStatus() {
        return status;
    }

    public byte[] getPoster() {
        return poster;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public String getTitle() {
        return title;
    }


    public void setStatus(Integer status) {
        this.status = status;
    }
}
