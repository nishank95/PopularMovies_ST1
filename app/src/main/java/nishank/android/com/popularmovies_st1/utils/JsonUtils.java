package nishank.android.com.popularmovies_st1.utils;

import android.text.TextUtils;
import android.widget.Adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nishank.android.com.popularmovies_st1.model.Movie;

/**
 * Created by dell on 6/6/2018.
 */

public class JsonUtils {

    public static List<Movie> fetchJsonResponse(String jsonMovie) {
        final String MOVIE_ROOT = "results";
        final String MOVIE_ID = "id";
        final String MOVIE_TITLE = "title";
        final String RELEASE_DATE = "release_date";
        final String MOVIE_OVERVIEW = "overview";
        final String MOVIE_POSTER_PATH = "poster_path";
        final String MOVIE_BACK_PATH = "backdrop_path";
        final String MOVIE_VOTE_COUNT = "vote_count";
        final String MOVIE_VOTE_AVERAGE = "vote_average";
        final String MOVIE_ADULT = "adult";

        if(TextUtils.isEmpty(jsonMovie)){
            return null;
        }

        List<Movie> movies = new ArrayList<Movie>();

        try {
        JSONObject root = new JSONObject(jsonMovie);
        JSONArray movieResult = root.getJSONArray(MOVIE_ROOT);

        for (int i=0;i<movieResult.length();i++){
            JSONObject movieObject = movieResult.getJSONObject(i);
            int movieId = movieObject.optInt(MOVIE_ID);
            String movieTitle = movieObject.optString(MOVIE_TITLE);
            String movieReleaseDate = movieObject.optString(RELEASE_DATE);
            String movieOverview = movieObject.optString(MOVIE_OVERVIEW);
            String moviePoster = movieObject.optString(MOVIE_POSTER_PATH);
            String movieBackpath = movieObject.optString(MOVIE_BACK_PATH);
            Double movieVote = movieObject.optDouble(MOVIE_VOTE_COUNT);
            Double movieRating = movieObject.optDouble(MOVIE_VOTE_AVERAGE);
            Boolean movieAdult = movieObject.optBoolean(MOVIE_ADULT);

            movies.add(new Movie(movieId,movieTitle, movieReleaseDate, movieVote, movieOverview, moviePoster, movieBackpath,movieRating,movieAdult));
        }
    }
    catch (JSONException e) {
        e.printStackTrace();
    }
        return movies;
    }
}
