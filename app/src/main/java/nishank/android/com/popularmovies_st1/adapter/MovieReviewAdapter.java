package nishank.android.com.popularmovies_st1.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import nishank.android.com.popularmovies_st1.R;
import nishank.android.com.popularmovies_st1.model.Review;
import nishank.android.com.popularmovies_st1.model.Trailer;

public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewAdapter.ViewHolder> {

    private Context mContext;
    private List<Review> mReviewObjectList;

    public MovieReviewAdapter(Context context, List<Review> review){
        mContext = context;
        mReviewObjectList = review;
    }

    @NonNull
    @Override
    public MovieReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_movie_review, parent, false);
        return new MovieReviewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieReviewAdapter.ViewHolder holder, int position) {
        Review object = mReviewObjectList.get(position);
        holder.reviewName.setText(object.getReviewAuthor());
        holder.reviewContent.setText(object.getReviewContent());
    }

    @Override
    public int getItemCount() {
        return mReviewObjectList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.review_name)TextView reviewName;
        @BindView(R.id.review_details)TextView reviewContent;
        @BindView(R.id.view_more)Button viewMore;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }

        @OnClick(R.id.view_more)
        void onClick(View view){
          int position = getAdapterPosition();
          Review clickedReview = mReviewObjectList.get(position);
          Intent i= new Intent(Intent.ACTION_VIEW, Uri.parse(clickedReview.getReviewUrl()));
          mContext.startActivity(i);
        }
    }
}