package nishank.android.com.popularmovies_st1.utils;

/**
 * Created by dell on 6/16/2018.
 */

public class DateUtils {

    public static String getYear(String releaseDate){

        String movieYear = "";
        String separated[] = releaseDate.split("-");
        movieYear = separated[0];

        return movieYear;
    }

    public static String getMonth(String releaseDate){

        String movieMonth = "";
        String separated[] = releaseDate.split("-");
        movieMonth = separated[1];

        switch (movieMonth){
            case "01" : movieMonth = "January";
                break;
            case "02" : movieMonth = "February";
                break;
            case "03" : movieMonth = "March";
                break;
            case "04" : movieMonth = "April";
                break;
            case "05" : movieMonth = "May";
                break;
            case "06" : movieMonth = "June";
                break;
            case "07" : movieMonth = "July";
                break;
            case "08" : movieMonth = "August";
                break;
            case "09" : movieMonth = "September";
                break;
            case "10" : movieMonth = "October";
                break;
            case "11" : movieMonth = "November";
                break;
            case "12" : movieMonth = "December";
                break;
        }

        return movieMonth;
    }

    public static String getDay(String releaseDate){

        String movieDay = "";
        String separated[] = releaseDate.split("-");
        movieDay = separated[2];
        switch (movieDay){
            case "01" : movieDay = movieDay + "th";
                break;
            case "02" : movieDay = movieDay + "nd";
                break;
            case "03" : movieDay = movieDay + "rd";
                break;
            default: movieDay = movieDay + "th";
        }
        return movieDay;
    }


}

