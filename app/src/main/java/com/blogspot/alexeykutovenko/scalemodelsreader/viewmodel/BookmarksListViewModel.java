package com.blogspot.alexeykutovenko.scalemodelsreader.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.annotation.NonNull;

import com.blogspot.alexeykutovenko.scalemodelsreader.AppExecutors;
import com.blogspot.alexeykutovenko.scalemodelsreader.DataRepository;
import com.blogspot.alexeykutovenko.scalemodelsreader.MyApp;
import com.blogspot.alexeykutovenko.scalemodelsreader.db.dao.PostDao;
import com.blogspot.alexeykutovenko.scalemodelsreader.model.PostEntity;
import com.blogspot.alexeykutovenko.scalemodelsreader.model.Post;

import java.util.List;

public class BookmarksListViewModel extends AndroidViewModel {
    private final MediatorLiveData<List<PostEntity>> observableBookmarks;
    private PostDao postDao;

    @SuppressLint("VisibleForTests")
    public BookmarksListViewModel(@NonNull Application application) {
        super(application);
        postDao = ((MyApp) application).getDatabase().postDao();

        observableBookmarks = new MediatorLiveData<>();
        observableBookmarks.setValue(null);
        LiveData<List<PostEntity>> allBookmarks = ((MyApp) application)
                .getRepository()
                .getAllBookmarks();


        observableBookmarks.addSource(allBookmarks, observableBookmarks::setValue);

        DataRepository dataRepository = ((MyApp) application).getRepository();

        MutableLiveData<String> filteredBookmarks = new MutableLiveData<>();
        LiveData<List<PostEntity>> searchedBookmarks = Transformations.switchMap(filteredBookmarks,
                dataRepository::filterBookmarks);
    }


    public void setFilter(String filter) {
        new AppExecutors().diskIO().execute(() ->
                postDao.loadFilteredBookmarks(filter));
    }

    public LiveData<List<PostEntity>> getBookmarks() {
        return observableBookmarks;
    }

    public void updatePost(Post post) {new AppExecutors().diskIO().execute(() ->
            postDao.updatePost(post.getId(), post.getIsBookmark()));
    }
}