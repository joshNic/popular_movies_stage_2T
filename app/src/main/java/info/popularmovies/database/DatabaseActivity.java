package info.popularmovies.database;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import info.popularmovies.R;
import info.popularmovies.adapter.MovieRoomAdapter;

public class DatabaseActivity extends AppCompatActivity {
    private final String TAG = DatabaseActivity.class.getSimpleName();
    private MovieRoomAdapter movieRoomAdapter;

    private AppBarLayout appBarLayout;
    private List<Movie> movieList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_database);
        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DatabaseActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        MovieDao movieDao = MovieDatabase.getInstance(getApplicationContext()).movie();
        movieList = new ArrayList<>();
        movieRoomAdapter = new MovieRoomAdapter(this, movieList);
        movieDao.getAllMovie().observe(this, (List<Movie> movie) -> {
            movieRoomAdapter = new MovieRoomAdapter(DatabaseActivity.this, movie);
            recyclerView.setAdapter(movieRoomAdapter);
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        appBarLayout = findViewById(R.id.app_bar_layout);
        appBarLayout.setExpanded(false);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}