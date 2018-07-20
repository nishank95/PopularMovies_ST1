package nishank.android.com.popularmovies_st1.utils;

import android.text.TextUtils;
import android.widget.Adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nishank.android.com.popularmovies_st1.model.CastModel;
import nishank.android.com.popularmovies_st1.model.Movie;
import nishank.android.com.popularmovies_st1.model.Review;
import nishank.android.com.popularmovies_st1.model.Trailer;

/**
 * Created by dell on 6/6/2018.
 */

public class JsonUtils {

    public static List<Movie> fetchJsonMovieResponse(String jsonMovie, String sortBy) {
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
            Movie movie= new Movie(movieId,movieTitle, movieReleaseDate, movieVote, movieOverview, moviePoster, movieBackpath,movieRating,movieAdult);
            movie.setSortBy(sortBy);
            movies.add(movie);

        }
    }
    catch (JSONException e) {
        e.printStackTrace();
    }
        return movies;
    }



    public static List<CastModel> fetchJsonCastResponse(String jsonCast) {
        final String CAST_POSTER_PATH = "profile_path";
        final String CAST_NAME = "name";
        final String CAST_CHARACTER = "character";
        final String CAST_ROOT = "cast";

        if(TextUtils.isEmpty(jsonCast)){
            return null;
        }

        List<CastModel> cast = new ArrayList<CastModel>();

        try {
            JSONObject root = new JSONObject(jsonCast);
            JSONArray movieResult = root.getJSONArray(CAST_ROOT);

            for (int i=0;i<movieResult.length();i++){
                JSONObject movieObject = movieResult.getJSONObject(i);
                String castName = movieObject.optString(CAST_NAME);
                String castCharacter = movieObject.optString(CAST_CHARACTER);
                String castPosterPath = movieObject.optString(CAST_POSTER_PATH);

                cast.add(new CastModel(castName,castCharacter,castPosterPath));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return cast;
    }




    public static List<Trailer> fetchJsonTrailerResponse(String jsonTrailer) {
        final String TRAILER_KEY= "key";
        final String TRAILER_NAME = "name";
        final String CAST_ROOT = "results";
        final String TRAILER_ID = "id";

        if(TextUtils.isEmpty(jsonTrailer)){
            return null;
        }

        List<Trailer> trailer = new ArrayList<Trailer>();

        try {
            JSONObject root = new JSONObject(jsonTrailer);
            JSONArray movieResult = root.getJSONArray(CAST_ROOT);

            for (int i=0;i<movieResult.length();i++){
                JSONObject movieObject = movieResult.getJSONObject(i);
                String trailerID = movieObject.optString(TRAILER_ID);
                String trailerName = movieObject.optString(TRAILER_NAME);
                String trailerKey = movieObject.optString(TRAILER_KEY);

                trailer.add(new Trailer(trailerID,trailerName,trailerKey));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return trailer;
    }



    public static List<Review> fetchJsonReviewResponse(String jsonReview) {
        final String REVIEW_CONTENT = "content";
        final String REVIEW_URL = "url";
        final String REVIEW_AUTHOR = "author";
        final String CAST_ROOT = "results";
        final String REVIEW_ID = "id";

        if(TextUtils.isEmpty(jsonReview)){
            return null;
        }

        List<Review> review = new ArrayList<Review>();

        try {
            JSONObject root = new JSONObject(jsonReview);
            JSONArray reviewResult = root.getJSONArray(CAST_ROOT);

            for (int i=0;i<reviewResult.length();i++){
                JSONObject reviewObject = reviewResult.getJSONObject(i);
                String reviewID = reviewObject.optString(REVIEW_ID);
                String reviewAuthor = reviewObject.optString(REVIEW_AUTHOR);
                String reviewContent = reviewObject.optString(REVIEW_CONTENT);
                String reviewUrl = reviewObject.optString(REVIEW_URL);


                review.add(new Review(reviewID,reviewAuthor,reviewContent,reviewUrl));
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return review;
    }
}

