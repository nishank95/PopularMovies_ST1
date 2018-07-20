package nishank.android.com.popularmovies_st1.model;

public class CastModel {
    private String mMovieCharacter;
    private String mMovieCastName;
    private String mMovieProfilePath;

    public CastModel(String movieCharacter, String movieCastName, String movieProfilePath){
        mMovieCharacter = movieCharacter;
        mMovieCastName = movieCastName;
        mMovieProfilePath = movieProfilePath;
    }

    public String getMovieCharacter(){
        return mMovieCharacter;
    }

    public String getMovieCastName(){
        return mMovieCastName;
    }

    public String getMovieProfilePath(){
        return mMovieProfilePath;
    }

}
