package nishank.android.com.popularmovies_st1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nishank.android.com.popularmovies_st1.adapter.MovieCastAdapter;
import nishank.android.com.popularmovies_st1.adapter.MovieReviewAdapter;
import nishank.android.com.popularmovies_st1.adapter.MovieTrailerAdapter;
import nishank.android.com.popularmovies_st1.database.AppDatabase;
import nishank.android.com.popularmovies_st1.model.CastModel;
import nishank.android.com.popularmovies_st1.model.Movie;
import nishank.android.com.popularmovies_st1.model.Review;
import nishank.android.com.popularmovies_st1.model.Trailer;
import nishank.android.com.popularmovies_st1.utils.AppExecutor;
import nishank.android.com.popularmovies_st1.utils.DateUtils;
import nishank.android.com.popularmovies_st1.utils.JsonUtils;
import nishank.android.com.popularmovies_st1.utils.NetworkUtils;

public class DetailActivity extends AppCompatActivity {

    //Layout Elements Binding
    @BindView(R.id.movieTitle) TextView mvTitle;
    @BindView(R.id.movieOverview) TextView mvOverview;
    @BindView(R.id.movieRating) TextView mvRating;
    @BindView(R.id.movieVote) TextView mvVotes;
    @BindView(R.id.movieCategory) TextView mvCategory;
    @BindView(R.id.movieReleaseDate) TextView mvReleaseDate;
    @BindView(R.id.moviePoster) ImageView mvPoster;
    @BindView(R.id.movieBackPathImage) ImageView mvBackPathImage;
    @BindView(R.id.movieTrailerView) RecyclerView mvTrailerView;
    @BindView(R.id.movieCastView) RecyclerView mvCastView;
    @BindView(R.id.movieReviewView) RecyclerView mvReviewView;
    @BindView(R.id.trailerErrorTextView) TextView mvTrailerErrorTextView;
    @BindView(R.id.castErrorTextView) TextView mvCastErrorTextView;
    @BindView(R.id.reviewErrorTextView) TextView mvReviewErrorTextView;
    @BindView(R.id.iv_like) ImageView mMovieLike;

    int isFav=0;
    AppDatabase appDatabase = null;
    MovieTrailerAdapter mvTrailerAdapter;
    LinearLayoutManager mvTrailerLayoutManager;
    MovieCastAdapter mvCastAdapter;
    LinearLayoutManager mvCastLayoutManager;
    MovieReviewAdapter mvReviewAdapter;
    LinearLayoutManager mvReviewLayoutManager;
    Movie currentMovie;
    String MOVIE_DB_API = "http://api.themoviedb.org/3/movie/";
    final String API_KEY = "01c0f588b93e07e631c4a65f7109f5e6";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        //Detail Activity Toolbar Configuration
        Toolbar mvToolbar =(Toolbar)findViewById(R.id.detailActivityToolbar);
        mvToolbar.setTitle("");
        mvToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(mvToolbar);

        appDatabase = AppDatabase.getInstance(this);

        //Trailer Recycler view settings
        mvTrailerLayoutManager = new LinearLayoutManager(DetailActivity.this);
        mvTrailerLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mvTrailerView.setLayoutManager(mvTrailerLayoutManager);

        //Cast Recycler view settings
        mvCastLayoutManager = new LinearLayoutManager(DetailActivity.this);
        mvCastLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mvCastView.setLayoutManager(mvCastLayoutManager);

        //Review Recycler view settings
        mvReviewLayoutManager = new LinearLayoutManager(DetailActivity.this);
        mvReviewLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mvReviewView.setLayoutManager(mvReviewLayoutManager);


        currentMovie = this.getIntent().getParcelableExtra("Movie");
        int movieID = currentMovie.getMovieId();
        currentMovie = appDatabase.movieDao().getMovie(movieID);
        isFav = currentMovie.getMovieIsFav();
        Log.d("DB","Liked: " + isFav );

        checkBookMark();

