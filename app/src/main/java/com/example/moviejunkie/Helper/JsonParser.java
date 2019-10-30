package com.example.moviejunkie.Helper;

import android.util.Log;

import com.example.moviejunkie.Classes.MovieInfoCast;
import com.example.moviejunkie.Classes.MovieInfoData;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonParser {


    public static MovieInfoData GetMovieInfo(JSONObject response) {

        Gson gson = new Gson();
        JsonObject GJsonObject = gson.fromJson(response.toString(), JsonObject.class);
        MovieInfoData movieInfo = gson.fromJson(GJsonObject, MovieInfoData.class);

        return movieInfo;
    }


    public static String GetTrailerString(JSONObject response) {

        try {

            JSONObject videos = response.getJSONObject("videos");
            JSONArray trailers = videos.getJSONArray("results");
            JSONObject trailer1 = trailers.getJSONObject(0);
            return trailer1.getString("key");

        } catch (JSONException e) {
            Log.d("JSON_ERROR", "onResponse: " + e.getMessage());
        }

        return "";
    }


    public static List<MovieInfoCast> GetActors(JSONObject response) {

        List<MovieInfoCast> movieCast = new ArrayList<MovieInfoCast>();

        Gson gson = new Gson();

        try {

            JSONArray array = response.getJSONArray("cast");
            for (int i = 0; i < array.length(); i++) {

                JSONObject myData = array.getJSONObject(i);
                JsonObject element = gson.fromJson(myData.toString(), JsonObject.class);
                MovieInfoCast actor = gson.fromJson(element, MovieInfoCast.class);

                movieCast.add(actor);
            }


        } catch (JSONException error) {


        }

        return movieCast;

    }


}
