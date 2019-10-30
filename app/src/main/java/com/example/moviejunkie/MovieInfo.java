package com.example.moviejunkie;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.ANResponse;
import com.example.moviejunkie.Classes.MovieInfoCast;
import com.example.moviejunkie.Classes.MovieInfoData;
import com.example.moviejunkie.Helper.ImageProcessor;
import com.example.moviejunkie.Helper.JsonParser;
import com.example.moviejunkie.SqlLite.AppDatabase;
import com.example.moviejunkie.SqlLite.MovieDAO;
import com.example.moviejunkie.SqlLite.MovieInfoSQL;
import com.example.moviejunkie.SqlLite.MovieSQL;
import com.example.moviejunkie.SqlLite.UserSQL;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.moviejunkie.Classes.Genres;
import com.example.moviejunkie.Classes.Movie;
import com.example.moviejunkie.Helper.CardPosterAdapter;
import com.example.moviejunkie.Helper.TabAdapter;
import com.example.moviejunkie.TabFragments.Tab1Fragment;
import com.example.moviejunkie.TabFragments.Tab2Fragment;
import com.example.moviejunkie.TabFragments.Tab3Fragment;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MovieInfo extends AppCompatActivity {

    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    private Movie movie;

    private LinearLayout GenresCrumbs;


    ArrayList<String> Number;
    ArrayList<Movie> similarMovies = new ArrayList<Movie>();


    private RecyclerView recyclerView;
    private ImageView Poster;
    private TextView Title;


    private Button AddButton;
    private Button test2;

    private Boolean IsAdded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);

        IsAdded=false;

        AddButton = findViewById(R.id.DbTestButton);

        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                do_setListener(v);

            }
        });

