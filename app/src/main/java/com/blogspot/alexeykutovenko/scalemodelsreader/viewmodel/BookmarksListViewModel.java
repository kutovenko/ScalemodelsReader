package com.blogspot.alexeykutovenko.scalemodelsreader.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.annotation.NonNull;

import com.blogspot.alexeykutovenko.scalemodelsreader.AppExecutors;
import com.blogspot.alexeykutovenko.scalemodelsreader.DataRepository;
import com.blogspot.alexeykutovenko.scalemodelsreader.ScalemodelsApp;
import com.blogspot.alexeykutovenko.scalemodelsreader.db.dao.PostDao;
import com.blogspot.alexeykutovenko.scalemodelsreader.model.PostEntity;
import com.blogspot.alexeykutovenko.scalemodelsreader.model.Post;

import java.util.List;

public class BookmarksListViewModel extends AndroidViewModel {
    private final MediatorLiveData<List<PostEntity>> mObservableBookmarks; // == filteredData
    private LiveData<List<PostEntity>> mAllBookmarks;

    private PostDao postDao;
    ////

    private LiveData<List<PostEntity>> mSearchByLiveData;
    private MutableLiveData<String> mFilteredLiveData = new MutableLiveData<>();

    public BookmarksListViewModel(@NonNull Application application) {
        super(application);
        postDao = ((ScalemodelsApp) application).getDatabase().postDao();

        mObservableBookmarks = new MediatorLiveData<>();
        mObservableBookmarks.setValue(null);
        LiveData<List<PostEntity>> allBookmarks = ((ScalemodelsApp) application)
                .getRepository()
                .getAllBookmarks();


        mObservableBookmarks.addSource(allBookmarks, mObservableBookmarks::setValue);

        DataRepository dataRepository = ((ScalemodelsApp) application)
                .getRepository();
        mAllBookmarks = ((ScalemodelsApp) application)
                .getRepository()
                .getAllBookmarks();

        mSearchByLiveData = Transformations.switchMap(mFilteredLiveData, //?
                v -> dataRepository.filterBookmarks(v));

    }


    public LiveData<List<PostEntity>> getSearchBy() {
        return mSearchByLiveData;
    }

    public void setFilter(String filter) {
        new AppExecutors().diskIO().execute(() ->
                postDao.filterBookmarks(filter));
    }

    LiveData<List<PostEntity>> getAllBookmarks() {
        return mAllBookmarks;
    }


    public LiveData<List<PostEntity>> getBookmarks() {
        return mObservableBookmarks;
    }

    public void updatePost(Post post) {new AppExecutors().diskIO().execute(() ->
            postDao.updatePost(post.getId(), post.getIsBookmark()));
    }

    public void sortByDate() {
    }

//
//    public void removeBookmark(long id) {new AppExecutors().diskIO().execute(() ->
//            postDao.updateBookmark(id, false));
//    }
//
//    public void setBookmark(long id) {new AppExecutors().diskIO().execute(() ->
//            postDao.updateBookmark(id, true));
//    }
}
