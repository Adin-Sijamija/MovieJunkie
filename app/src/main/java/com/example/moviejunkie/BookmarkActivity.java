package com.example.moviejunkie;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.moviejunkie.Dialogs.FullScreenSearchDialog;
import com.example.moviejunkie.Dialogs.OfflineBookmarkSearchDialog;
import com.example.moviejunkie.Dialogs.OfflineBookmarkSearchDialogInterface;
import com.example.moviejunkie.Helper.CardPosterAdapter;
import com.example.moviejunkie.Helper.NetworkHelper;
import com.example.moviejunkie.Dialogs.SearchDialogInterface;
import com.example.moviejunkie.SqlLite.AppDatabase;
import com.example.moviejunkie.SqlLite.MovieDAO;
import com.example.moviejunkie.SqlLite.MovieDataHolder;
import com.example.moviejunkie.SqlLite.MovieSQL;
import com.example.moviejunkie.SqlLite.OfflineDataFilter;
import com.example.moviejunkie.SqlLite.UserSQL;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class BookmarkActivity extends AppCompatActivity implements OfflineBookmarkSearchDialogInterface {


    private RecyclerView CurentlyWatching;
    private RecyclerView PlannedWatching;
    private RecyclerView WatchedMovies;

    private ArrayList<MovieSQL> WatchingList;
    private ArrayList<MovieSQL> PlanedList;
    private ArrayList<MovieSQL> WatchedList;

    private List<MovieDataHolder> WatchingHolderList=new ArrayList<>();
    private List<MovieDataHolder> PlanedHolderList=new ArrayList<>();
    private List<MovieDataHolder> WatchedHolderList=new ArrayList<>();


    private CardPosterAdapter Watching;
    private CardPosterAdapter Planed;
    private CardPosterAdapter Watched;


    Boolean IsOnline=true;


    private Button Db_clear;
    //MivieBookmark activity
    //Search

    @Override
    public void ResetNavbar() {
        navView.setSelectedItemId(R.id.navigation_dashboard);
    }

    @Override
    public void onBookmarkFilter(OfflineDataFilter offlineDataFilter) {
        navView.setSelectedItemId(R.id.navigation_dashboard);

        //Toast.makeText(this, "ONBOOKMARK FILTER", Toast.LENGTH_LONG).show();
        Log.d("BOOKMARK", "onBookmarkFilter: is called");
        Log.d("BOOKMARK", "onBookmarkFilter: ofline data is reset::"+offlineDataFilter.IsReset());
        if (offlineDataFilter.IsReset()){
            do_getDbData();
        }else {


            ArrayList<MovieSQL>  WatchingFilterlist=new ArrayList<>();
            ArrayList<MovieSQL> PlanedFilterlist=new ArrayList<>();
            ArrayList<MovieSQL> WatchedFilterlist=new ArrayList<>();

            for (int i = 0; i < WatchingHolderList.size(); i++) {

                if(WatchingHolderList.get(i).IsValidFilter(offlineDataFilter)){
                    WatchingFilterlist.add(WatchingHolderList.get(i).getMovieSQL());
                }

            }
            for (int i = 0; i < PlanedHolderList.size(); i++) {

                if(PlanedHolderList.get(i).IsValidFilter(offlineDataFilter)){
                    PlanedFilterlist.add(PlanedHolderList.get(i).getMovieSQL());

                }

            }

            for (int i = 0; i < WatchedHolderList.size(); i++) {

                if(WatchedHolderList.get(i).IsValidFilter(offlineDataFilter)){
                    WatchedFilterlist.add(WatchedHolderList.get(i).getMovieSQL());
                }

            }

            Watching = new CardPosterAdapter(WatchingFilterlist, true);
            Planed = new CardPosterAdapter(PlanedFilterlist, true);
            Watched = new CardPosterAdapter(WatchedFilterlist, true);

            CurentlyWatching.setAdapter(Watching);
            PlannedWatching.setAdapter(Planed);
            WatchedMovies.setAdapter(Watched);

        }







    }



    private BottomNavigationView navView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

//                     Intent intent= new Intent(BookmarkActivity.this,MainActivity.class);
//                     startActivity(intent);

                    try {
                        Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                IsOnline= NetworkHelper.checkActiveInternetConnection(getApplicationContext());
                            }
                        });
                        t.start();
                        t.join();
                    } catch (InterruptedException e) {
                        Log.d("ERROR", "run: " + e.getLocalizedMessage());
                    }

                    if(IsOnline)
                        finish();

                    Intent intent = new Intent(BookmarkActivity.this, MainActivity.class);
                    startActivity(intent);

                    return true;
                case R.id.navigation_dashboard:
