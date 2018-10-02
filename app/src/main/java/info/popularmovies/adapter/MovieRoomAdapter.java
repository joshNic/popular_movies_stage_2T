package info.popularmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import info.popularmovies.Detail;
import info.popularmovies.R;
import info.popularmovies.database.Movie;

public class MovieRoomAdapter extends RecyclerView.Adapter<MovieRoomAdapter.RoomViewHolder> {
    private Context context;
    private List<Movie> movieList;

    public MovieRoomAdapter(Context context, List<Movie> movieList) {
        this.movieList = movieList;
        this.context = context;
    }

    @Override
    public MovieRoomAdapter.RoomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_card, parent, false);
        return new MovieRoomAdapter.RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RoomViewHolder holder, int position) {
        final Movie movie = movieList.get(position);
        final String title = movie.getMovie_title();
        final String poster = "https://image.tmdb.org/t/p/w500" + movie.getPoster_path();
        final String Oview = movie.getOver_view();
        final String date = movie.getRelease_date();
        final Double range = movie.getVote_average();
        final int movie_id = movie.getMovie_id();
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //listener.onCardSelected(position, holder.thumbnail);
                Intent intent = new Intent(view.getContext(), Detail.class);
                intent.putExtra("original_title", title);
                intent.putExtra("poster_path", poster);
                intent.putExtra("release_date", date);
                intent.putExtra("vote_average", range);
                intent.putExtra("over_view", Oview);
                intent.putExtra("movie_id", movie_id);
                view.getContext().startActivity(intent);
                //Toast.makeText(getActivity(), "Image still downloading.....", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder {
        public TextView movieTitle, movieDate;
        public CardView cardView;

        public RoomViewHolder(View view) {
            super(view);
            cardView = view.findViewById(R.id.card_view);
            movieTitle = view.findViewById(R.id.movie_title);
            movieDate = view.findViewById(R.id.movie_date);


        }
    }
}