//        test2 = findViewById(R.id.button3);
//        test2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(final View v) {
//
//                AsyncTask.execute(new Runnable() {
//                    @Override
//                    public void run() {
//                        AppDatabase appDatabase = AppDatabase.getInstance(v.getContext());
//                        MovieDAO dao = appDatabase.getMovieDAO();
//
//
//                        List<MovieSQL> movies = dao.getAllMovies();
//                        Log.d("DB", "ADDED:::  " + movies.size());
//
//                        dao.EmptyMovieTable();
//                        dao.EmptyMovieInfoTable();
//                        List<MovieSQL> movies2 = dao.getAllMovies();
//                        Log.d("DB", "CLEARED SIZE:::  " + movies2.size());
//                    }
//                });
//
//
//            }
//        });


        Intent i = getIntent();
        movie = (Movie) i.getSerializableExtra("FILM");


        viewPager = (ViewPager) findViewById(R.id.TabsID);
        tabLayout = (TabLayout) findViewById(R.id.TabLayoutId);
        adapter = new TabAdapter(getSupportFragmentManager());

        Tab1Fragment tab1 = new Tab1Fragment();
        Bundle tab1b = new Bundle();
        tab1b.putString("MOVIE_OVERVIEW", movie.getOverview());
        tab1.setArguments(tab1b);
        adapter.addFragment(tab1, "DESCRIPTION");

        //Toast.makeText(this, "GENRE:::" + movie.getFirstGenre(), Toast.LENGTH_SHORT).show();


        Tab2Fragment tab2 = new Tab2Fragment();
        Bundle tab2b = new Bundle();
        tab2b.putInt("MOVIE_ID", movie.getId());
        tab2.setArguments(tab2b);
        adapter.addFragment(tab2, "INFO");


        Tab3Fragment tab3 = new Tab3Fragment();
        Bundle tab3b = new Bundle();
        tab3b.putInt("MOVIE_ID", movie.getId());
        tab3.setArguments(tab3b);
        adapter.addFragment(tab3, "CAST");


        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);



        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        /* Set Horizontal LinearLayout to RecyclerView */
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(mLayoutManager);

        /* Set MyCustomAdapter to RecyclerView */
        //recyclerView.setAdapter(new CardPosterAdapter(_listDataModel));


        Poster = findViewById(R.id.MovieInfoPoster);
        String base = "https://image.tmdb.org/t/p/w342";
        Picasso.get().load(base + movie.getPoster_path()).placeholder(R.drawable.load).fit().into(Poster);

        Title = findViewById(R.id.MovieInfoTitle);
        Title.setText(movie.getTitle());

        GenresCrumbs = findViewById(R.id.GenresCruumbs);
        do_setGenres();
        do_setSimilarMovies();





    }

    @Nullable
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        check_if_added();
        return super.onCreateView(parent, name, context, attrs);
    }

    private void check_if_added() {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                AppDatabase appDatabase = AppDatabase.getInstance(getApplicationContext());
                MovieDAO dao = appDatabase.getMovieDAO();



                    Intent i = getIntent();
                    Movie movie2=(Movie) i.getSerializableExtra("FILM");

                UserSQL userSQL=dao.GetCurrentUser();
                MovieSQL movie=dao.GetMovie(movie2.getId(),userSQL.getId());

                    if (movie!=null){
                        IsAdded=true;


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AddButton.setText("-REMOVE");
                            }
                        });

                    }


            }
        });



    }

    private void do_setListener(final View v) {


        if (IsAdded){

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    AppDatabase appDatabase = AppDatabase.getInstance(v.getContext());
                    MovieDAO dao = appDatabase.getMovieDAO();

                    UserSQL userSQL=dao.GetCurrentUser();

                    List<MovieSQL> movies=dao.getAllMovieByID(movie.getId());


                    Intent i = getIntent();
                    Movie movie2=(Movie) i.getSerializableExtra("FILM");
                    dao.RemoveMovie(movie2.getId(),userSQL.getId());

                    if (movies.size()==1)
                        dao.DeleteMovieInfo(movie2.getId());

                    Snackbar.make(v, R.string.MovieRemoved, Snackbar.LENGTH_LONG)
                            .setBackgroundTint(getResources().getColor(R.color.colorGrey3))
                            .setTextColor(getResources().getColor(R.color.colorOnBackground))
                            .show();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AddButton.setText("+ADD");
                        }
                    });

                    IsAdded=false;

                }
            });

        }else {
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    AppDatabase appDatabase = AppDatabase.getInstance(v.getContext());
                    MovieDAO dao = appDatabase.getMovieDAO();

                    UserSQL userSQL=dao.GetCurrentUser();

                    MovieSQL movieSQL = new MovieSQL(movie.getId(), movie.getName(), movie.getPoster_path(), movie.getTitle(), ImageProcessor.getImageFromUrl(movie.getPoster_path()), 2,userSQL.getId());
                    dao.AddMovie(movieSQL);

                    Snackbar.make(v, R.string.MovieBookmarked, Snackbar.LENGTH_LONG)
                            .setBackgroundTint(getResources().getColor(R.color.colorGrey3))
                            .setTextColor(getResources().getColor(R.color.colorOnBackground))
                            .show();


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AddButton.setText("-REMOVE");
                        }
                    });

