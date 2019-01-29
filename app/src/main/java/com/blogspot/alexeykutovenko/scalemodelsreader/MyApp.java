package com.blogspot.alexeykutovenko.scalemodelsreader;

import android.app.Application;
import android.content.Context;

import com.blogspot.alexeykutovenko.scalemodelsreader.db.AppDatabase;
import com.blogspot.alexeykutovenko.scalemodelsreader.network.RestApiFactory;
import com.blogspot.alexeykutovenko.scalemodelsreader.network.ScalemodelsApi;

/**
 * Android Application class for accessing singletons.
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

    /**
     * Singleton of the application database.
     *
     * @return database instance.
     */
    public AppDatabase getDatabase() {
        return AppDatabase.getInstance(this, appExecutors);
    }

    /**
     * Singleton of the application data repository.
     *
     * @return instance of DataRepository class.
     */
    public DataRepository getRepository() {
        return DataRepository.getInstance(getDatabase(), getScalemodelsApi());
    }

    /**
     * Singleton of the ScalemodelsAPI (for Retrofit library).
     * @return instance of ScalemodelsApi class.
     */
    public ScalemodelsApi getScalemodelsApi(){
        if(scalemodelsApi == null) {
            scalemodelsApi = RestApiFactory.create();
        }
        return scalemodelsApi;
    }
}