package com.blogspot.alexeykutovenko.scalemodelsreader.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import com.blogspot.alexeykutovenko.scalemodelsreader.AppExecutors;
import com.blogspot.alexeykutovenko.scalemodelsreader.ScalemodelsApp;
import com.blogspot.alexeykutovenko.scalemodelsreader.db.dao.PostDao;
import com.blogspot.alexeykutovenko.scalemodelsreader.db.entity.PostEntity;
import com.blogspot.alexeykutovenko.scalemodelsreader.model.Post;

import java.util.List;

public class BookmarksListViewModel extends AndroidViewModel {
    private final MediatorLiveData<List<PostEntity>> mObservableBookmark;
    private PostDao postDao;

    public BookmarksListViewModel(@NonNull Application application) {
        super(application);
        postDao = ((ScalemodelsApp) application).getDatabase().postDao();
        mObservableBookmark = new MediatorLiveData<>();

        mObservableBookmark.setValue(null);
        LiveData<List<PostEntity>> favoritePosts = ((ScalemodelsApp) application)
                .getRepository()
                .getAllBookmarks();

        mObservableBookmark.addSource(favoritePosts, mObservableBookmark::setValue);
    }

    public LiveData<List<PostEntity>> getFavoritePosts() {
        return mObservableBookmark;
    }

    public void updatePost(Post post) {new AppExecutors().diskIO().execute(() ->
            postDao.updatePost(post.getId(), post.getIsBookmark()));
    }

    //new not w
    public void removeBookmark(long id) {new AppExecutors().diskIO().execute(() ->
            postDao.updateBookmark(id, false));
    }

    public void setBookmark(long id) {new AppExecutors().diskIO().execute(() ->
            postDao.updateBookmark(id, true));
    }
}
