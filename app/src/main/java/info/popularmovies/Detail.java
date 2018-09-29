package info.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import info.popularmovies.adapter.ReviewsAdapter;
import info.popularmovies.adapter.TrailersAdapter;

public class Detail extends AppCompatActivity {
    private CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout appBarLayout;
    private TextView overView, releaseDate, voteRange;
    private ImageView posterPath;
    private List<Review> reviewsList;
    private List<Trailer> trailersList;
    private ReviewsAdapter rAdpter;
    private TrailersAdapter tAdpter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);
        overView = findViewById(R.id.over_view);
        releaseDate = findViewById(R.id.release_date);
        voteRange = findViewById(R.id.vote_average);
        posterPath = findViewById(R.id.poster_path);
        final ProgressBar progressBar = findViewById(R.id.progress);

        Intent i = getIntent();
        final String original_title = i.getStringExtra("original_title");
        final String over_view = i.getStringExtra("over_view");
        final String poster_path = i.getStringExtra("poster_path");
        final Double vote_average = i.getDoubleExtra("vote_average", 0.0);
        final String release_date = i.getStringExtra("release_date");

        overView.setText(over_view);
        releaseDate.setText(release_date);
        voteRange.setText(String.valueOf(vote_average));
        Glide.with(this)
                .load(poster_path)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target,
                                                   boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(posterPath);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        appBarLayout = findViewById(R.id.app_bar_layout);
        appBarLayout.setExpanded(true);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (scrollRange + verticalOffset > 0) {
                    collapsingToolbar.setTitle(original_title);
                    isShow = true;
                }
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    private void fetchReviewItem(String url) {
        // Showing progress bar before making http request
        //pb.setVisibility(ProgressBar.VISIBLE);
        // Creating volley request obj
        JsonObjectRequest reviewReq = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Message", response.toString());
                        //pb.setVisibility(ProgressBar.INVISIBLE);

                        // Parsing json
                        for (int j = 0; j < response.length(); j++) {
                            try {
                                JSONArray reviewArray = response.getJSONArray("results");
                                reviewsList.clear();
                                for (int i = 0; i < reviewArray.length(); i++) {
                                    JSONObject reviewObj = (JSONObject) reviewArray.get(i);

                                    Review reviews = new Review();

                                    reviews.setAuthor(reviewObj.getString("author"));
                                    reviews.setContent(reviewObj.getString("content"));
                                    reviewsList.add(reviews);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        rAdpter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error: " + error.getMessage());
                //pb.setVisibility(ProgressBar.INVISIBLE);

            }
        });

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(reviewReq);
    }

    private void fetchTrailerItem(String url) {
        // Showing progress bar before making http request
        //pb.setVisibility(ProgressBar.VISIBLE);
        // Creating volley request obj
        JsonObjectRequest trailerReq = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Message", response.toString());
                        //pb.setVisibility(ProgressBar.INVISIBLE);

                        // Parsing json
                        for (int j = 0; j < response.length(); j++) {
                            try {
                                JSONArray trailerArray = response.getJSONArray("results");
                                reviewsList.clear();
                                for (int i = 0; i < trailerArray.length(); i++) {
                                    JSONObject trailerObj = (JSONObject) trailerArray.get(i);

                                    Trailer trailer = new Trailer();

                                    trailer.setKey(trailerObj.getString("key"));
                                    trailer.setName(trailerObj.getString("name"));
                                    trailer.setType(trailerObj.getString("type"));
                                    trailersList.add(trailer);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        tAdpter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Error: " + error.getMessage());
                //pb.setVisibility(ProgressBar.INVISIBLE);

            }
        });

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(trailerReq);
    }

}


