package info.popularmovies.database;

import android.arch.lifecycle.LiveData;

public class MovieIdViewModel {//LiveData reference that holds the data freshly retrieved from the database
    //This data will be provided to the UI Controller once there is a change in the database
    //Everytime a viewmodel is rotated, a new
    private LiveData<DatabaseMovie> favouriteMovie;

    //Movie id
    private int mId;

    //TODO Dagger2 to be used for dependncy injection
    //Repository reference to fetch the data
    private MoviesIdRepository favouriteMoviesRepository;

    //A constructor that receives Database and movieId for which it wants to get the details for.
    public MovieIdViewModel(int movieId) {
        mId = movieId;
        favouriteMoviesRepository = new MoviesIdRepository();
    }

    //Reposiory initialization method
    public void init() {

        favouriteMovie = favouriteMoviesRepository.getFavouriteMovieById(mId);

    }

    //public getters and setters for the Favourite Movies
    public LiveData<DatabaseMovie> getFavouriteMovieById() {
        return favouriteMovie;
    }
}
