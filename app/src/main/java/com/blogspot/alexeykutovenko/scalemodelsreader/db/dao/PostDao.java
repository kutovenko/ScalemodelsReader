package com.blogspot.alexeykutovenko.scalemodelsreader.db.dao;

import com.blogspot.alexeykutovenko.scalemodelsreader.db.entity.Category;
import com.blogspot.alexeykutovenko.scalemodelsreader.model.PostEntity;

import java.util.List;
import java.util.Set;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

/**
 * DAO class for working with posts, featured posts and bookmarks
 */
@Dao
public interface PostDao {

    //POSTS
    @Query("SELECT * FROM posts WHERE isFeatured = 0 ORDER BY date DESC")
    LiveData<List<PostEntity>> loadAllPosts();

    @Query("SELECT * FROM posts WHERE isFeatured = 0 ORDER BY date DESC")
    List<PostEntity> getAllPosts();


    @Query("SELECT COUNT(*) FROM posts WHERE isFeatured = 0")
    int countNews();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<PostEntity> posts);

    @Query("select * from posts where id = :postId")
    LiveData<PostEntity> loadPost(long postId);

    @Query("SELECT category FROM posts WHERE isFeatured = 0")
    List<Category> getAllCategories();

    @Query("SELECT * FROM posts WHERE isFeatured = 0 AND category IN (:categories) ORDER BY date DESC")
    LiveData<List<PostEntity>> loadFilteredPosts(Set<Category> categories);

//    @Query("SELECT * FROM posts WHERE isFeatured = 0 AND category IN (:categories) ORDER BY date DESC")
//    List<PostEntity> loadFilteredPosts(Set<Category> categories);

    @Query("DELETE FROM posts WHERE isFeatured = 0 AND isBookmark = 0 AND date < :date")
    void deletePostsOlderThen(String date);


    //FEATURED POSTS
    @Query("SELECT * FROM posts WHERE isFeatured = 1")
    LiveData<List<PostEntity>> loadAllFeatureds();

    @Query("DELETE FROM posts WHERE isFeatured = 1")
    void deleteAllFeatureds();


    //BOOKMARKS
    @Query("SELECT * FROM posts WHERE isBookmark = 1 AND isFeatured = 0 ORDER BY date DESC")
    LiveData<List<PostEntity>> loadAllBookmarks();

    @Query("select * from posts where isBookmark = 1 AND title_ru LIKE :searchQuery")
    LiveData<List<PostEntity>> loadFilteredBookmarks(String searchQuery);

    @Query("UPDATE posts SET isBookmark = :value WHERE id = :postId AND isFeatured = 0")
    void updatePost(long postId, boolean value);
}
