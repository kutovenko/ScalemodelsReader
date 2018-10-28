package com.blogspot.alexeykutovenko.scalemodelsreader.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.annotation.NonNull;
import android.util.Log;

import com.blogspot.alexeykutovenko.scalemodelsreader.AppExecutors;
import com.blogspot.alexeykutovenko.scalemodelsreader.DataRepository;
import com.blogspot.alexeykutovenko.scalemodelsreader.ScalemodelsApp;
import com.blogspot.alexeykutovenko.scalemodelsreader.db.dao.PostDao;
import com.blogspot.alexeykutovenko.scalemodelsreader.model.PostEntity;
import com.blogspot.alexeykutovenko.scalemodelsreader.model.Post;

import java.util.List;

public class NewsListViewModel extends AndroidViewModel {
    private final MediatorLiveData<List<PostEntity>> mObservablePosts;
    private final MediatorLiveData<List<PostEntity>> mObservableFeatured;
    private PostDao postDao;
    private ScalemodelsApp application;

    public NewsListViewModel(@NonNull Application application) {
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

        LiveData<List<PostEntity>> featureds = ((ScalemodelsApp) application)
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
    public LiveData<List<PostEntity>> getFeaturedEntities() {
        return mObservableFeatured;
    }


    public void updatePost(Post post) {
        new AppExecutors().diskIO().execute(() ->
                postDao.updatePost(post.getId(), post.getIsBookmark()));
    }

    public void refreshFeatured() {
        application.getRepository().getScalemodelsFeatured();
    }

    public void refreshPosts() {
        Log.d("DATABASE R repo", " Refresh");
        application.getRepository().getScalemodelsPosts();
    }

    /**
     * Method for paging library
     *
     * @return Data Repository instance.
     */
    public DataRepository getRepository() {
        return application.getRepository();
    }
}