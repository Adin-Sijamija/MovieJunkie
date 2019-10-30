package com.example.moviejunkie.SqlLite;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "UserSQL")
public class UserSQL {


    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String user_name;
    private String user_password;
    private int logged_in;


    public UserSQL( String user_name, String user_password, int logged_in) {

        this.user_name = user_name;
        this.user_password = user_password;
        this.logged_in = logged_in;
    }

    public int getLogged_in() {
        return logged_in;
    }

    public void setLogged_in(int logged_in) {
        this.logged_in = logged_in;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }
}
