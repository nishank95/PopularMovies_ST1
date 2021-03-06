package nishank.android.com.popularmovies_st1;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import nishank.android.com.popularmovies_st1.adapter.MovieAdapter;
import nishank.android.com.popularmovies_st1.database.AppDatabase;
import nishank.android.com.popularmovies_st1.model.Movie;
import nishank.android.com.popularmovies_st1.utils.JsonUtils;
import nishank.android.com.popularmovies_st1.utils.NetworkUtils;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>> {

    //API Related Strings
    private String MOVIE_DB_POPULAR_API;
    private String MOVIE_DB_TOP_RATED_API;
    private String MOVIE_DB_API;

    //Loader ID
    private static final int MOVIE_LOADER_ID = 26;

    public MovieAdapter movieAdapter;
    public LoaderManager movieLoader;
    private ProgressBar progressBar;
    private TextView errorMessage;
    private ImageView errorImage;
    public static String sortBy = "popular";
    RecyclerView recyclerView;
    List<Movie> mMovieObject= new ArrayList<Movie>();
    List<Movie> favMovieList;
    AppDatabase appDatabase = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Fetch API Path fromString Resource
        MOVIE_DB_POPULAR_API = getResources().getString(R.string.api_movie_popular_link);
        MOVIE_DB_TOP_RATED_API = getResources().getString(R.string.api_movie_top_rated_link);
        MOVIE_DB_API = MOVIE_DB_POPULAR_API;
        appDatabase = AppDatabase.getInstance(this);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        errorMessage = (TextView) findViewById(R.id.errorMessage);
        errorImage = (ImageView) findViewById(R.id.errorImage);

        recyclerView = (RecyclerView) findViewById(R.id.movies_rv);
        int numberOfColumns = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        movieAdapter = new MovieAdapter(this, new ArrayList<Movie>());

        boolean internet_status = checkInternetConnectivity();

        if(internet_status){
            movieLoader = getLoaderManager();
            movieLoader.initLoader(MOVIE_LOADER_ID, null, this).forceLoad();
        }
        else{
            progressBar.setVisibility(View.INVISIBLE);
            List<Movie> movieDbList = appDatabase.movieDao().getMovieList(sortBy);
            movieAdapter = new MovieAdapter(this, movieDbList);
            recyclerView.setAdapter(movieAdapter);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.setIcon(R.drawable.ic_filter_list_black_24dp);
        switch (item.getItemId()){
            case R.id.popular_movies:
                MOVIE_DB_API = MOVIE_DB_POPULAR_API;
                boolean internet_status = checkInternetConnectivity();
                sortBy= "popular";
                if(internet_status){
                    getLoaderManager().restartLoader(0,null,MainActivity.this).forceLoad();
                    showContent();
                }
                else{
                        List<Movie> movieDbList = appDatabase.movieDao().getMovieList(sortBy);
                        movieAdapter = new MovieAdapter(this, movieDbList);
                        recyclerView.setAdapter(movieAdapter);
                }
                break;

            case R.id.top_rated_movies:
                MOVIE_DB_API = MOVIE_DB_TOP_RATED_API;
                internet_status = checkInternetConnectivity();
                sortBy ="top_rated";
                if(internet_status){
                    getLoaderManager().restartLoader(0,null,MainActivity.this).forceLoad();
                    showContent();
                }
                else{
                    List<Movie> movieDbList = appDatabase.movieDao().getMovieList(sortBy);
                    movieAdapter = new MovieAdapter(this, movieDbList);
                    recyclerView.setAdapter(movieAdapter);
                }
                break;

            case R.id.favourite_movies:
                favMovieList = appDatabase.movieDao().getMovieFavList();
                if(favMovieList.size() == 0){
                    hideContent();
                }
                else{
                    showContent();
                }
                movieAdapter = new MovieAdapter(MainActivity.this,favMovieList);
                recyclerView.setAdapter(movieAdapter);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void hideContent() {
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        errorMessage.setVisibility(View.VISIBLE);
        errorImage.setVisibility(View.VISIBLE);
    }

    private void showContent() {
        recyclerView.setVisibility(View.VISIBLE);
        errorMessage.setVisibility(View.GONE);
        errorImage.setVisibility(View.GONE);
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
        return new MovieLoader(this, MOVIE_DB_API);
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {

        progressBar.setVisibility(View.GONE);
        mMovieObject = movies;
        movieAdapter = new MovieAdapter(this,movies);
        recyclerView.setAdapter(movieAdapter);
        List<Movie> movieDbList = appDatabase.movieDao().getMovieList(sortBy);
        if(movieDbList.size()!= movies.size()){
            appDatabase.movieDao().addMovies(movies);
        }
        movieAdapter.notifyDataSetChanged();
    }


    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {

    }

    public static class MovieLoader extends AsyncTaskLoader<List<Movie>>{
        URL mUrl;
        MovieLoader(Context context, String url) {
            super(context);
            try {
                mUrl = new URL(url);
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public List<Movie> loadInBackground() {
            try {
                String jsonResponse = NetworkUtils.httpConnectionResponse(mUrl);
                return JsonUtils.fetchJsonMovieResponse(jsonResponse,sortBy);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return null;
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
