package info.popularmovies.database;

import android.arch.lifecycle.LiveData;
import android.content.Context;

public class MoviesIdRepository {

    //Database reference for various data collection
    private static MovieRoomDatabase databaseService;


    //Repository initialization
    public void init(Context context) {
        databaseService = MovieRoomDatabase.getDatabase(context);
    }


    public LiveData<DatabaseMovie> getFavouriteMovieById(int id) {
        return databaseService.movieDao().getMovieById(id);
    }
}
