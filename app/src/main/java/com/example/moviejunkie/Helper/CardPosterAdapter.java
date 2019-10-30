package com.example.moviejunkie.Helper;


import android.app.Activity;
import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;
import androidx.room.util.DBUtil;

import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.moviejunkie.BookmarkActivity;
import com.example.moviejunkie.Classes.Movie;
import com.example.moviejunkie.MovieBookmark;
import com.example.moviejunkie.MovieInfo;
import com.example.moviejunkie.R;
import com.example.moviejunkie.SqlLite.MovieSQL;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static androidx.core.app.ActivityCompat.startActivityForResult;

public class CardPosterAdapter extends RecyclerView.Adapter<CardPosterAdapter.MyViewHolder> {

    ArrayList<Movie> listMovie;
    ArrayList<MovieSQL> DbMovies;
    Boolean IsDbAdapter;



    public CardPosterAdapter(ArrayList<Movie> _listMovie) {
        this.listMovie = _listMovie;
        IsDbAdapter = false;
    }

    public CardPosterAdapter(ArrayList<MovieSQL> DB_MOVIES, Boolean dbCall) {

        if (DB_MOVIES == null) {
            DbMovies = new ArrayList<>();
        } else {
            this.DbMovies = DB_MOVIES;
        }

        IsDbAdapter = true;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardinterface, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


//        holder.txtRestaurantName.setText("TEST");
//        holder.txtDesc.setText("test");
        // holder.imgRestaurant.setImageResource();


        if (IsDbAdapter) {

           // Picasso.get().load("https://image.tmdb.org/t/p/w342" + DbMovies.get(position).getPoster_path()).placeholder(R.drawable.load).into(holder.imgRestaurant);

            holder.imgRestaurant.setImageBitmap(BitmapFactory.decodeByteArray(DbMovies.get(position).getPoster(),0,DbMovies.get(position).getPoster().length)) ;
            holder.imgRestaurant.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), MovieBookmark.class);
                    intent.putExtra("FILM_BOOKMARK_ID", DbMovies.get(position).getMovie_id());
                    Log.d("DATA", "PUT EXTRA ID: "+DbMovies.get(position).getId());
                    //v.getContext().startActivity(intent);

                    ((Activity) v.getContext()).startActivityForResult(intent, 1);
                }
            });

        } else {

            Picasso.get().load("https://image.tmdb.org/t/p/w342" + listMovie.get(position).getPoster_path()).placeholder(R.drawable.load).into(holder.imgRestaurant);

            holder.imgRestaurant.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), MovieInfo.class);
                    intent.putExtra("FILM", listMovie.get(position));
                    v.getContext().startActivity(intent);

                }
            });
        }

    }



    @Override
    public int getItemCount() {

        if (IsDbAdapter){
            return DbMovies.size();
        }else {
            return listMovie.size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        //TextView txtRestaurantName, txtDesc;
        ImageView imgRestaurant;

        public MyViewHolder(View view) {
            super(view);
//            txtRestaurantName = (TextView) view.findViewById(R.id.txtRestaurantName);
//            txtDesc = (TextView) view.findViewById(R.id.txtDesc);
            imgRestaurant = (ImageView) view.findViewById(R.id.SimilarMoviePoster);

        }
    }
}