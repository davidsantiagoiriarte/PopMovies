package com.example.david.popmovies.util;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.david.popmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.concurrent.Exchanger;

/**
 * Created by david on 16/08/17.
 */

public class Util {
    /**
     * Log TAG
     */
    private static final String TAG = Util.class.getSimpleName();
    /**
     * themoviedb.org API's Key
     */
    public static String API_KEY="";
    /**
     * URL for most popular movies
     */
    public static String POPULAR_MOVIES_URL="https://api.themoviedb.org/3/movie/popular?api_key="+API_KEY+"&language=en-US&page=1";
    /**
     * URL for top rated movies
     */
    public static String TOP_RATED_MOVIES_URL="https://api.themoviedb.org/3/movie/top_rated?api_key="+API_KEY+"&language=en-US&page=1";
    /**
     * URL for get the images
     */
    public static String BAS_IMAGES_URL="http://image.tmdb.org/t/p/w185/";
    /**
     * Current list of movies
     */
    public static String currentURL=Util.TOP_RATED_MOVIES_URL;

    /**
     * Date Format to get from JSON
     */
    public static SimpleDateFormat DATE_FORMAT=new SimpleDateFormat("yyyy-MM-dd");
    /**
     * Date Format to show
     */
    public static SimpleDateFormat DATE_SHOW_FORMAT=new SimpleDateFormat("MMMM dd yyyy");

    /**
     * Builds an URL from given String
     * @param urlString
     * @return
     */
    public static URL buildUrl(String urlString){
        Uri builtUri = Uri.parse(urlString).buildUpon()
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    /**
     * Gets the response from http Url request
     * @param url
     * @return
     * @throws IOException
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    /**
     * Transform the JSON given text to a list of Movies
     * @param context
     * @param jsonString
     * @return
     */
    public  static Movie[] getSimpleMoviesDataFromJson(Context context, String jsonString){
        Movie[] movs = null;
try {
    JSONObject jsonObject = new JSONObject(jsonString);
    JSONArray array = jsonObject.getJSONArray("results");
    movs = new Movie[array.length()];
    for (int i=0; i<array.length();i++) {
        JSONObject item = (JSONObject) array.get(i);
        Movie mov = new Movie();
        mov.setTitle(item.getString("original_title"));
        mov.setPlot(item.getString("overview"));
        mov.setRating(item.getDouble("vote_average"));
        mov.setReleaseDate(DATE_FORMAT.parse(item.getString("release_date")));
        mov.setImageURL(BAS_IMAGES_URL+item.getString("poster_path"));
        movs[i]=mov;
    }
}catch (Exception ex){
    Log.d("Error",ex.getMessage());
    ex.printStackTrace();
}

        return movs;

    }
}
