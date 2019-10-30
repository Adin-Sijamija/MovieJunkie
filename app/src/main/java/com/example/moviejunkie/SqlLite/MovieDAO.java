package com.example.moviejunkie.SqlLite;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

@Dao
public interface MovieDAO {
    @Query("Select * from MOVIESQL")
    List<MovieSQL> getAllMovies();

    @Insert void AddMovie(MovieSQL movieSQL);

    @Update void  UpdateMovie(MovieSQL movieSQL);

    @Delete void  RemoveMovie(MovieSQL movieSQL);



    @Query("DELETE from MovieSQL where movie_id=:movieID and user_id=:userid")
    void RemoveMovie(int movieID,int userid);

    @Query("SELECT * from MovieSQL where movie_id=:movieID and user_id=:userid")
    MovieSQL GetMovie(int movieID,int userid);


    @Query("Select * from MovieSQL where status=3  and user_id=:user_id")
    List<MovieSQL> GetAllWatched(int user_id);

    @Query("Select * from MovieSQL where status=2  and user_id=:user_id")
    List<MovieSQL> GetAllPlanned(int user_id);

    @Query("Select * from MovieSQL where status=1 and user_id=:user_id")
    List<MovieSQL> GetAllWatching(int user_id);

    @Query("DELETE FROM MovieSQL")
     void EmptyMovieTable();

    @Query("DELETE FROM MovieInfoSQL")
    void EmptyMovieInfoTable();

    @Query("DELETE FROM UserSQL")
    void EmptyUserTable();

    @Query("Select * from MovieInfoSQL where id=:ID LIMIT 1")
    MovieInfoSQL GetMovieInfo(int ID);

    @Query("Delete from MovieInfoSQL where id=:ID")
    void DeleteMovieInfo(int ID);

    @Insert void AddMovieInfo(MovieInfoSQL info);


    @Query("SELECT * FROM MovieSQL where movie_id=:id")
    List<MovieSQL> getAllMovieByID(int id);

    @Query("Update UserSQL set logged_in=0")
    void ResetAllLoggedUsers();

    @Query("Select * from UserSQL where user_name=:Username LIMIT 1")
    UserSQL CheckIfNameTaken(String Username);

    @Query("Update UserSQL set logged_in=1 where user_name=:Username AND user_password=:Password")
    int  LogInUser(String Username,String Password);

    @Query("SELECT * FROM USERSQL where logged_in=1 LIMIT 1")
    UserSQL GetCurrentUser();

    @Insert long RegisterUser(UserSQL userSQL);

}
