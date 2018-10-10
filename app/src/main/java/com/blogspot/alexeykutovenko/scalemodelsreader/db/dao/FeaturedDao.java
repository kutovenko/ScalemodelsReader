package com.blogspot.alexeykutovenko.scalemodelsreader.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.blogspot.alexeykutovenko.scalemodelsreader.db.entity.FeaturedEntity;

import java.util.List;

@Dao
public interface FeaturedDao {
    //For all posts
    @Query("SELECT * FROM featured")
    LiveData<List<FeaturedEntity>> loadAllBookmarks();

    @Query("SELECT * FROM featured")
    List<FeaturedEntity> getAllBookmarks();

    @Query("DELETE FROM featured")
    void deleteAllFeatured();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<FeaturedEntity> featureds);

    @Query("select * from featured where id = :featuredId")
    LiveData<FeaturedEntity> loadFeatured(int featuredId);

    @Query("select * from featured where id = :featuredId")
    FeaturedEntity loadFeaturedSync(int featuredId);


}
