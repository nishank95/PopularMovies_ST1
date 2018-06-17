package nishank.android.com.popularmovies_st1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.squareup.picasso.Picasso;

import java.util.Date;

import nishank.android.com.popularmovies_st1.utils.DateUtils;

public class DetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Detail Activity Toolbar Configuration
        Toolbar mvToolbar =(Toolbar)findViewById(R.id.detailActivityToolbar);
        mvToolbar.setTitle("");
        mvToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(mvToolbar);

        //Layout Elements Binding
        TextView mvTitle = (TextView)findViewById(R.id.movieTitle);
        TextView mvOverview = (TextView)findViewById(R.id.movieOverview);
        TextView mvRating = (TextView)findViewById(R.id.movieRating);
        TextView mvVotes = (TextView)findViewById(R.id.movieVote);
        TextView mvCategory = (TextView)findViewById(R.id.movieCategory);
        TextView mvReleaseDate = (TextView)findViewById(R.id.movieReleaseDate);
        ImageView mvPoster = (ImageView)findViewById(R.id.moviePoster);
        ImageView mvBackPathImage = (ImageView)findViewById(R.id.movieBackPathImage);


        //Extract data from Bundle passed from MainActivity
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String movieTitle = bundle.getString("movieTitle");
            String movieReleaseDate = bundle.getString("movieReleaseDate");
            String moviePosterPath = bundle.getString("moviePoster");
            String movieBackPath = bundle.getString("movieBackPath");
            String movieOverview = bundle.getString("movieOverview");
            String movieVote = bundle.getString("movieVote");
            String movieRating = bundle.getString("movieRating");
            Boolean movieAdult = bundle.getBoolean("movieAdult");

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

            //Load images in respectie ImageView's
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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id =item.getItemId();
        switch (id){
            case R.id.detail_menu_like:
                item.setIcon(R.drawable.ic_heart_fill);
                break;
        }
        return super.onOptionsItemSelected(item);
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

}

