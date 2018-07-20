package nishank.android.com.popularmovies_st1.model;

public class Review {

    private String mReviewAuthor;
    private String mReviewContent;
    private String mReviewUrl;
    private String mReviewID;

    public Review( String reviewID, String reviewAuthor, String reviewContent, String reviewUrl) {
        mReviewID =reviewID;
        mReviewAuthor = reviewAuthor;
        mReviewContent = reviewContent;
        mReviewUrl =reviewUrl;
    }

    public String getReviewID(){
        return mReviewID;
    }

    public String getReviewAuthor(){
        return mReviewAuthor;
    }

    public String getReviewContent(){
        return mReviewContent;
    }

    public String getReviewUrl(){
        return mReviewUrl;
    }

}

