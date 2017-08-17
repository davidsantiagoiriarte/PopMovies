package com.example.david.popmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.david.popmovies.model.Movie;
import com.example.david.popmovies.util.Util;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private Movie movie;

    TextView title;
    TextView plot;
    TextView rating;
    RatingBar ratingBar;
    TextView release;
    ImageView poster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        movie= (Movie) getIntent().getSerializableExtra("movie");

        title = (TextView) findViewById(R.id.title);
        plot = (TextView) findViewById(R.id.plot);
        rating = (TextView) findViewById(R.id.rating);


        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        release = (TextView) findViewById(R.id.date);

        poster = (ImageView) findViewById(R.id.poster);



refreshMovie();
    }




    public  void refreshMovie(){

        title.setText(movie.getTitle());
        plot.setText(movie.getPlot());
        rating.setText(""+movie.getRating());
        ratingBar.setRating((float) movie.getRating()/2);
        release.setText(Util.DATE_SHOW_FORMAT.format(movie.getReleaseDate()));
        try {
            Picasso.with(this).load(movie.getImageURL()).fit().into(poster);
        }catch (Exception e){
            Log.e("Error "+DetailActivity.class,e.getMessage());
            e.printStackTrace();
        }
    }

}
