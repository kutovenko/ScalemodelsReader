package com.blogspot.alexeykutovenko.scalemodelsreader.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;

import com.blogspot.alexeykutovenko.scalemodelsreader.AppExecutors;
import com.blogspot.alexeykutovenko.scalemodelsreader.DataRepository;
import com.blogspot.alexeykutovenko.scalemodelsreader.MyApp;
import com.blogspot.alexeykutovenko.scalemodelsreader.db.dao.PostDao;
import com.blogspot.alexeykutovenko.scalemodelsreader.model.Post;
import com.blogspot.alexeykutovenko.scalemodelsreader.model.PostEntity;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class PostViewModel extends AndroidViewModel {
    public ObservableField<PostEntity> post = new ObservableField<>();
    private LiveData<PostEntity> observablePost;

    private PostViewModel(@NonNull Application application, DataRepository repository,
                          final long postId) {
        super(application);
        observablePost = repository.loadPost(postId);
    }

    public LiveData<PostEntity> getObservablePost() {
        return observablePost;
    }

    public void setPost(PostEntity post) {
        this.post.set(post);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application application;
        private final long postId;

        private final DataRepository repository;

        public Factory(@NonNull Application application, long postId) {
            this.application = application;
            this.postId = postId;
            repository = ((MyApp) application).getRepository();
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new PostViewModel(application, repository, postId);
        }
    }

    public void updatePost(Post post) {
        @SuppressLint("VisibleForTests") PostDao postDao = ((MyApp) getApplication()).getDatabase().postDao();
        new AppExecutors().diskIO().execute(() ->
                postDao.updatePost(post.getId(), post.getIsBookmark()));
    }
}
