package info.popularmovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface MovieDao {
    @Query("Select * from Movie")
    LiveData<List<Movie>> getAllMovie();

    @Delete
    void deleteMovie(Movie movie);

    @Insert(onConflict = REPLACE)
    void insertNewMovie(Movie movie);

    @Query("Select * from Movie where movie_id = :movieId")
    Movie getMovieWithId(String movieId);
}
