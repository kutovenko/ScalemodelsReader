package com.blogspot.alexeykutovenko.scalemodelsreader.db;

import android.content.Context;

import com.blogspot.alexeykutovenko.scalemodelsreader.AppExecutors;
import com.blogspot.alexeykutovenko.scalemodelsreader.db.converter.DataConverters;
import com.blogspot.alexeykutovenko.scalemodelsreader.db.dao.PostDao;
import com.blogspot.alexeykutovenko.scalemodelsreader.model.FeaturedEntity;
import com.blogspot.alexeykutovenko.scalemodelsreader.model.PostEntity;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {PostEntity.class, FeaturedEntity.class}, version = 1, exportSchema = false)
@TypeConverters(DataConverters.class)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase sInstance;

    @VisibleForTesting
    public abstract PostDao postDao();

    private static final String DATABASE_NAME = "smreader-db";
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

    private static AppDatabase buildDatabase(final Context appContext,
                                             final AppExecutors executors) {
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        executors.diskIO().execute(() -> {
                            AppDatabase database = AppDatabase.getInstance(appContext, executors);
                            database.setDatabaseCreated();
                        });
                    }
                }).build();
    }

    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }
}