package nishank.android.com.popularmovies_st1.model;

/**
 * Created by dell on 6/6/2018.
 */

public class Movie {
    private int mMovieId;
    private String mMovieTitle;
    private String mMovieReleaseDate;
    private String mMovieOverview;
    private Double mMovieVote;
    private String mMoviePoster;
    private String mMovieBackPath;
    private Double mMovieRating;
    private Boolean mMovieAdult;
    private Boolean mMovieLike;


    public Movie(int movieId,String movieTitle,String movieReleaseDate,Double movieVote, String movieOverview, String moviePoster, String movieBackPath, Double movieRating,Boolean movieAdult){
        mMovieId = movieId;
        mMovieTitle = movieTitle;
        mMovieReleaseDate = movieReleaseDate;
        mMovieOverview = movieOverview;
        mMovieVote = movieVote;
        mMoviePoster = moviePoster;
        mMovieBackPath = movieBackPath;
        mMovieRating = movieRating;
        mMovieAdult =movieAdult;
    }

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

    public Boolean getMovieLike() {
        return mMovieLike;
    }

    public void setMovieLike(Boolean input){
        mMovieLike = input;
    }



}
