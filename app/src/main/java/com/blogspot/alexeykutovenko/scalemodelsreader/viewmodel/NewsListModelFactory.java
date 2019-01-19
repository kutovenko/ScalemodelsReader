package com.blogspot.alexeykutovenko.scalemodelsreader.viewmodel;

import android.app.Application;

import com.blogspot.alexeykutovenko.scalemodelsreader.db.entity.Category;

import java.util.Set;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class NewsListModelFactory implements ViewModelProvider.Factory {
    private Application application;

    public NewsListModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new NewsListViewModel(application);
    }
}