        if(checkInternetConnectivity()){
            populateDetailsLayout(currentMovie);
        }
        else{
            currentMovie=appDatabase.movieDao().getMovie(movieID);
            Log.d("DB","Database Call");
            populateDetailsLayout(currentMovie);
        }
        }

    private void checkBookMark() {
        if(isFav==1)
        {
            mMovieLike.setImageResource(R.drawable.ic_heart_fill);
        }
        else{
            mMovieLike.setImageResource(R.drawable.ic_heart_outline);
        }

    }


    @OnClick(R.id.iv_like)
        void onClick(View view){
            AppExecutor.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {

                    if(isFav == 1){
                        mMovieLike.setImageResource(R.drawable.ic_heart_outline);
                        appDatabase.movieDao().setMovieUnFav(currentMovie.getMovieId());
                        isFav=0;
                    }
                    else{
                        appDatabase.movieDao().setMovieFav(currentMovie.getMovieId());
                        mMovieLike.setImageResource(R.drawable.ic_heart_fill);
                        isFav=1;
                        }


                }
            });


    }

    //Function for checking and selecting the category of movie
    private String checkCategory(Boolean movieAdult){
        if(movieAdult){
            return getResources().getString(R.string.movie_certificate_adult);
        }
        else{
            return getResources().getString(R.string.movie_certificate_non_adult);
        }
    }

    //Fetching other movie details asynchronously
    @Override
    protected void onStart() {
        super.onStart();
        if(checkInternetConnectivity()){
            new FetchCastTask().execute(Integer.toString(currentMovie.getMovieId()));
            new FetchTrailerTask().execute(Integer.toString(currentMovie.getMovieId()));
            new FetchReviewTask().execute(Integer.toString(currentMovie.getMovieId()));
        }
    }

    private void populateDetailsLayout(Movie currentMovie){

        //Extract data from Parcelable Object passed from MainActivity
        String movieTitle = currentMovie.getMovieTitle();
        String movieReleaseDate = currentMovie.getMovieReleaseDate();
        String moviePosterPath = currentMovie.getMoviePoster();
        String movieBackPath = currentMovie.getMovieBackPath();
        String movieOverview = currentMovie.getMovieOverview();
        String movieVote =currentMovie.getMovieVote().toString();
        String movieRating = currentMovie.getMovieRating().toString();
        Boolean movieAdult = currentMovie.getMovieAdult();

        //Format Date
        String movieDay = DateUtils.getDay(movieReleaseDate);
        String movieMonth = DateUtils.getMonth(movieReleaseDate);
        String movieYear = DateUtils.getYear(movieReleaseDate);
        String movieFormattedDate = movieDay + " " + movieMonth + " " + movieYear;

        //Add prelink to Image Path
        String imagePosterLink = getResources().getString(R.string.api_images_prefix_link) + moviePosterPath;
        String imageBackPath = getResources().getString(R.string.api_images_prefix_link) + movieBackPath;

        //Get Movie Certificate
        String movieCertificate = checkCategory(movieAdult);

        //Set Data on TextView's in Detail Activity
        mvTitle.setText(movieTitle);
        mvRating.setText(movieRating);
        mvVotes.setText(movieVote);
        mvCategory.setText(movieCertificate);
        mvOverview.setText(movieOverview);
        mvReleaseDate.setText(movieFormattedDate);

        //Load images in respective ImageView's
        Picasso.get()
                .load(imagePosterLink)
                .placeholder(R.drawable.movie_grid_placeholder)
                .into(mvPoster);

        Picasso.get()
                .load(imageBackPath)
                .placeholder(R.drawable.movie_grid_placeholder)
                .error(R.drawable.error_image)
                .into(mvBackPathImage);

    }


    @SuppressLint("StaticFieldLeak")
    private class FetchCastTask extends AsyncTask<String,Void,List<CastModel>>{


        @Override
        protected List<CastModel> doInBackground(String... params) {

            if(params.length == 0){
                Log.e("BG","PArams length is 0");
                return null;

            }

            String jsonResponse = null;
            final String BASE_URL = MOVIE_DB_API + params[0] + "/credits";
            Uri builtUri = Uri.parse(BASE_URL).buildUpon().appendQueryParameter("api_key",API_KEY).build();

            try {
                URL url = new URL(builtUri.toString());
                jsonResponse = NetworkUtils.httpConnectionResponse(url);
                Log.e("BG", builtUri.toString());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return JsonUtils.fetchJsonCastResponse(jsonResponse);

        }

        @Override
        protected void onPostExecute(List<CastModel> castModels) {
            if(castModels!= null){
                if(castModels.size() > 0){
                  mvCastView.setVisibility(View.VISIBLE);
                  mvCastErrorTextView.setVisibility(View.INVISIBLE);
                  mvCastAdapter = new MovieCastAdapter(DetailActivity.this,castModels);
                  mvCastView.setAdapter(mvCastAdapter);
                }
            }
            else{
                mvCastErrorTextView.setVisibility(View.VISIBLE);
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class FetchTrailerTask extends AsyncTask<String,Void,List<Trailer>>{


        @Override
        protected List<Trailer> doInBackground(String... params) {

            if(params.length == 0){
                Log.e("BG","PArams length is 0");
                return null;

            }

            String jsonResponse = null;
            final String BASE_URL = MOVIE_DB_API + params[0] + "/videos";
            Uri builtUri = Uri.parse(BASE_URL).buildUpon().appendQueryParameter("api_key",API_KEY).build();

            try {
                URL url = new URL(builtUri.toString());
                jsonResponse = NetworkUtils.httpConnectionResponse(url);
                Log.e("BG", builtUri.toString());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return JsonUtils.fetchJsonTrailerResponse(jsonResponse);

        }

        @Override
        protected void onPostExecute(List<Trailer> trailer) {
            if(trailer!= null){
                if(trailer.size() > 0){
                    mvTrailerView.setVisibility(View.VISIBLE);
                    mvTrailerErrorTextView.setVisibility(View.INVISIBLE);
                    mvTrailerAdapter = new MovieTrailerAdapter(DetailActivity.this,trailer);
                    mvTrailerView.setAdapter(mvTrailerAdapter);
                }
            }
            else{
                mvTrailerErrorTextView.setVisibility(View.VISIBLE);
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class FetchReviewTask extends AsyncTask<String,Void,List<Review>>{

        @Override
        protected List<Review> doInBackground(String... params) {

            if(params.length == 0){
                Log.e("BG","Params length is 0");
                return null;
            }

            String jsonResponse = null;
            final String BASE_URL = MOVIE_DB_API + params[0] + "/reviews";
            Uri builtUri = Uri.parse(BASE_URL).buildUpon().appendQueryParameter("api_key",API_KEY).build();

            try {
                URL url = new URL(builtUri.toString());
                jsonResponse = NetworkUtils.httpConnectionResponse(url);
                Log.e("BG", builtUri.toString());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return JsonUtils.fetchJsonReviewResponse(jsonResponse);

        }

        @Override
        protected void onPostExecute(List<Review> review) {
            if(review!= null){
                if(review.size() > 0){
                    mvReviewView.setVisibility(View.VISIBLE);
                    mvReviewAdapter = new MovieReviewAdapter(DetailActivity.this, review);
                    mvReviewView.setAdapter(mvReviewAdapter);
                }
            }
            else{
                mvReviewErrorTextView.setVisibility(View.VISIBLE);
            }
        }
    }

    public boolean checkInternetConnectivity(){
        //Check internet connection//
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }


}


