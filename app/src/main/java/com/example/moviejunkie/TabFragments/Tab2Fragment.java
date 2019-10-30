package com.example.moviejunkie.TabFragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.moviejunkie.Classes.MovieInfoData;
import com.example.moviejunkie.MovieInfo;
import com.example.moviejunkie.R;
import com.example.moviejunkie.SqlLite.MovieInfoSQL;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Tab2Fragment extends Fragment {


    private String MovieID;
    public MovieInfoData movieInfo = new MovieInfoData();

    private String YoutubeLink = "https://www.youtube.com/watch?v=";
    private String WatchUrl = "";

    private Button Trailer;
    private TextView Budget;
    private TextView ReleaseDate;
    private TextView RunTime;
    private TextView Rating;
    private TextView Language;

    private String URl = "https://api.themoviedb.org/3/movie/{MOVIE}?api_key=77da696d93b5f500c3f18a0aba2691f9&language=en-US&append_to_response=videos";

    private MovieInfoSQL SqlData;
    private boolean Offline=false;


    public Tab2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (getArguments() != null) {
            Integer id = getArguments().getInt("MOVIE_ID");
            MovieID = id.toString();
            SqlData=(MovieInfoSQL) getArguments().getSerializable("MOVIE_DATA");

            if (SqlData!=null)
                Offline=true;


        }


        return inflater.inflate(R.layout.fragment_tab2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Trailer = view.findViewById(R.id.MovieInfoTrailer);
        Budget = view.findViewById(R.id.MovieInfoBudget);
        ReleaseDate = view.findViewById(R.id.MovieInfoReleaseDate);
        RunTime = view.findViewById(R.id.MovieInfoRuntime);
        Rating = view.findViewById(R.id.MovieInfoVoteRating);
        Language = view.findViewById(R.id.MovieInfoLanguage);


        if (Offline){

            set_offiline_data();


        }else{

            AndroidNetworking.get(URl)
                    .addPathParameter("MOVIE", MovieID)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("RESPONSE", response.toString());

                            Gson gson = new Gson();
                            JsonObject GJsonObject = gson.fromJson(response.toString(), JsonObject.class);
                            movieInfo = gson.fromJson(GJsonObject, MovieInfoData.class);

                            try{

                                JSONObject videos= response.getJSONObject("videos");
                                JSONArray trailers=videos.getJSONArray("results");
                                JSONObject trailer1=trailers.getJSONObject(0);
                                WatchUrl=trailer1.getString("key");

                            }catch (JSONException e){
                                Log.d("JSON_ERROR", "onResponse: "+e.getMessage());
                            }


//                        JsonArray TrailersJson = GJsonObject.getAsJsonArray("videos");
//                        JsonObject FirstTrailer = (JsonObject) TrailersJson.get(0);
                            //WłatchUrl = FirstTrailer.get("key").getAsString();
                           // Toast.makeText(getActivity(), "KEY::" + WatchUrl, Toast.LENGTH_SHORT).show();

                            do_updateTextViews();
                            do_setTrailerButton();
                        }


                        @Override
                        public void onError(ANError anError) {

                        }
                    });


        }




    }

    private void set_offiline_data() {

        Trailer.setVisibility(View.GONE);

        if (SqlData.getBudget()==null){
            Budget.append(" N/A ");
        }else{
            Budget.append(SqlData.getBudget() + " $");
        }

        ReleaseDate.append(SqlData.getRelease_date());
        RunTime.append(SqlData.getRuntime().toString() + " minutes");
        Rating.append(SqlData.getVote_average().toString());
        Language.append(SqlData.getOriginal_language());


    }

    private void do_setTrailerButton() {

        Trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent videoClient = new Intent(Intent.ACTION_VIEW);
                videoClient.setData(Uri.parse("http://m.youtube.com/watch?v=" + WatchUrl));
                startActivityForResult(videoClient, 1234);
            }
        });


    }

    private void do_updateTextViews() {


        if (movieInfo.getBudget()==null){
            Budget.append(" N/A ");
        }else{
            Budget.append(movieInfo.getBudget() + " $");
        }

        ReleaseDate.append(movieInfo.getRelease_date());


        if(movieInfo.getRuntime()!=null){
            RunTime.append(movieInfo.getRuntime().toString().isEmpty() ? " N/A" : movieInfo.getRuntime().toString() + " minutes");
        }else{
            RunTime.append( " N/A" );
        }


        Rating.append(movieInfo.getVote_average().toString());
        Language.append(movieInfo.getOriginal_language());


    }


}
