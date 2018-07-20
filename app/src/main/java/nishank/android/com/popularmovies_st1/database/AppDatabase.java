package nishank.android.com.popularmovies_st1.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import nishank.android.com.popularmovies_st1.model.Movie;

@Database(entities = Movie.class,version = 2,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "moviedb";


    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context){
        if(sInstance == null){
            synchronized (LOCK){
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                          .fallbackToDestructiveMigration()
                          .allowMainThreadQueries()
                          .build();
            }
        }
        Log.d(TAG,"Getting the Database");
        return sInstance;
    }

    public abstract MovieDao movieDao();

}


