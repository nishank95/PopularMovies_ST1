package nishank.android.com.popularmovies_st1.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nishank.android.com.popularmovies_st1.R;
import nishank.android.com.popularmovies_st1.model.CastModel;
import nishank.android.com.popularmovies_st1.model.Trailer;

public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.ViewHolder> {

    private Context mContext;
    private List<Trailer> mTrailerObjectList;
    private String YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v=";
    private String YOUTUBE_APP_VIDEO_URL = "vnd.youtube:";

    public MovieTrailerAdapter(Context c, List<Trailer> trailer){
        mContext = c;
        mTrailerObjectList = trailer;
    }
    @NonNull
    @Override
    public MovieTrailerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_movie_trailer,parent,false);
        return new MovieTrailerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieTrailerAdapter.ViewHolder holder, int position) {

        Trailer object = mTrailerObjectList.get(position);
        holder.trailerTitle.setText(object.getTrailerTitle());
        final String VIDEO_KEY = object.getTrailerKey();
        String YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/";
        YOUTUBE_THUMBNAIL_URL = YOUTUBE_THUMBNAIL_URL + VIDEO_KEY + "/0.jpg";
        YOUTUBE_VIDEO_URL = YOUTUBE_VIDEO_URL + VIDEO_KEY;

        Picasso.get()
                .load(YOUTUBE_THUMBNAIL_URL)
                .placeholder(R.drawable.movie_grid_placeholder)
                .error(R.drawable.movie_grid_placeholder)
                .into(holder.trailerPoster);

    }

    @Override
    public int getItemCount() {
        return mTrailerObjectList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.trailer_title) TextView trailerTitle;
        @BindView(R.id.trailer_thumbnail) ImageView trailerPoster;

        ViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @OnClick
        void onClick(View view){
        int position = getAdapterPosition();
        Trailer clickedTrailer = mTrailerObjectList.get(position);
        String key = clickedTrailer.getTrailerKey();
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_APP_VIDEO_URL + key));
        mContext.startActivity(appIntent);
        }

    }


}
