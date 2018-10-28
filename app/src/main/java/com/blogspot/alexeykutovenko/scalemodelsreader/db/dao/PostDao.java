package com.blogspot.alexeykutovenko.scalemodelsreader.db.dao;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.blogspot.alexeykutovenko.scalemodelsreader.db.entity.Category;
import com.blogspot.alexeykutovenko.scalemodelsreader.model.PostEntity;

import java.util.List;

@Dao
public interface PostDao {

    //POSTS
    @Query("SELECT * FROM posts WHERE isFeatured = 0 ORDER BY date DESC")
    LiveData<List<PostEntity>> loadAllPosts();

    @Query("SELECT * FROM posts WHERE isFeatured = 0")
    List<PostEntity> getAllPosts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<PostEntity> posts);

    @Query("select * from posts where id = :postId")
    LiveData<PostEntity> loadPost(int postId);

    @Query("select * from posts where id = :postId")
    PostEntity loadPostSync(int postId);

    @Query("SELECT * FROM posts WHERE isFeatured = 0")
    DataSource.Factory<Integer, PostEntity> getPagedPosts();

    @Query("SELECT category FROM posts WHERE isFeatured = 0")
    List<Category> getAllCategories();

    @Query("SELECT * FROM posts WHERE isFeatured = 0 AND category = :categoryId ORDER BY date DESC")
    LiveData<List<PostEntity>> loadPostsForCategory(String categoryId);
    // TODO: 27.10.2018 plain category

    //FEATURED
    @Query("SELECT * FROM posts WHERE isFeatured = 1")
    LiveData<List<PostEntity>> loadAllFeatureds();

    @Query("SELECT * FROM posts WHERE isFeatured = 1")
    List<PostEntity> getAllFeatureds();

    @Query("DELETE FROM posts WHERE isFeatured = 1")
    void deleteAllFeatureds();

    @Query("select * from posts where isFeatured = 1 AND id = :featuredId")
    LiveData<PostEntity> loadFeatured(int featuredId);

    @Query("select * from posts where isFeatured = 1 AND id = :featuredId")
    PostEntity loadFeaturedSync(int featuredId);

    @Query("select * from posts where isBookmark = 1 AND title_ru LIKE :searchQuery")
    LiveData<List<PostEntity>> filterBookmarks(String searchQuery);


    //BOOKMARKS
    @Query("SELECT * FROM posts WHERE isBookmark = 1 AND isFeatured = 0 ORDER BY :orderBy")
    LiveData<List<PostEntity>> loadAllBookmarks(String orderBy);

    @Query("select * from posts where id = :postId AND isBookmark = 1 AND isFeatured = 0")
    LiveData<PostEntity> loadBookmark(int postId);


    @Query("UPDATE posts SET isBookmark = :value WHERE id = :postId AND isFeatured = 0")
    void updatePost(int postId, boolean value);

    @Query("UPDATE posts SET isBookmark = :b WHERE id = :id AND isFeatured = 0")
    void updateBookmark(long id, boolean b);

}
