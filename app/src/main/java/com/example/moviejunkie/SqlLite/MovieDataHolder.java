package com.example.moviejunkie.SqlLite;

import com.example.moviejunkie.SqlLite.MovieInfoSQL;
import com.example.moviejunkie.SqlLite.MovieSQL;

import java.util.ArrayList;
import java.util.Arrays;

public class MovieDataHolder {


    private int Status;
    private MovieSQL movieSQL;
    private MovieInfoSQL movieInfoSQL;
    private ArrayList<Integer> genre_ids;

    public MovieDataHolder() {

    }


    public MovieDataHolder(MovieSQL movieSQL, MovieInfoSQL movieInfoSQL) {
        this.movieSQL = movieSQL;
        this.movieInfoSQL = movieInfoSQL;
    }


    public void SetGenresIntegeres() {
        String[] strings = this.movieInfoSQL.getGenres().replace("[", "").replace("]", "").split(", ");
        Integer result[] = new Integer[strings.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = Integer.parseInt(strings[i]);
        }
        genre_ids = new ArrayList<>(Arrays.asList(result));
    }

    public boolean IsValidFilter(OfflineDataFilter offlineDataFilter) {

        boolean IsOK = true;

        if (offlineDataFilter.vote_min != null)
            if (movieInfoSQL.getVote_average() < offlineDataFilter.vote_min)
                IsOK = false;


        if (offlineDataFilter.vote_max != null)
            if (movieInfoSQL.getVote_average() > offlineDataFilter.vote_max)
                IsOK = false;

        if (offlineDataFilter.release_date_min != null) {
            String releaseDate = movieInfoSQL.getRelease_date();

            releaseDate = releaseDate.substring(0, 4);
            int release1 = Integer.parseInt(releaseDate);

            if (release1<Integer.parseInt(offlineDataFilter.release_date_min))
                IsOK = false;


        }

        if (offlineDataFilter.release_date_max != null) {
            String releaseDate = movieInfoSQL.getRelease_date();

            releaseDate = releaseDate.substring(0, 4);
            int release1 = Integer.parseInt(releaseDate);

            if (release1>Integer.parseInt(offlineDataFilter.release_date_max))
                IsOK = false;


        }

        if (offlineDataFilter.genre_ids.size()>0){

            boolean GenreTest=false;
            if (!genre_ids.contains(offlineDataFilter.genre_ids))
                for (int i = 0; i < offlineDataFilter.genre_ids.size(); i++) {

                    if (GenreTest)
                        break;

                    for (int j = 0; j <genre_ids.size() ; j++) {

                        if(offlineDataFilter.genre_ids.get(i)==genre_ids.get(j))
                            GenreTest=true;
                    }
                };

            if (!GenreTest)
                IsOK=false;
        }



        return IsOK;
    }


    public MovieSQL getMovieSQL() {
        return movieSQL;
    }
}
