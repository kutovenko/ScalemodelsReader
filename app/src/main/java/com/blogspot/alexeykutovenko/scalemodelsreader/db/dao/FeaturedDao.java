package com.blogspot.alexeykutovenko.scalemodelsreader.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.blogspot.alexeykutovenko.scalemodelsreader.model.FeaturedEntity;

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