//                MovieInfoData data = .GetMovieInfo(movie.getIdString());
//                Log.d("JSON PARSER", "Json parser get movie info:: "+data.getRuntime());


                    MovieInfoSQL movieInfoSQLInfo=dao.GetMovieInfo(movie.getId());

                    if(movieInfoSQLInfo==null){
                        Log.d("DB", "MOVIE_INFO_SQL IS NOT NULL: ");

                        final MovieInfoData[] movieInfoData = {new MovieInfoData()};
                        final List<MovieInfoCast>[] movieCast = new List[]{new ArrayList<MovieInfoCast>()};
                        final MovieInfoCast[] cast1 = {new MovieInfoCast()};
                        final MovieInfoCast[] cast2 = {new MovieInfoCast()};


                        try {
                            Thread t = new Thread(new Runnable() {
                                @Override
                                public void run() {

                                    ANRequest request = AndroidNetworking.get("https://api.themoviedb.org/3/movie/{MOVIE}?api_key=77da696d93b5f500c3f18a0aba2691f9&language=en-US&append_to_response=videos")
                                            .addPathParameter("MOVIE", movie.getIdString())
                                            .build();

                                    ANResponse<JSONObject> response = request.executeForJSONObject();
                                    if (response.isSuccess()) {
                                        JSONObject result = response.getResult();
                                        Log.d("RUNNABLE", "Json data1:: " + result.toString());

                                        movieInfoData[0] = JsonParser.GetMovieInfo(result);
                                        Log.d("RUNNABLE", "actor arary size:: " + movieInfoData.length);

                                    }

                                    ANRequest request2 = AndroidNetworking.get("https://api.themoviedb.org/3/movie/{MOVIE}/credits?api_key=77da696d93b5f500c3f18a0aba2691f9")
                                            .addPathParameter("MOVIE", movie.getIdString())
                                            .build();

                                    ANResponse<JSONObject> response2 = request2.executeForJSONObject();
                                    if (response2.isSuccess()) {
                                        JSONObject result2 = response2.getResult();
                                        Log.d("RUNNABLE", "Json data2:: " + result2.toString());

                                        List<MovieInfoCast> cast = new ArrayList<>(JsonParser.GetActors(result2)) ;
                                        Log.d("RUNNABLE", "CAST:: " + cast.size());

                                        cast1[0] =cast.get(0);
                                        cast2[0] =cast.get(1);

                                    }


                                }
                            });

                            t.start(); // spawn thread

                            t.join();


                        } catch (InterruptedException e) {
                            Log.d("ERROR", "run: " + e.getLocalizedMessage());
                        }

                        MovieInfoSQL movieInfoSQL= new MovieInfoSQL(movie.getId(),movieInfoData[0].getBudget(),movieInfoData[0].getOriginal_language(),movieInfoData[0].getVote_average(),movieInfoData[0].getRelease_date(),movieInfoData[0].getRuntime(),movie.getOverview(),

                                cast1[0].getName(),cast1[0].getCharacter(),ImageProcessor.getImageFromUrl(cast1[0].getProfile_path())
                                ,cast2[0].getName(),cast2[0].getCharacter(),ImageProcessor.getImageFromUrl(cast2[0].getProfile_path())
                                ,movie.GetGenreIds().toString());

                        Log.d("movieInfoSQL", "movieInfoSQL:: " + movieInfoSQL.getOverview()+ " /n"+movieInfoSQL.getCharacter2()+"/n"+movieInfoSQL.getRelease_date() +" GENRES::"+movie.GetGenreIds().toString());

                        dao.AddMovieInfo(movieInfoSQL);
                    }
                    else{
                        Log.d("DB", "MOVIE_INFO_SQL IS NULL: ");

                    }
                    IsAdded=true;


                }
            });

        }


    }


    private void do_setSimilarMovies() {


        AndroidNetworking.get("https://api.themoviedb.org/3/movie/{MOVIE_ID}/similar?api_key=77da696d93b5f500c3f18a0aba2691f9&language=en-US&page=1")
                .addPathParameter("MOVIE_ID", movie.getIdString())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {


                        Gson gson = new Gson();
                        try {

                            JSONArray array = response.getJSONArray("results");
                            for (int i = 0; i < array.length(); i++) {

                                JSONObject myData = array.getJSONObject(i);
                                JsonObject element = gson.fromJson(myData.toString(), JsonObject.class);
                                Movie movie = gson.fromJson(element, Movie.class);

                                similarMovies.add(movie);
                            }
                            do_setAdapter();
                        } catch (JSONException error) {


                        }

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });


    }

    private void do_setAdapter() {
        recyclerView.setAdapter(new CardPosterAdapter(similarMovies));

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void do_setGenres() {


        Genres genres = new Genres();
        List<String> GenreNames = genres.getMoviesGenresNames(movie.GetGenreIds());


        for (int i = 0; i < GenreNames.size(); i++) {

            TextView text = new TextView(this);
            text.setText(GenreNames.get(i));
            text.setGravity(1);
            text.setPadding(10, 10, 10, 10);
            text.setBackgroundResource(R.drawable.shape_chip_drawable);

            GenresCrumbs.addView(text);


        }

    }

    private ArrayList<Movie> listArray() {


        ArrayList<Movie> objList = new ArrayList<Movie>();

        Movie movie = new Movie(1, "Test", "AddButton", "2", 1, "TEST DESCRIPTION");
        objList.add(movie);
        objList.add(movie);
        objList.add(movie);
        objList.add(movie);
        objList.add(movie);
        objList.add(movie);
        return objList;

    }


}
