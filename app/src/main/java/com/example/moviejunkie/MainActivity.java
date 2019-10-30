package com.example.moviejunkie;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;

import androidx.annotation.NonNull;

import com.example.moviejunkie.Dialogs.FullScreenSearchDialog;
import com.example.moviejunkie.Dialogs.OnlineSearchDialog;
import com.example.moviejunkie.Dialogs.OnlineSearchDialogInterface;
import com.example.moviejunkie.Helper.NetworkHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.moviejunkie.Classes.Movie;
import com.example.moviejunkie.Helper.GridAdapter;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements OnlineSearchDialogInterface {


    private int DiscoverPage = 1;
    private int myLastVisiblePos;

    private GridView Grid;
    private GridAdapter adapter;

    private BottomNavigationView navView;

    private Boolean IsOnline;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    Intent intent = new Intent(MainActivity.this, BookmarkActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_notifications:
                    OnlineSearchDialog dialog = new OnlineSearchDialog();
                    FragmentManager ft2 = getSupportFragmentManager();
                    dialog.show(ft2, dialog.TAG);
                    return true;
            }
            return false;
        }
    };


    private List<Movie> movies = new ArrayList<Movie>();
    final String BaseUrl = "https://api.themoviedb.org/3/discover/" +
            "movie?api_key=77da696d93b5f500c3f18a0aba2691f9&language=en-US&sort_by=popularity.desc&include_adult=false" +
            "&include_video=false&page=";
    final String url = "https://api.themoviedb.org/3/discover/" +
            "movie?api_key=77da696d93b5f500c3f18a0aba2691f9&language=en-US&sort_by=popularity.desc&include_adult=false" +
            "&include_video=false&page=1";
    private String customUrl = "";
    private Boolean DefaultSearch;

    @Override
    public void ResetNavbar() {
        navView.setSelectedItemId(R.id.navigation_home);
    }

    @Override
    public void onUrlCreation(String url) {
       //Toast.makeText(this, "RETURN STRING::" + url, Toast.LENGTH_SHORT).show();

        if (url != "RESET") {
            customUrl = url;
            DefaultSearch = false;
            DiscoverPage = 1;
            movies.clear();
            navView.setSelectedItemId(R.id.navigation_home);

            GetMoreMovies();
        } else {
            DefaultSearch = true;
            DiscoverPage = 1;
            movies.clear();
            navView.setSelectedItemId(R.id.navigation_home);
            GetMoreMovies();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DefaultSearch = true;
        IsOnline=false;

        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setSelectedItemId(R.id.navigation_home);

        Grid = findViewById(R.id.MovieGrid);
        myLastVisiblePos = Grid.getFirstVisiblePosition();

        adapter = new GridAdapter(this, movies);
        Grid.setAdapter(adapter);

        Grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String Title = movies.get(position).getTitle();
               // Toast.makeText(MainActivity.this, "Title selected: " + Title, Toast.LENGTH_SHORT).show();

//
                Intent intent = new Intent(MainActivity.this, MovieInfo.class);
                intent.putExtra("FILM", movies.get(position));
                startActivity(intent);

            }
        });
        Grid.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                GetMoreMovies();
                return true;
            }
        });


        try {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {

                    IsOnline=NetworkHelper.checkActiveInternetConnection(getApplicationContext());
                }
            });
            t.start();
            t.join();
            } catch (InterruptedException e) {
                Log.d("ERROR", "run: " + e.getLocalizedMessage());
            }

       // Toast.makeText(this, "ISonline::"+IsOnline.toString(), Toast.LENGTH_SHORT).show();

        if (IsOnline) {
            if (DefaultSearch) {
                AndroidNetworking.get(url)
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

                                        movies.add(movie);
                                    }
                                    ++DiscoverPage;
                                    for (int i = 0; i < movies.size(); i++) {

                                        Log.d("TEST2", "onResponse: " + movies.get(i).getTitle());
                                    }
                                    setMovie(movies);
                                } catch (JSONException error) {


                                }
                            }

                            @Override
                            public void onError(ANError error) {
                                // handle error
                            }
                        });

            } else {
                AndroidNetworking.get(customUrl)
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

                                        movies.add(movie);
                                    }
                                    ++DiscoverPage;
                                    for (int i = 0; i < movies.size(); i++) {

                                        Log.d("TEST2", "onResponse: " + movies.get(i).getTitle());
                                    }
                                    setMovie(movies);
                                } catch (JSONException error) {


                                }
                            }

                            @Override
                            public void onError(ANError error) {
                                // handle error
                            }
                        });
            }

        } else {

//            new AlertDialog.Builder(new ContextThemeWrapper(this,R.style.AlertDialogCustom))
//                    .setTitle("No connection")
//                    .setMessage("You have no access to the internet, you can try to load the page again or go to bookmarks")
//
//                    // Specifying a listener allows you to take an action before dismissing the dialog.
//                    // The dialog is automatically dismissed when a dialog button is clicked.
//                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            // Continue with delete operation
//                        }
//                    })
//
//                    // A null listener allows the button to dismiss the dialog and take no further action.
//                    .setNegativeButton(android.R.string.no, null)
//                    .setIcon(android.R.drawable.ic_dialog_alert)
//                    .show();

            new MaterialAlertDialogBuilder(this)
                    .setTitle("No internet")
                    .setMessage("You are not online. You may retry loading the page or go to offline bookmarks")
                    .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            recreate();
                        }
                    })
                    .setNegativeButton("Bookmarks", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(MainActivity.this, BookmarkActivity.class);
                            startActivity(intent);
                        }
                    })
                    .show();




        }


