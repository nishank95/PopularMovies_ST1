package nishank.android.com.popularmovies_st1.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dell on 6/6/2018.
 */

@Entity(tableName = "movies")
public class Movie implements Parcelable{

    @PrimaryKey(autoGenerate = true)
    private int uid;

    private int mMovieId;
    private String mMovieTitle;
    private String mMovieReleaseDate;
    private String mMovieOverview;
    private Double mMovieVote;
    private String mMoviePoster;
    private String mMovieBackPath;
    private Double mMovieRating;
    private Boolean mMovieAdult;
    private int mMovieIsFav=0;
    private String sortBy;


    public Movie(int movieId,String movieTitle,String movieReleaseDate,Double movieVote, String movieOverview, String moviePoster, String movieBackPath, Double movieRating,Boolean movieAdult){
        mMovieId = movieId;
        mMovieTitle = movieTitle;
        mMovieReleaseDate = movieReleaseDate;
        mMovieOverview = movieOverview;
        mMovieVote = movieVote;
        mMoviePoster = moviePoster;
        mMovieBackPath = movieBackPath;
        mMovieRating = movieRating;
        mMovieAdult = movieAdult;
    }

    public int getUid(){return uid; }

    public int getMovieId(){
        return mMovieId;
    }

    public String getMovieTitle(){
        return mMovieTitle;
    }

    public String getMovieReleaseDate(){
        return mMovieReleaseDate;
    }

    public String getMovieOverview(){
        return mMovieOverview;
    }

    public Double getMovieVote(){
        return mMovieVote;
    }

    public String getMoviePoster(){
        return mMoviePoster;
    }

    public String getMovieBackPath(){
        return mMovieBackPath;
    }

    public Double getMovieRating(){
        return mMovieRating;
    }

    public Boolean getMovieAdult(){
        return mMovieAdult;
    }

    public int getMovieIsFav() {
        return mMovieIsFav;
    }

    public String getSortBy() { return sortBy; }

    public void setUid(int uid){this.uid =uid; }

    public void setMovieId(int movieId){ this.mMovieId = movieId;}

    public void setMovieTitle(String movieTitle){
        this.mMovieTitle=movieTitle;
    }

    public void setMovieReleaseDate(String movieReleaseDate){ this.mMovieReleaseDate = movieReleaseDate; }

    public void setMovieOverview(String movieOverview){this.mMovieOverview=movieOverview; }

    public void setMovieVote(Double movieVote){
        this.mMovieVote =movieVote;
    }

    public void setMoviePoster(String moviePoster){
        this.mMoviePoster=moviePoster;
    }

    public void setMovieBackPath(String movieBackPath){ this.mMovieBackPath = movieBackPath; }

    public void setMovieRating(Double movieRating){
        this.mMovieRating=movieRating;
    }

    public void setMovieAdult(Boolean movieAdult){
        this.mMovieAdult=movieAdult;
    }

    public void setMovieIsFav(int movieIsFav) {
        this.mMovieIsFav = movieIsFav;
    }

    public void setSortBy(String sortBy) { this.sortBy = sortBy; }



    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeInt(mMovieId);
        dest.writeString(mMovieTitle);
        dest.writeString(mMovieOverview);
        dest.writeString(mMoviePoster);
        dest.writeString(mMovieReleaseDate);
        dest.writeString(mMovieBackPath);
        dest.writeDouble(mMovieVote);
        dest.writeDouble(mMovieRating);
        dest.writeValue(mMovieAdult);
    }

    private Movie(Parcel in) {
        mMovieId = in.readInt();
        mMovieTitle = in.readString();
        mMovieOverview = in.readString();
        mMoviePoster = in.readString();
        mMovieReleaseDate = in.readString();
        mMovieBackPath = in.readString();
        mMovieVote = in.readDouble();
        mMovieRating = in.readDouble();
        byte tmpMMovieAdult = in.readByte();
        mMovieAdult = tmpMMovieAdult == 0 ? null : tmpMMovieAdult == 1;
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[0];
        }
    };

    @Override
    public int describeContents() {
        return hashCode();
    }

}
