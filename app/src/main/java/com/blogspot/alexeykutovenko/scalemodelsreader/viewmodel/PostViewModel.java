package com.blogspot.alexeykutovenko.scalemodelsreader.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.blogspot.alexeykutovenko.scalemodelsreader.ScalemodelsApp;
import com.blogspot.alexeykutovenko.scalemodelsreader.DataRepository;
import com.blogspot.alexeykutovenko.scalemodelsreader.db.entity.PostEntity;

public class PostViewModel extends AndroidViewModel {
    private final LiveData<PostEntity> mObservablePost;
private DataRepository repository;
    public ObservableField<PostEntity> post = new ObservableField<>();

    private final int mPostId;

    public PostViewModel(@NonNull Application application, DataRepository repository,
                         final int postId) {
        super(application);
        mPostId = postId;
        mObservablePost = repository.loadPost(mPostId);
        this.repository = repository;
    }

    public LiveData<PostEntity> getObservablePost() {
        return mObservablePost;
    }
    public void setPost(PostEntity post) {
        this.post.set(post);
    }
//    public void updatePost(){
//        repository.updatePost(this.post.get());
//    }

    /**
     * A creator is used to inject the product ID into the ViewModel
     * <p>
     * This creator is to showcase how to inject dependencies into ViewModels. It's not
     * actually necessary in this case, as the product ID can be passed in a public method.
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        // TODO: 05.10.2018 do i need this?
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
