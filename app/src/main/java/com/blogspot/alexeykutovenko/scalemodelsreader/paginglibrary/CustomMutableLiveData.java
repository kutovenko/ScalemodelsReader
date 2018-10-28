package com.blogspot.alexeykutovenko.scalemodelsreader.paginglibrary;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.databinding.BaseObservable;
import androidx.databinding.Observable;

public class CustomMutableLiveData <T extends BaseObservable> extends MediatorLiveData<T> {
    @Override
    public void setValue(T value) {
        super.setValue(value);
        //listen to property changes
        value.addOnPropertyChangedCallback(callback);
    }

    Observable.OnPropertyChangedCallback callback = new Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(Observable sender, int propertyId) {
            //Trigger LiveData observer on change of any property in object
            setValue(getValue());
        }
    };
}