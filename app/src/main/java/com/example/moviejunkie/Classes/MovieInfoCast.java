package com.example.moviejunkie.Classes;

public class MovieInfoCast {

    private Integer id;
    private String name;
    private String character;
    private String profile_path;

    public String getProfile_path() {
        return profile_path;
    }

    public String getName() {
        return name;
    }

    public String getCharacter() {
        return character;
    }

    public MovieInfoCast() {
    }

    public String  GetRole(){
        return name+" as "+ character;
    }

    public MovieInfoCast(String name, String character) {
        this.name = name;
        this.character = character;
    }
}
