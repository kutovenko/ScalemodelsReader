package com.blogspot.alexeykutovenko.scalemodelsreader;

import android.app.Application;

import com.blogspot.alexeykutovenko.scalemodelsreader.db.AppDatabase;
import com.blogspot.alexeykutovenko.scalemodelsreader.network.RestApiFactory;
import com.blogspot.alexeykutovenko.scalemodelsreader.network.ScalemodelsApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Android Application class. Used for accessing singletons.
 */
public class ScalemodelsApp extends Application {

    private AppExecutors mAppExecutors;
    private ScalemodelsApi scalemodelsApi;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this, mAppExecutors);
    }

    public DataRepository getRepository() {
        return DataRepository.getInstance(getDatabase(), getScalemodelsApi());
    }
//retrofit
    public ScalemodelsApi getScalemodelsApi(){
        if(scalemodelsApi == null) {
            scalemodelsApi = RestApiFactory.create();
        }
        return scalemodelsApi;
    }
}
