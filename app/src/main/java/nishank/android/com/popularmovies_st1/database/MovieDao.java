package nishank.android.com.popularmovies_st1.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import nishank.android.com.popularmovies_st1.model.Movie;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movies WHERE sortBy=:sortBy")
    List<Movie> getMovieList(String sortBy);

    @Query("SELECT * FROM movies WHERE mMovieId=:id")
    Movie getMovie(int id);

    @Query("SELECT * FROM movies WHERE mMovieIsFav=1 and mMovieId=:id")
    Movie getMovieFav(int id);

    @Query("UPDATE movies SET mMovieIsFav=1 WHERE mMovieId=:id")
    void setMovieFav(int id);

    @Query("UPDATE movies SET mMovieIsFav=0 WHERE mMovieId=:id")
    void setMovieUnFav(int id);

    @Query("SELECT * FROM movies WHERE mMovieIsFav=1")
    List<Movie> getMovieFavList();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addMovies(List<Movie> movieList);

    @Query("DELETE FROM movies WHERE sortBy=:sortBy")
    void deleteMovies(String sortBy);

}
