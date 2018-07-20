package nishank.android.com.popularmovies_st1.model;

public class Trailer {

        private String mTrailerTitle;
        private String mTrailerKey;
        private String mTrailerID;

        public Trailer( String trailerID, String trailerTitle, String trailerKey) {
            mTrailerTitle = trailerTitle;
            mTrailerID = trailerID;
            mTrailerKey = trailerKey;
        }

        public String getTrailerTitle(){
            return mTrailerTitle;
        }

        public String getTrailerKey(){
            return mTrailerKey;
        }

        public String getTrailerID(){
            return mTrailerID;
        }

    }

