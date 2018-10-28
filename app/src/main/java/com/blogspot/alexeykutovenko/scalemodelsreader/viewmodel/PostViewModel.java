package com.blogspot.alexeykutovenko.scalemodelsreader.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.databinding.ObservableField;
import androidx.annotation.NonNull;

import com.blogspot.alexeykutovenko.scalemodelsreader.DataRepository;
import com.blogspot.alexeykutovenko.scalemodelsreader.ScalemodelsApp;
import com.blogspot.alexeykutovenko.scalemodelsreader.model.FeaturedEntity;
import com.blogspot.alexeykutovenko.scalemodelsreader.model.PostEntity;

public class PostViewModel extends AndroidViewModel {
    public ObservableField<PostEntity> post = new ObservableField<>();
    public ObservableField<FeaturedEntity> featured = new ObservableField<>();
    private LiveData<PostEntity> mObservablePost;
    private final int mPostId;

    public PostViewModel(@NonNull Application application, DataRepository repository,
                         final int postId) {
        super(application);
        mPostId = postId;
        mObservablePost = repository.loadPost(mPostId);
    }

    public LiveData<PostEntity> getObservablePost() {
        return mObservablePost;
    }

    public void setPost(PostEntity post) {
        this.post.set(post);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application mApplication;
        private final int mPostId;

        private final DataRepository mRepository;

        public Factory(@NonNull Application application, int postId) {
            mApplication = application;
            mPostId = postId;
            mRepository = ((ScalemodelsApp) application).getRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new PostViewModel(mApplication, mRepository, mPostId);
        }
    }
}
