package com.example.moviejunkie.TabFragments;


import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.moviejunkie.Classes.MovieInfoCast;
import com.example.moviejunkie.R;
import com.example.moviejunkie.SqlLite.MovieInfoSQL;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Tab3Fragment extends Fragment {


    private String URL = "https://api.themoviedb.org/3/movie/{MOVIE}/credits?api_key=77da696d93b5f500c3f18a0aba2691f9";
    private String id;

    private TextView Actor1Name;
    private TextView Actor1Role;
    private TextView Actor2Name;
    private TextView Actor2Role;

    private ImageView Actor1Picture;
    private ImageView Actor2Picture;

    private List<MovieInfoCast> movieCast = new ArrayList<MovieInfoCast>();

    private MovieInfoSQL SqlData;
    private boolean Offline = false;

    public Tab3Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if (getArguments() != null) {
            Integer ID = getArguments().getInt("MOVIE_ID");
            id = ID.toString();

            SqlData = (MovieInfoSQL) getArguments().getSerializable("MOVIE_DATA");

            if (SqlData != null)
                Offline = true;
        }


        Actor1Name = view.findViewById(R.id.Actor1Name);
        Actor1Role = view.findViewById(R.id.Acto1Role);
        Actor2Name = view.findViewById(R.id.Actor2Name);
        Actor2Role = view.findViewById(R.id.Actor2Role);
        Actor1Picture = view.findViewById(R.id.Actor1Image);
        Actor2Picture = view.findViewById(R.id.Actor2Image);


        if (Offline) {

            Actor1Name.setText(SqlData.getName1());
            Actor1Role.setText(SqlData.getCharacter1());
            Actor2Name.setText(SqlData.getName2());
            Actor2Role.setText(SqlData.getCharacter2());

            Actor1Picture.setImageBitmap(BitmapFactory.decodeByteArray(SqlData.getProfile_path1(), 0, SqlData.getProfile_path1().length));
            Actor2Picture.setImageBitmap(BitmapFactory.decodeByteArray(SqlData.getProfile_path2(), 0, SqlData.getProfile_path2().length));


        } else {

            AndroidNetworking.get(URL)
                    .addPathParameter("MOVIE", id)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Gson gson = new Gson();

                            try {

                                JSONArray array = response.getJSONArray("cast");
                               // Toast.makeText(getActivity(), "ARRAY SIZE::" + array.length(), Toast.LENGTH_SHORT).show();
                                for (int i = 0; i < array.length(); i++) {

                                    JSONObject myData = array.getJSONObject(i);
                                    JsonObject element = gson.fromJson(myData.toString(), JsonObject.class);
                                    MovieInfoCast actor = gson.fromJson(element, MovieInfoCast.class);

                                    movieCast.add(actor);
                                }

                                do_updateTextviews();

                            } catch (JSONException error) {


                            }

                        }

                        @Override
                        public void onError(ANError anError) {

                        }
                    });

        }


    }

    private void do_updateTextviews() {


        Picasso.get().load("https://image.tmdb.org/t/p/w342" + movieCast.get(0).getProfile_path()).placeholder(R.drawable.load).into(Actor1Picture);
        Picasso.get().load("https://image.tmdb.org/t/p/w342" + movieCast.get(1).getProfile_path()).placeholder(R.drawable.load).into(Actor2Picture);

        Actor1Name.setText(movieCast.get(0).getName());
        Actor1Role.setText(movieCast.get(0).getCharacter());
        Actor2Name.setText(movieCast.get(1).getName());
        Actor2Role.setText(movieCast.get(1).getCharacter());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab3, container, false);
    }

}
