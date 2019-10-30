package com.example.moviejunkie.Helper;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.moviejunkie.Classes.Movie;
import com.example.moviejunkie.R;
import com.google.gson.JsonArray;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GridAdapter extends BaseAdapter {


    private Context mContext;
    private List<Movie> movieList;


    public GridAdapter(Context mContext, List<Movie> movieList) {
        this.mContext = mContext;
        this.movieList = movieList;
    }



    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String base="https://image.tmdb.org/t/p/w342";

        ImageView imageView = new ImageView(mContext);


       // Picasso.get().load(base+movieList.get(position).getPoster_path()).placeholder(R.drawable.load).fit().into(imageView);

        //imageView.setLayoutParams(new GridView.LayoutParams(440, 500));
        Picasso.get().load(base+movieList.get(position).getPoster_path()).placeholder(R.drawable.load).into(imageView);
        Log.d("PICASO", "Image loaded:: "+movieList.get(position).getPoster_path());

        // imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
       //imageView.setLayoutParams(new GridView.LayoutParams(440, 500));

        return imageView;
    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