//        AppDatabase database= Room.databaseBuilder(this,AppDatabase.class,"MovieJunkie")
//                .allowMainThreadQueries()
//                .build();
//


    }

    private void isNetworkAvailable(MainActivity mainActivity) {
    }


    @Override
    protected void onResume() {
        super.onResume();

        navView.setSelectedItemId(R.id.navigation_home);

        //Toast.makeText(this, "RESUME CALLED", Toast.LENGTH_SHORT).show();

//        if (DefaultSearch) {
//            AndroidNetworking.get(url)
//                    .build()
//                    .getAsJSONObject(new JSONObjectRequestListener() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            Gson gson = new Gson();
//                            try {
//
//                                JSONArray array = response.getJSONArray("results");
//                                for (int i = 0; i < array.length(); i++) {
//
//                                    JSONObject myData = array.getJSONObject(i);
//                                    JsonObject element = gson.fromJson(myData.toString(), JsonObject.class);
//                                    Movie movie = gson.fromJson(element, Movie.class);
//
//                                    movies.add(movie);
//                                }
//                                ++DiscoverPage;
//                                for (int i = 0; i < movies.size(); i++) {
//
//                                    Log.d("TEST2", "onResponse: " + movies.get(i).getTitle());
//                                }
//                                setMovie(movies);
//                            } catch (JSONException error) {
//
//
//                            }
//                        }
//
//                        @Override
//                        public void onError(ANError error) {
//                            // handle error
//                        }
//                    });
//
//        }else{
//            AndroidNetworking.get(customUrl)
//                    .build()
//                    .getAsJSONObject(new JSONObjectRequestListener() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            Gson gson = new Gson();
//                            try {
//
//                                JSONArray array = response.getJSONArray("results");
//                                for (int i = 0; i < array.length(); i++) {
//
//                                    JSONObject myData = array.getJSONObject(i);
//                                    JsonObject element = gson.fromJson(myData.toString(), JsonObject.class);
//                                    Movie movie = gson.fromJson(element, Movie.class);
//
//                                    movies.add(movie);
//                                }
//                                ++DiscoverPage;
//                                for (int i = 0; i < movies.size(); i++) {
//
//                                    Log.d("TEST2", "onResponse: " + movies.get(i).getTitle());
//                                }
//                                setMovie(movies);
//                            } catch (JSONException error) {
//
//
//                            }
//                        }
//
//                        @Override
//                        public void onError(ANError error) {
//                            // handle error
//                        }
//                    });
//        }


    }


    private void setMovie(List<Movie> movies) {
        //Toast.makeText(this, "SETMOVIE CALLED", Toast.LENGTH_SHORT).show();
        adapter = new GridAdapter(this, movies);
        Grid.setAdapter(adapter);
    }

    private void GetMoreMovies() {

        if (DefaultSearch) {

            String defaultsearch = BaseUrl + DiscoverPage;

            AndroidNetworking.get(defaultsearch)
                    // .addPathParameter("pageNumber",String.valueOf(DiscoverPage))
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // do anything with response
                           // Toast.makeText(MainActivity.this, "API CALL PAGE DEFAULT :" + DiscoverPage, Toast.LENGTH_SHORT).show();
                            Gson gson = new Gson();
                            try {

                                JSONArray array = response.getJSONArray("results");
                                List<Movie> newMovies = new ArrayList<Movie>();
                                for (int i = 0; i < array.length(); i++) {

                                    JSONObject myData = array.getJSONObject(i);
                                    JsonObject element = gson.fromJson(myData.toString(), JsonObject.class);
                                    Movie movie = gson.fromJson(element, Movie.class);
                                    newMovies.add(movie);
                                }
                                ++DiscoverPage;
                                for (int i = 0; i < newMovies.size(); i++) {

                                    Log.d("TEST2", "onResponse: " + newMovies.get(i).getTitle());
                                }
                                do_UpgardeGrid(newMovies);
                            } catch (JSONException error) {


                            }
                        }

                        @Override
                        public void onError(ANError error) {
                            // handle error
                        }
                    });
        } else {

            final String customSearch2 = customUrl + "&page=" + DiscoverPage;

            AndroidNetworking.get(customSearch2)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // do anything with response
//                            Toast.makeText(MainActivity.this, "API CALL PAGE CUSTOM :" +DiscoverPage, Toast.LENGTH_SHORT).show();
//                            Toast.makeText(MainActivity.this, "URL:::" +customSearch2, Toast.LENGTH_LONG).show();
//                            Log.d("CUSTOM CALL", "URL::::: " +customSearch2);


                            Gson gson = new Gson();
                            try {

                                JSONArray array = response.getJSONArray("results");
                                //Toast.makeText(MainActivity.this, "ARRAY SIZE::"+array.length(), Toast.LENGTH_SHORT).show();
                                List<Movie> newMovies = new ArrayList<Movie>();
                                for (int i = 0; i < array.length(); i++) {

                                    JSONObject myData = array.getJSONObject(i);
                                    JsonObject element = gson.fromJson(myData.toString(), JsonObject.class);
                                    Movie movie = gson.fromJson(element, Movie.class);
                                    newMovies.add(movie);
                                }
                                ++DiscoverPage;
                                for (int i = 0; i < newMovies.size(); i++) {

                                    Log.d("CUSTOM CALL", "onResponse: " + newMovies.get(i).getTitle());
                                }
                                do_UpgardeGrid(newMovies);
                            } catch (JSONException error) {


                            }
                        }

                        @Override
                        public void onError(ANError error) {
                            // handle error
                        }
                    });
        }


    }

    private void do_UpgardeGrid(List<Movie> newMovies) {

        movies.addAll(newMovies);
        adapter.notifyDataSetChanged();


    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current scroll state
        savedInstanceState.putInt("STATE_POSITION", Grid.getFirstVisiblePosition());

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);

        // Restore state members from saved instance
        Grid.smoothScrollToPosition(savedInstanceState.getInt("STATE_POSITION"));
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Toast.makeText(this, "onCinfigurationCalled", Toast.LENGTH_SHORT).show();
        super.onConfigurationChanged(newConfig);
        int pos = Grid.getFirstVisiblePosition();
        //Toast.makeText(this, "FirstVisible:"+pos, Toast.LENGTH_SHORT).show();
        Grid.setSelection(pos);

        Grid.setNumColumns(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE ? 7 : 4);

    }


    /*
    NAPOMENA
    EndlessScrollListener nije moguce izvest u vlastitu klasu jer za skrivanje navigacisko bara bottom potreban
    je GridView i NavagationBar
    Grid view je potreban trenutnio prvog vildjivog pri svakom skroluu sto nije moguce uradidti ako se
    EndlessScrollListener izvuce u vlastitu klasu I ako mu se doda konstruktor sa gridView i bottomnav jer
    se postavljenje vrijednosti nije moguce updajetovat

    */
    public abstract class EndlessScrollListener implements AbsListView.OnScrollListener {
        // The minimum number of items to have below your current scroll position
        // before loading more.
        private int visibleThreshold = 5;
        // The current offset index of data you have loaded
        private int currentPage = 0;
        // The total number of items in the dataset after the last load
        private int previousTotalItemCount = 0;
        // True if we are still waiting for the last set of data to load.
        private boolean loading = true;
        // Sets the starting page index
        private int startingPageIndex = 0;


        public EndlessScrollListener() {
        }


        public EndlessScrollListener(int visibleThreshold) {
            this.visibleThreshold = visibleThreshold;
        }

        public EndlessScrollListener(int visibleThreshold, int startPage) {
            this.visibleThreshold = visibleThreshold;
            this.startingPageIndex = startPage;
            this.currentPage = startPage;
        }


        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            int currentFirstVisPos = view.getFirstVisiblePosition();

            if (currentFirstVisPos > myLastVisiblePos) {
                //scroll down
                //  Toast.makeText(MainActivity.this, "Scroll down", Toast.LENGTH_SHORT).show();

                if (navView.isShown())
                    navView.clearAnimation();
                navView.animate().translationY(navView.getHeight()).setDuration(200);
            }
            if (currentFirstVisPos < myLastVisiblePos) {
                //scroll up
                //navView.setVisibility(View.VISIBLE);
                navView.clearAnimation();
                navView.animate().translationY(0).setDuration(200);

                // Toast.makeText(MainActivity.this, "scroll up ", Toast.LENGTH_SHORT).show();
            }
            myLastVisiblePos = currentFirstVisPos;

            // If the total item count is zero and the previous isn't, assume the
            // list is invalidated and should be reset back to initial state
            if (totalItemCount < previousTotalItemCount) {
                this.currentPage = this.startingPageIndex;
                this.previousTotalItemCount = totalItemCount;
                if (totalItemCount == 0) {
                    this.loading = true;
                }

            }
            // If it's still loading, we check to see if the dataset count has
            // changed, if so we conclude it has finished loading and update the current page
            // number and total item count.
            if (loading && (totalItemCount > previousTotalItemCount)) {
                loading = false;
                previousTotalItemCount = totalItemCount;

            }

            // If it isn't currently loading, we check to see if we have breached
            // the visibleThreshold and need to reload more data.
            // If we do need to reload some more data, we execute onLoadMore to fetch the data.
            if (!loading && (firstVisibleItem + visibleItemCount + visibleThreshold) >= totalItemCount) {
                loading = onLoadMore(currentPage + 1, totalItemCount);

            }
        }

        // Defines the process for actually loading more data based on page
        // Returns true if more data is being loaded; returns false if there is no more data to load.

        public boolean onLoadMore(int page, int totalItemsCount) {
            GetMoreMovies();
            return true; // ONLY if more data is actually being loaded; false otherwise.
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            // Don't take any action on changed
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        System.exit(0);
    }
}
