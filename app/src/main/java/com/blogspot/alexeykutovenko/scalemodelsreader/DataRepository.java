package com.blogspot.alexeykutovenko.scalemodelsreader;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.paging.DataSource;
import androidx.annotation.NonNull;
import android.util.Log;

import com.blogspot.alexeykutovenko.scalemodelsreader.db.AppDatabase;
import com.blogspot.alexeykutovenko.scalemodelsreader.db.entity.Category;
import com.blogspot.alexeykutovenko.scalemodelsreader.model.FeaturedEntity;
import com.blogspot.alexeykutovenko.scalemodelsreader.model.PostEntity;
import com.blogspot.alexeykutovenko.scalemodelsreader.network.ScalemodelsApi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepository {
    private static DataRepository sInstance;

    private final AppDatabase mDatabase;
    private final ScalemodelsApi mScalemodelsApi;
    private MediatorLiveData<List<PostEntity>> mObservablePosts;
    private MediatorLiveData<List<PostEntity>> mObservableBookmarks; //new
    private MediatorLiveData<List<PostEntity>> mObservableFeatureds;

    private MediatorLiveData<List<PostEntity>> mObservableScalemodelsPosts;

    private DataRepository(final AppDatabase database, final ScalemodelsApi scalemodelsApi) {
        mDatabase = database;
        mScalemodelsApi = scalemodelsApi;

        mObservablePosts = new MediatorLiveData<>();
        mObservablePosts.addSource(mDatabase.postDao().loadAllPosts(),
                postEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservablePosts.postValue(postEntities);
                    }
                });

        mObservableBookmarks = new MediatorLiveData<>();
        mObservableBookmarks.addSource(mDatabase.postDao().loadAllBookmarks("date DESC"),
                postEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableBookmarks.postValue(postEntities);
                    }
                });

        mObservableFeatureds = new MediatorLiveData<>();
        mObservableFeatureds.addSource(mDatabase.postDao().loadAllFeatureds(),
                postEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableFeatureds.postValue(postEntities);
                    }
                });
    }

    public static DataRepository getInstance(final AppDatabase database, final ScalemodelsApi scalemodelsApi) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database, scalemodelsApi);
                }
            }
        }
        return sInstance;
    }

    /**
     * Get the list of posts from the database and get notified when the data changes.
     */
    public LiveData<List<PostEntity>> getPosts() {
        return mObservablePosts;
    }

    public LiveData<List<PostEntity>> getAllBookmarks() {
        return mObservableBookmarks;
    }

    public LiveData<List<PostEntity>> getAllFeaturedPosts() {
        return mObservableFeatureds;
    }


    public LiveData<List<PostEntity>> filterBookmarks(String searchQuery) {
        return mDatabase.postDao().filterBookmarks(searchQuery);
    }

    public LiveData<PostEntity> loadPost(final int postId) {
        return mDatabase.postDao().loadPost(postId);
    }

    public LiveData<PostEntity> loadBookmark(final int postId) {
        return mDatabase.postDao().loadBookmark(postId);
    }

    public LiveData<PostEntity> loadFeaturedPost(final int featuredId) {
        return mDatabase.postDao().loadFeatured(featuredId);
    }

    /**
     * Data from database with paging
     * @return Factory for paged posts
     */
    public DataSource.Factory<Integer, PostEntity> getPagedPostsFactory() {
        return mDatabase.postDao().getPagedPosts();
    }

    /**
     * Gets unique new posts (by story id) from Scalemodels in reverse chronological order.
     * Updates local database.
     */
    public void getScalemodelsPosts() {
        Log.d("DATABASE R connect", " Repo get updates entered");
        Call<List<PostEntity>> call = mScalemodelsApi.getScalemodelsPosts();
        call.enqueue(new Callback<List<PostEntity>>() {
            @Override
            public void onResponse(@NonNull Call<List<PostEntity>> call,
                                   @NonNull Response<List<PostEntity>> response) {
                Executor executor = Executors.newSingleThreadExecutor();
                executor.execute(() -> {
                    final List<PostEntity> dbList = mDatabase.postDao().getAllPosts();
                    ArrayList<PostEntity> uniqueList = new ArrayList<>();
                    HashSet<String> localIds = new HashSet<>(dbList.size());
                    for (PostEntity entity : dbList) {
                        localIds.add(entity.getStoryid());
                    }

                    for (PostEntity entity : response.body()) {
                        if (!localIds.contains(entity.getStoryid())) {
                            uniqueList.add(entity);
                        }
                    }

                    mDatabase.postDao().insertAll(uniqueList);
                });
            }

            @Override
            public void onFailure(@NonNull Call<List<PostEntity>> call, @NonNull Throwable t) {
                Log.e("Retrofit Error", t.getMessage());
            }
        });
    }

    public void getScalemodelsFeatured(){
        Log.d("FEATURED ", "entered");

        Call<List<FeaturedEntity>> call = mScalemodelsApi.getScalemodelsFeaturedPosts();
        call.enqueue(new Callback<List<FeaturedEntity>>() {
            @Override
            public void onResponse(@NonNull Call<List<FeaturedEntity>> call,
                                   @NonNull Response<List<FeaturedEntity>> response) {
                Log.d("FEATURED responce ", String.valueOf(response.body().size()));
                Executor executor = Executors.newSingleThreadExecutor();
                executor.execute(() -> {
                    mDatabase.postDao().deleteAllFeatureds();
                    mDatabase.postDao().insertAll(convertFeaturedToPost(response.body()));
                    Log.d("FEATURED ", String.valueOf(convertFeaturedToPost(response.body()).size()));
                });
            }

            @Override
            public void onFailure(@NonNull Call<List<FeaturedEntity>> call, @NonNull Throwable t) {
                Log.e("Retrofit Featured Error", t.getMessage());
            }
        });
    }


    private List<PostEntity> convertFeaturedToPost(List<FeaturedEntity> featuredList) {
        List<PostEntity> postList = new ArrayList<>(featuredList.size());
        for (FeaturedEntity featuredEntity : featuredList) {
            PostEntity post = new PostEntity();
            post.setId(featuredEntity.getId());
            post.setAuthor(featuredEntity.getAuthor());
            post.setTitle(featuredEntity.getTitle());
            post.setLastUpdate(featuredEntity.getLastUpdate());
            post.setCategory(new Category());
            post.setOriginalUrl(featuredEntity.getOriginalUrl());
            post.setThumbnailUrl(featuredEntity.getThumbnailUrl());
            post.setPrintingUrl(featuredEntity.getPrintingUrl());
            post.setImagesUrls(featuredEntity.getImagesUrls());
            post.setType(featuredEntity.getType());
            post.setDate(featuredEntity.getDate());
            post.setStoryid(featuredEntity.getStoryid());
            post.setDescription(featuredEntity.getDescription());
            post.setIsBookmark(false);
            post.setIsFeatured(true);
            postList.add(post);
        }
        return postList;
    }
}
