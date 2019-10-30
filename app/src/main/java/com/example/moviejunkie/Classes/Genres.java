package com.example.moviejunkie.Classes;

import java.util.ArrayList;
import java.util.List;

public class Genres  {

private List<GenreHolder> AllGenres = new ArrayList<GenreHolder>();

    public Genres() {
       setGenres();
    }

    private void setGenres() {
        AllGenres.add(new GenreHolder("Action",28));
        AllGenres.add(new GenreHolder("Adventure",12));
        AllGenres.add(new GenreHolder("Comedy",35));
        AllGenres.add(new GenreHolder("Animation",16));
        AllGenres.add(new GenreHolder("Crime",80));
        AllGenres.add(new GenreHolder("Documentary",99));
        AllGenres.add(new GenreHolder("Drama",18));
        AllGenres.add(new GenreHolder("Family",10751));
        AllGenres.add(new GenreHolder("Fantasy",14));
        AllGenres.add(new GenreHolder("History",36));
        AllGenres.add(new GenreHolder("Horor",27));
        AllGenres.add(new GenreHolder("Music",10402));
        AllGenres.add(new GenreHolder("Mystery",9648));
        AllGenres.add(new GenreHolder("Romance",10749));
        AllGenres.add(new GenreHolder("Sci-Fi",878));
        AllGenres.add(new GenreHolder("Thriller",53));
        AllGenres.add(new GenreHolder("War",10752));
        AllGenres.add(new GenreHolder("Western",37));


    }



    public List<String> getMoviesGenresNames(List<Integer> genreIDs) {

        List<String> moviesGenreNames=new ArrayList<String>();


        for (int i = 0; i < genreIDs.size(); i++) {


            for (int j = 0; j <AllGenres.size(); j++) {

                int id=genreIDs.get(i);
                int AllID=AllGenres.get(j).GenreNumber;
                if (id==AllID)
                    moviesGenreNames.add(AllGenres.get(j).GenreName);
            }

        }



        return  moviesGenreNames;

    }


    private class  GenreHolder{

        private String GenreName;
        private Integer GenreNumber;

        public String getGenreName() {
            return GenreName;
        }

        public Integer getGenreNumber() {
            return GenreNumber;
        }

        public GenreHolder(String genreName, Integer genreNumber) {
            GenreName = genreName;
            GenreNumber = genreNumber;
        }
    }

}
