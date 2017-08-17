package com.example.david.popmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.example.david.popmovies.model.*;



public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder>{


    public Movie movies[];
    private  Context context;

    private final MovieAdapterOnClickHandler mClickHandler;

    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movieDetail);
    }

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }


    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;



        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
       // view.setLayoutParams(new LinearLayout.LayoutParams(RecyclerView.LayoutParams.FILL_PARENT, MainActivity.default_height/2));

        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        String movie = movies[position].getImageURL();
        if(context!=null)
        Picasso.with(context).load(movie).fit().into(holder.movieImage);
    }

    @Override
    public int getItemCount() {
        if (null == movies) return 0;
        return movies.length;
    }
    public void setMoviesData(Movie[] movies) {


        this.movies = movies;
        notifyDataSetChanged();
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView movieImage;

        public MovieAdapterViewHolder(View view) {
            super(view);
            movieImage = (ImageView) view.findViewById(R.id.movie_image);
            view.setOnClickListener(this);
        }

        /**
         * This gets called by the child views during a click.
         *
         * @param v The View that was clicked
         */
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(movies[adapterPosition]);
        }
    }






}
