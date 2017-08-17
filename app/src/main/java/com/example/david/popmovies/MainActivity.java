package com.example.david.popmovies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.david.popmovies.model.*;

import com.example.david.popmovies.util.Util;
import com.squareup.picasso.Picasso;

import java.net.URL;

public class MainActivity extends AppCompatActivity  implements MovieAdapter.MovieAdapterOnClickHandler{

    private RecyclerView mRecyclerView;
    private TextView error;
    private ProgressBar progressBar;

    private  MovieAdapter movieAdapter;

    public  static  int default_height;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        default_height= metrics.heightPixels; // ancho absoluto en pixels



        mRecyclerView= (RecyclerView) findViewById(R.id.recycler_view);
        error = (TextView) findViewById(R.id.tv_error_message_display);

        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        movieAdapter=  new MovieAdapter(this);
        mRecyclerView.setAdapter(movieAdapter);

        progressBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);

         loadWMovieData();
    }

    private void loadWMovieData() {
        showMoviesDataView();

     new FetchMoviesTask().execute(Util.currentURL);
    }
    private void showMoviesDataView() {
        error.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }


    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        error.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(Movie movieDetail) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra("movie", movieDetail);
        startActivity(intentToStartDetailActivity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

          MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_top_rated){
            Util.currentURL=Util.TOP_RATED_MOVIES_URL;
            loadWMovieData();
            return true;
        }
        else if(item.getItemId()==R.id.action_most_popular){
            Util.currentURL=Util.POPULAR_MOVIES_URL;
            loadWMovieData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, Movie[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Movie[] doInBackground(String... params) {

            /* If there's no zip code, there's nothing to look up. */
            if (params.length == 0) {
                return null;
            }

            String location = params[0];
            URL dataRequestUrl = Util.buildUrl(location);

            try {
                String jsonDataResponse = Util
                        .getResponseFromHttpUrl(dataRequestUrl);

                Movie[] simpleJsonMovieData = Util
                        .getSimpleMoviesDataFromJson(MainActivity.this, jsonDataResponse);

                return simpleJsonMovieData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Movie[] data) {
            progressBar.setVisibility(View.INVISIBLE);
            if (data != null) {
                showMoviesDataView();


                movieAdapter.setMoviesData(data);
            } else {
                showErrorMessage();
            }
        }
    }



}
