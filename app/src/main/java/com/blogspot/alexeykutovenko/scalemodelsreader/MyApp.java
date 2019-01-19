package com.blogspot.alexeykutovenko.scalemodelsreader;

import android.app.Application;
import android.content.Context;

import com.blogspot.alexeykutovenko.scalemodelsreader.db.AppDatabase;
import com.blogspot.alexeykutovenko.scalemodelsreader.network.RestApiFactory;
import com.blogspot.alexeykutovenko.scalemodelsreader.network.ScalemodelsApi;

/**
 * Android Application class. Used for accessing singletons.
 */
public class MyApp extends Application {

    private AppExecutors appExecutors;
    private ScalemodelsApi scalemodelsApi;
    private Context context;

    public Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appExecutors = new AppExecutors();
        context = getApplicationContext();
    }

    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this, appExecutors);
    }

    public DataRepository getRepository() {
        return DataRepository.getInstance(getDatabase(), getScalemodelsApi());
    }

    //for Retrofit
    public ScalemodelsApi getScalemodelsApi(){
        if(scalemodelsApi == null) {
            scalemodelsApi = RestApiFactory.create();
        }
        return scalemodelsApi;
    }
}