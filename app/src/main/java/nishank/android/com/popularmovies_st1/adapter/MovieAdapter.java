package nishank.android.com.popularmovies_st1.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nishank.android.com.popularmovies_st1.DetailActivity;
import nishank.android.com.popularmovies_st1.MainActivity;
import nishank.android.com.popularmovies_st1.R;
import nishank.android.com.popularmovies_st1.extras.ItemClickListener;
import nishank.android.com.popularmovies_st1.model.Movie;
import nishank.android.com.popularmovies_st1.utils.DateUtils;

/**
 * Created by dell on 6/3/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    private List<Movie> mMovieObjectList;
    private Context mContext;

    public MovieAdapter(Context c, List<Movie> movies){
        mContext = c;
        mMovieObjectList = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.movies_grid_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        Movie movieObject = mMovieObjectList.get(position);

        String movieReleaseDate =movieObject.getMovieReleaseDate();
        String movieYear = DateUtils.getYear(movieReleaseDate);
        String moviePosterImagePath = movieObject.getMoviePoster();
        String imagePosterLink = mContext.getResources().getString(R.string.api_images_prefix_link);
        imagePosterLink = imagePosterLink + moviePosterImagePath;

        holder.movieTitle.setText(movieObject.getMovieTitle());
        holder.movieYear.setText(movieYear);

        Picasso.get()
                .load(imagePosterLink)
                .placeholder(R.drawable.movie_grid_placeholder)
                .error(R.drawable.movie_grid_placeholder)
                .into(holder.moviePoster);


    }

    @Override
    public int getItemCount() {
        return mMovieObjectList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.movieTitle)TextView movieTitle;
        @BindView(R.id.movieYear)TextView movieYear;
        @BindView(R.id.moviePoster) ImageView moviePoster;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @OnClick
        public void onClick(View view){
            Intent launchDetailActivity = new Intent(mContext,DetailActivity.class);
            launchDetailActivity.putExtra("Movie", mMovieObjectList.get(getAdapterPosition()));
            mContext.startActivity(launchDetailActivity);
        }
    }


}
