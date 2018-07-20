package nishank.android.com.popularmovies_st1.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import nishank.android.com.popularmovies_st1.R;
import nishank.android.com.popularmovies_st1.model.CastModel;
import nishank.android.com.popularmovies_st1.model.Movie;

public class MovieCastAdapter extends RecyclerView.Adapter<MovieCastAdapter.ViewHolder> {

private Context mContext;
private List<CastModel> mCastObjectList;


public MovieCastAdapter(Context c, List<CastModel> cast){
    mContext = c;
    mCastObjectList = cast;
}

@NonNull
@Override
public MovieCastAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_movie_cast,parent,false);
        return new MovieCastAdapter.ViewHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull MovieCastAdapter.ViewHolder holder, int position) {
        CastModel object = mCastObjectList.get(position);
        holder.castName.setText(object.getMovieCastName());
        holder.castCharacter.setText(object.getMovieCharacter());
        String imagePosterLink = "http://image.tmdb.org/t/p/w200/";
        String cast_profile_path = object.getMovieProfilePath();
        imagePosterLink = imagePosterLink + cast_profile_path;

        Picasso.get()
            .load(imagePosterLink)
            .placeholder(R.drawable.movie_grid_placeholder)
            .error(R.drawable.movie_grid_placeholder)
            .into(holder.castProfile);

}

@Override
public int getItemCount() {
        return mCastObjectList.size();
        }

class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.castName)TextView castName;
    @BindView(R.id.castCharacter)TextView castCharacter;
    @BindView(R.id.castProfile)CircleImageView castProfile;

    ViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
}

