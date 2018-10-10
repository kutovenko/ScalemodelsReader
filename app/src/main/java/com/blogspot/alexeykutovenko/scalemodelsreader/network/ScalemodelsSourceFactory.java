package com.blogspot.alexeykutovenko.scalemodelsreader.network;

import android.arch.paging.DataSource;

import com.blogspot.alexeykutovenko.scalemodelsreader.db.entity.PostEntity;

class ScalemodelsSourceFactory extends DataSource.Factory<Integer, PostEntity> {
    @Override
    public DataSource<Integer, PostEntity> create() {
        return null;
    }

//    private final EmployeeStorage employeeStorage;
//
//    MySourceFactory(EmployeeStorage employeeStorage) {
//        this.employeeStorage = employeeStorage;
//    }
//
//    @Override
//    public DataSource create() {
//        return new MyPositionalDataSource(employeeStorage);
//    }
}
