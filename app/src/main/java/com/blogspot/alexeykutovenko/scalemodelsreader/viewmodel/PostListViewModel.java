package com.blogspot.alexeykutovenko.scalemodelsreader.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.util.Log;

import com.blogspot.alexeykutovenko.scalemodelsreader.AppExecutors;
import com.blogspot.alexeykutovenko.scalemodelsreader.ScalemodelsApp;
import com.blogspot.alexeykutovenko.scalemodelsreader.db.dao.PostDao;
import com.blogspot.alexeykutovenko.scalemodelsreader.db.entity.FeaturedEntity;
import com.blogspot.alexeykutovenko.scalemodelsreader.db.entity.PostEntity;
import com.blogspot.alexeykutovenko.scalemodelsreader.model.Post;

import java.util.List;

public class PostListViewModel extends AndroidViewModel {
    private final MediatorLiveData<List<PostEntity>> mObservablePosts;
    private final MediatorLiveData<List<FeaturedEntity>> mObservableFeatured;
    private PostDao postDao;

    ScalemodelsApp application;
    private LiveData<PagedList<PostEntity>> articleLiveData;

    public PostListViewModel(@NonNull Application application) {
        super(application);
        this.application = (ScalemodelsApp) application;

        postDao = ((ScalemodelsApp) application)
                .getDatabase().postDao();

        mObservablePosts = new MediatorLiveData<>();
        mObservablePosts.setValue(null);

        LiveData<List<PostEntity>> posts = ((ScalemodelsApp) application)
                .getRepository()
                .getPosts();
        mObservablePosts.addSource(posts, mObservablePosts::setValue);

        mObservableFeatured = new MediatorLiveData<>();
        mObservableFeatured.setValue(null);

        LiveData<List<FeaturedEntity>> featureds = ((ScalemodelsApp) application)
                .getRepository()
                .getAllFeaturedPosts();
        mObservableFeatured.addSource(featureds, mObservableFeatured::setValue);

    }

    /**
     * Expose the LiveData Posts query so the UI can observe it.
     */
    public LiveData<List<PostEntity>> getPosts() {
        return mObservablePosts;
    }

    /**
     * Expose the LiveData Featured query so the UI can observe it.
     */
    public LiveData<List<FeaturedEntity>> getFeaturedEntities() {
        return mObservableFeatured;
    }


    public void updatePost(Post post) {
        new AppExecutors().diskIO().execute(() ->
            postDao.updatePost(post.getId(), post.getIsBookmark()));
    }

    public void refreshFeatured() {
        application.getRepository().getScalemodelsFeatured();
        getFeaturedEntities();
    }

    public void refreshPosts() {
        application.getRepository().getScalemodelsUpdates();
        Log.d("DATABASE R repo", " Connect");

//        mObservablePosts.postValue(application.getRepository().getScalemodelsUpdates());
        getPosts();
    }
}