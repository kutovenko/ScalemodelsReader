package com.blogspot.alexeykutovenko.scalemodelsreader.viewmodel;

import android.app.Application;

import com.blogspot.alexeykutovenko.scalemodelsreader.MyApp;
import com.blogspot.alexeykutovenko.scalemodelsreader.db.entity.Category;
import com.blogspot.alexeykutovenko.scalemodelsreader.model.PostEntity;
import com.blogspot.alexeykutovenko.scalemodelsreader.network.GetValueCallback;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

public class NewsListViewModel extends AndroidViewModel {

    private final MediatorLiveData<List<PostEntity>> observableFeatureds;
    private final MediatorLiveData<List<PostEntity>> observableNews;
    private MutableLiveData<Set<Category>> liveCategoryTrigger;
    private MyApp application;

    NewsListViewModel(@NonNull Application application) {
        super(application);
        this.application = (MyApp) application;
        observableFeatureds = new MediatorLiveData<>();
        observableFeatureds.setValue(null);
        LiveData<List<PostEntity>> featureds = ((MyApp) application)
                .getRepository()
                .getAllFeaturedPosts();
        observableFeatureds.addSource(featureds, observableFeatureds::setValue);

        liveCategoryTrigger = new MutableLiveData<>();
        liveCategoryTrigger.setValue(loadCategories());

        observableNews = new MediatorLiveData<>();
        observableNews.setValue(null);
        if (Objects.requireNonNull(liveCategoryTrigger.getValue()).size() == 0) {
            LiveData<List<PostEntity>> news = Transformations.switchMap(liveCategoryTrigger, choosedCategories ->
                    ((MyApp) application)
                            .getRepository()
                            .getLivedataForNews());
            observableNews.addSource(news, observableNews::setValue);
        } else {
            LiveData<List<PostEntity>> news = Transformations.switchMap(liveCategoryTrigger, choosedCategories ->
                    ((MyApp) application)
                            .getRepository()
                            .getLivedataForCategory(choosedCategories));
            observableNews.addSource(news, observableNews::setValue);
        }
    }

    /**
     * Updates categories list and triggers switching data source in News LiveData
     * @param categories set of categories codes.
     */
    public void setCategory(Set<Category> categories) {
        liveCategoryTrigger.setValue(categories);
    }

    /**
     * Expose the LiveData Featured query so the UI can observe it.
     */
    public Set<Category> loadCategories() {
        return new TreeSet<>(application.getRepository().getCategories());
    }

    public MutableLiveData<Set<Category>> getLiveCategories() {
        return liveCategoryTrigger;
    }

    public LiveData<List<PostEntity>> getNews() {
        return observableNews;
    }

    public LiveData<List<PostEntity>> getFeatureds() {
        return observableFeatureds;
    }

    public void refreshFeatured() {
        application.getRepository().getScalemodelsFeatured();
    }

    public void refreshPosts() {
        application.getRepository().getScalemodelsPosts(getApplication().getApplicationContext(), new GetValueCallback() {
            @Override
            public void onSuccess(@NonNull int value) {

            }

            @Override
            public void onError(@NonNull Throwable throwable) {

            }
        });
    }
}