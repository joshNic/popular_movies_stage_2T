package info.popularmovies.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Movie.class}, version = 1)
public abstract class MovieDatabase extends RoomDatabase {
    private static MovieDatabase movieDatabase;
    private Context context;

    public static MovieDatabase getInstance(Context context) {
        if (movieDatabase == null) {
            movieDatabase = Room.databaseBuilder(context.getApplicationContext(), MovieDatabase.class, "Movie-database")
                    .allowMainThreadQueries()
                    .build();
        }
        return movieDatabase;
    }

    public static void destroyInstance() {
        movieDatabase = null;
    }

    public abstract MovieDao movie();
}
