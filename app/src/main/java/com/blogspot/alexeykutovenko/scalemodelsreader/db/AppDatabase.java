package com.blogspot.alexeykutovenko.scalemodelsreader.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.blogspot.alexeykutovenko.scalemodelsreader.AppExecutors;
import com.blogspot.alexeykutovenko.scalemodelsreader.DataRepository;
import com.blogspot.alexeykutovenko.scalemodelsreader.ScalemodelsApp;
import com.blogspot.alexeykutovenko.scalemodelsreader.db.converters.DataConverters;
import com.blogspot.alexeykutovenko.scalemodelsreader.db.dao.FeaturedDao;
import com.blogspot.alexeykutovenko.scalemodelsreader.db.dao.PostDao;
import com.blogspot.alexeykutovenko.scalemodelsreader.db.entity.FeaturedEntity;
import com.blogspot.alexeykutovenko.scalemodelsreader.db.entity.PostEntity;
import com.blogspot.alexeykutovenko.scalemodelsreader.network.ScalemodelsApi;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Database(entities = {PostEntity.class, FeaturedEntity.class}, version = 1)
@TypeConverters(DataConverters.class)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase sInstance;

    @VisibleForTesting
    public static final String DATABASE_NAME = "smreader-db";

    public abstract PostDao postDao();
    public abstract FeaturedDao featuredDao();


    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static AppDatabase getInstance(final Context context, final AppExecutors executors) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext(), executors);
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    /**
     * Build the database. {@link Builder#build()} only sets up the database configuration and
     * creates a new instance of the database.
     * The SQLite database is only created when it's accessed for the first time.
     */
    private static AppDatabase buildDatabase(final Context appContext,
                                             final AppExecutors executors) {
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        executors.diskIO().execute(() -> {
                            AppDatabase database = AppDatabase.getInstance(appContext, executors);
                            //First run - get full initial data from Scalemates
                            ScalemodelsApp scalemodelsApp = (ScalemodelsApp)appContext.getApplicationContext();
                            ScalemodelsApi scalemodelsApi = scalemodelsApp.getScalemodelsApi();
                            DataRepository dataRepository = DataRepository.getInstance(database, scalemodelsApi);
//                            List<PostEntity> scalematesPosts = dataRepository.getInitialPostsFromScalemodels();
                            List<PostEntity> posts = DataGenerator.generatePosts();
                            List<FeaturedEntity> featuredEntities = DataGenerator.generateFeatured();
                            insertData(database, featuredEntities);
//                            insertData(database, posts, featuredEntities);
                            // notify that the database was created and it's ready to be used
                            database.setDatabaseCreated();
                        });
                    }
                }).build();
    }

    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }

    private static void insertData(final AppDatabase database,
//                                   final List<PostEntity> posts,
                                   final List<FeaturedEntity> featuredEntities) {
        database.runInTransaction(() -> {
//            database.postDao().insertAll(posts);
//            Log.d("DATABASE", "inserted all" + String.valueOf(posts.size()));
            database.featuredDao().insertAll(featuredEntities);
        });
    }

private static void insertWithExecutor(final AppDatabase database, final List<PostEntity> posts){


    // Call
    database.postDao().insertAll(posts);
    Log.d("TAG", "inserted all" + String.valueOf(posts.size()));

    Executor executor = Executors.newSingleThreadExecutor();

    executor.execute(new Runnable() {
        @Override
        public void run() {

//            List<PostEntity> myOrders = database.postDao().loadAllPosts();
//            Log.d("TAG", "Orders nr = " + myOrders.size());
        }
    });


}


    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }
}