//                    mTextMessage.setText(R.string.title_dashboard);

                    // Intent intent= new Intent(MainActivity.this,BookmarkActivity.class);
                    // startActivity(intent);

                    return true;
                case R.id.navigation_notifications:
//                    mTextMessage.setText(R.string.title_notifications);
                   // Toast.makeText(BookmarkActivity.this, "notification", Toast.LENGTH_SHORT).show();
                    OfflineBookmarkSearchDialog dialog = new OfflineBookmarkSearchDialog();
                    FragmentManager ft2 = getSupportFragmentManager();
                    dialog.show(ft2, dialog.TAG);
                    return true;
            }
            return false;
        }
    };

    private ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

//
//        Db_clear=findViewById(R.id.DB_CLearButton);
//        Db_clear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick( final View v) {
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
//
//                        dao.EmptyUserTable();
//                    }
//                });
//            }
//        });

        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navView.setSelectedItemId(R.id.navigation_dashboard);

        scrollView = findViewById(R.id.BookmarkScroll);
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {

                int scrollY = scrollView.getScrollY();

                if (scrollY < 50 && navView.isShown()) {

                    navView.clearAnimation();
                    navView.animate().translationY(0).setDuration(200);
                } else {


                    navView.clearAnimation();
                    navView.animate().translationY(navView.getHeight()).setDuration(200);
                }

                Log.d("Scroll", "onScrollChanged: position::" + scrollY);
            }
        });


        CurentlyWatching = findViewById(R.id.WatchingMoviesID);
        CurentlyWatching.setHasFixedSize(true);

        PlannedWatching = findViewById(R.id.PlanedMoviesID);
        PlannedWatching.setHasFixedSize(true);

        WatchedMovies = findViewById(R.id.WatchedMoviesID);
        WatchedMovies.setHasFixedSize(true);


        LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(this);
        mLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        CurentlyWatching.setLayoutManager(mLayoutManager1);


        LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(this);
        mLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        PlannedWatching.setLayoutManager(mLayoutManager2);

        LinearLayoutManager mLayoutManager3 = new LinearLayoutManager(this);
        mLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        WatchedMovies.setLayoutManager(mLayoutManager3);


        WatchedList = new ArrayList<>();

        PlanedList = new ArrayList<>();
        //   PlanedList= getTestData();

        WatchedList = new ArrayList<>();
        // WatchedList=getTestData();


        Watching = new CardPosterAdapter(WatchingList, true);
        Planed = new CardPosterAdapter(PlanedList, true);
        Watched = new CardPosterAdapter(WatchedList, true);

        CurentlyWatching.setAdapter(Watching);
        PlannedWatching.setAdapter(Planed);
        WatchedMovies.setAdapter(Watched);

        Watched.notifyDataSetChanged();
        Watching.notifyDataSetChanged();
        Planed.notifyDataSetChanged();
        do_getDbData();

    }


    @Nullable
    @Override
    public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {

        return super.onCreateView(parent, name, context, attrs);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //Toast.makeText(this, "OnActivitResult::result:::"+resultCode+":: request:: "+ requestCode, Toast.LENGTH_SHORT).show();
        Log.d("DBK", "onActivityResult: requestcode ......"+requestCode+ ".........resultcode"+resultCode);
        if (requestCode==1){

            if (resultCode == Activity.RESULT_OK) {
                Log.d("DBK", "Result RESULT_OK ");
                do_getDbData();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Log.d("DBK", "Result RESULT_CANCELED ");
                do_getDbData();
            }
        }
    }

    private void do_getDbData() {
//        DB_Task= new GetDBDataAsyncTask();
//        DB_Task.execute();
        new GetDBDataAsyncTask(1).execute();
        new GetDBDataAsyncTask(2).execute();
        new GetDBDataAsyncTask(3).execute();

        new GetDataHolderAsyncTask(1).execute();
        new GetDataHolderAsyncTask(2).execute();
        new GetDataHolderAsyncTask(3).execute();
    }




    private final class GetDBDataAsyncTask extends AsyncTask<Void, Void, List<MovieSQL>> {

        private int status;

         GetDBDataAsyncTask(int status) {
            this.status = status;
        }

        @Override
        protected List<MovieSQL> doInBackground(Void... Status) {

            AppDatabase appDatabase = AppDatabase.getInstance(getApplicationContext());
            MovieDAO dao = appDatabase.getMovieDAO();
            UserSQL userSQL=dao.GetCurrentUser();

            List<MovieSQL> movies=new ArrayList<>();

            switch (status){
                case 1:
                    movies = dao.GetAllWatching(userSQL.getId());
                    break;
                case 2:
                    movies = dao.GetAllPlanned(userSQL.getId());
                    break;
                case 3:
                    movies = dao.GetAllWatched(userSQL.getId());
                    break;
            }

            return movies;
        }

        @Override
        protected void onPostExecute(List<MovieSQL> movies) {
            Log.d("DB", "onPostExecute: loaded movies " + movies.size());

            ArrayList<MovieSQL> db = new ArrayList<>(movies);

            switch (status){
                case 1:
                    WatchingList = db;
                    Watching = new CardPosterAdapter(WatchingList, false);
                    Watching.notifyDataSetChanged();
                    CurentlyWatching.setAdapter(Watching);
                    break;
                case 2:
                    PlanedList = db;
                    Planed = new CardPosterAdapter(PlanedList, false);
                    Planed.notifyDataSetChanged();
                    PlannedWatching.setAdapter(Planed);
                    break;
                case 3:
                    WatchedList = db;
                    Watched = new CardPosterAdapter(WatchedList, false);
                    Watched.notifyDataSetChanged();
                    WatchedMovies.setAdapter(Watched);
                    break;
            }

            Log.d("DB", "onPostExecute: wathcing list " + WatchingList.size());

        }


    }



    private final class GetDataHolderAsyncTask extends AsyncTask<Void, Void, List<MovieDataHolder>> {

        private int status;

        GetDataHolderAsyncTask(int status) {
            this.status = status;
        }

        @Override
        protected  List<MovieDataHolder> doInBackground(Void... Status) {

            AppDatabase appDatabase = AppDatabase.getInstance(getApplicationContext());
            MovieDAO dao = appDatabase.getMovieDAO();

            List<MovieSQL> movies;

            List<MovieDataHolder> holders=new ArrayList<>();
            UserSQL userSQL=dao.GetCurrentUser();


            switch (status){
                case 1:
                    movies = dao.GetAllWatching(userSQL.getId());
                    for (int i = 0; i <movies.size(); i++) {
                        MovieDataHolder holder= new MovieDataHolder(movies.get(i),dao.GetMovieInfo(movies.get(i).getMovie_id()));
                        holder.SetGenresIntegeres();
                        holders.add(holder);
                    }
                    break;
                case 2:
                    movies = dao.GetAllPlanned(userSQL.getId());
                    for (int i = 0; i <movies.size(); i++) {
                        MovieDataHolder holder= new MovieDataHolder(movies.get(i),dao.GetMovieInfo(movies.get(i).getMovie_id()));
                        holder.SetGenresIntegeres();
                        holders.add(holder);
                    }
                    break;
                case 3:
                    movies = dao.GetAllWatched(userSQL.getId());
                    for (int i = 0; i <movies.size(); i++) { dao.GetMovieInfo(movies.get(i).getMovie_id());
                       MovieDataHolder holder= new MovieDataHolder(movies.get(i),dao.GetMovieInfo(movies.get(i).getMovie_id()));
                       holder.SetGenresIntegeres();
                       holders.add(holder);
                    }
                    break;
            }
            return holders;

        }

        @Override
        protected void onPostExecute( List<MovieDataHolder> holders) {

            switch (status){
                case 1:
                    WatchingHolderList=holders;
                    Log.d("HOLDER", "Wathcing holder size::"+holders.size());
                    break;
                case 2:
                    PlanedHolderList=holders;
                    Log.d("HOLDER", "Planned holder size::"+holders.size());
                    break;
                case 3:
                    WatchedHolderList=holders;
                    Log.d("HOLDER", "Watched holder size::"+holders.size());
                    break;
            }

        }


    }







}
