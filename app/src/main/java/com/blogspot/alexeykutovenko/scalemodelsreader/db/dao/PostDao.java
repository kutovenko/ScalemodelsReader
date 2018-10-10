package com.blogspot.alexeykutovenko.scalemodelsreader.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.blogspot.alexeykutovenko.scalemodelsreader.db.entity.PostEntity;

import java.util.List;

@Dao
public interface PostDao {
    //For all posts
    @Query("SELECT * FROM posts")
    LiveData<List<PostEntity>> loadAllPosts();

    @Query("SELECT * FROM posts")
    List<PostEntity> getAllPosts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<PostEntity> posts);

    @Query("select * from posts where id = :postId")
    LiveData<PostEntity> loadPost(int postId);

    @Query("select * from posts where id = :postId")
    PostEntity loadPostSync(int postId);

    @Query("SELECT * FROM posts")
    DataSource.Factory<Integer, PostEntity> getPagedPosts();


    // For favorite posts
    @Query("SELECT * FROM posts WHERE isBookmark = :isBookmark")
    LiveData<List<PostEntity>> loadAllFavoritePosts(boolean isBookmark);

    @Query("select * from posts where id = :postId AND isBookmark = :isBookmark")
    LiveData<PostEntity> loadFavoritePost(int postId, boolean isBookmark);


    @Query ("UPDATE posts SET isBookmark = :value WHERE id = :postId")
    void updatePost(int postId, boolean value);

    @Query ("UPDATE posts SET isBookmark = :b WHERE id = :id")
    void updateBookmark(long id, boolean b);

}
