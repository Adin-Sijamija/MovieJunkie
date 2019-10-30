package com.example.moviejunkie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviejunkie.Helper.TabAdapter;
import com.example.moviejunkie.Dialogs.bottomSheetDialigInterface;
import com.example.moviejunkie.SqlLite.AppDatabase;
import com.example.moviejunkie.SqlLite.MovieDAO;
import com.example.moviejunkie.SqlLite.MovieInfoSQL;
import com.example.moviejunkie.SqlLite.MovieSQL;
import com.example.moviejunkie.SqlLite.UserSQL;
import com.example.moviejunkie.TabFragments.Tab1Fragment;
import com.example.moviejunkie.TabFragments.Tab2Fragment;
import com.example.moviejunkie.TabFragments.Tab3Fragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

public class MovieBookmark extends AppCompatActivity implements bottomSheetDialigInterface {

    private MovieSQL movie;
    private MovieInfoSQL movieinfo;


    private ImageView Poster;
    private TextView Title;
    private TextView Status;


    private RecyclerView recyclerView;
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    private TextView StatusMessage;
    private Button StatusButton;


    private int MovieStatus;
    private Boolean MovieStatusChanged =false;

    @Override
    public void ChangeStatus(final Integer status) {


        if (MovieStatus != status) {

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    AppDatabase appDatabase = AppDatabase.getInstance(getApplicationContext());
                    MovieDAO dao = appDatabase.getMovieDAO();

                    movie.setStatus(status);
                    dao.UpdateMovie(movie);
                    MovieStatus = status;
                    MovieStatusChanged =true;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            setStatusTextView(status);
                            Snackbar.make(findViewById(android.R.id.content), getString(R.string.MovieStatusChanged), Snackbar.LENGTH_LONG)
                                    .setBackgroundTint(getResources().getColor(R.color.colorGrey3))
                                    .setTextColor(getResources().getColor(R.color.colorOnBackground))
                                    .show();

                        }
                    });
                }


            });
        }else{
            MovieStatusChanged=false;
        }

    }


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_bookmark);

        Poster = findViewById(R.id.MovieBookmarkPoster);
        Title = findViewById(R.id.MovieBookmarkTitle);

        StatusButton = findViewById(R.id.ModalButton);
        StatusMessage = findViewById(R.id.StatusMessage);


        try {

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {

                    AppDatabase appDatabase = AppDatabase.getInstance(getApplicationContext());
                    MovieDAO dao = appDatabase.getMovieDAO();

                    int n = getIntent().getIntExtra("FILM_BOOKMARK_ID", -1);
                    UserSQL userSQL=dao.GetCurrentUser();


                    movie = dao.GetMovie(n,userSQL.getId());
                    movieinfo = dao.GetMovieInfo(n);

                    Log.d("DATA", "movie test: " + movie.getTitle());
                    Log.d("DATA", "movie info test: " + movieinfo.getBudget());
                }
            });

            t.start();
            t.join();

        } catch (InterruptedException e) {
            Log.d("Error", "onCreate: " + e.getLocalizedMessage());
        }

        StatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BottomSheetFragment fragment = new BottomSheetFragment();
                Bundle args = new Bundle();
                args.putInt("STATUS", movie.getStatus());
                fragment.setArguments(args);
                fragment.show(getSupportFragmentManager(), "TAG");
            }
        });


//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                Poster.setImageBitmap(BitmapFactory.decodeByteArray(movie.getPoster(), 0, movie.getPoster().length));
//                Poster.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            }
//        });
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Poster.setImageBitmap(BitmapFactory.decodeByteArray(movie.getPoster(), 0, movie.getPoster().length));
                Poster.setScaleType(ImageView.ScaleType.CENTER_CROP);
                // Stuff that updates the UI

            }
        });

        Title.setText(movie.getTitle());

        viewPager = findViewById(R.id.TabsBookmarkID);
        tabLayout = findViewById(R.id.TabBookmarkLayoutId);
        adapter = new TabAdapter(getSupportFragmentManager());


        Tab1Fragment tab1 = new Tab1Fragment();
        Bundle tab1b = new Bundle();
        tab1b.putString("MOVIE_OVERVIEW", movieinfo.getOverview());
        tab1.setArguments(tab1b);
        adapter.addFragment(tab1, "DESCRIPTION");


        Tab2Fragment tab2 = new Tab2Fragment();
        Bundle tab2b = new Bundle();
        tab2b.putInt("MOVIE_ID", movie.getId());
        tab2b.putSerializable("MOVIE_DATA", movieinfo);
        tab2.setArguments(tab2b);
        adapter.addFragment(tab2, "INFO");


        Tab3Fragment tab3 = new Tab3Fragment();
        Bundle tab3b = new Bundle();
        tab3b.putInt("MOVIE_ID", movie.getId());
        tab3b.putSerializable("MOVIE_DATA", movieinfo);
        tab3.setArguments(tab3b);
        adapter.addFragment(tab3, "CAST");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        setStatusTextView(movie.getStatus());

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent returnIntent = new Intent();

        Log.d("DBK", "onBackPressed: movie status "+MovieStatusChanged.toString());
        if (MovieStatusChanged){
            setResult(RESULT_OK,returnIntent);
            finish();
        }else{
            setResult(RESULT_CANCELED,returnIntent);
            finish();
        }

    }

    private void setStatusTextView(Integer status) {

        switch (status) {
            case 1:
                StatusMessage.setText(getString(R.string.StatusMessageWatching));
                break;
            case 2:
                StatusMessage.setText(getString(R.string.MovieStatusPlaned));
                break;
            case 3:
                StatusMessage.setText(getString(R.string.StatusMessageWatched));
                break;
        }

    }
}
