package com.blogspot.alexeykutovenko.scalemodelsreader;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.paging.DataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.blogspot.alexeykutovenko.scalemodelsreader.db.AppDatabase;
import com.blogspot.alexeykutovenko.scalemodelsreader.db.entity.FeaturedEntity;
import com.blogspot.alexeykutovenko.scalemodelsreader.db.entity.PostEntity;
import com.blogspot.alexeykutovenko.scalemodelsreader.network.ScalemodelsApi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    private MediatorLiveData<List<FeaturedEntity>> mObservableFeatureds;

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
        //new Favorites
        mObservableBookmarks = new MediatorLiveData<>();
        mObservableBookmarks.addSource(mDatabase.postDao().loadAllFavoritePosts(true),
                postEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableBookmarks.postValue(postEntities);
                    }
                });

        //new Featured items
        mObservableFeatureds = new MediatorLiveData<>();
        mObservableFeatureds.addSource(mDatabase.featuredDao().loadAllBookmarks(),
                featuredEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableFeatureds.postValue(featuredEntities);
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

    public LiveData<PostEntity> loadPost(final int postId) {
        return mDatabase.postDao().loadPost(postId);
    }

    /**
     * Get the list of favorites posts and get notified of its changes
     */
    public LiveData<List<PostEntity>> getAllBookmarks() {
        return mObservableBookmarks;
    }

    public LiveData<PostEntity> loadBookmark(final int postId) {
        return mDatabase.postDao().loadFavoritePost(postId, true);
    }

    /**
     * Get the list of featured posts and get notified of its changes
     */
    public LiveData<List<FeaturedEntity>> getAllFeaturedPosts() {
        return mObservableFeatureds;
    }

    public LiveData<FeaturedEntity> loadFeaturedPost(final int featuredId) {
        return mDatabase.featuredDao().loadFeatured(featuredId);
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
    public void getScalemodelsUpdates(){
        Log.d("DATABASE R connect", " Repo get updates entered");
        Call<List<PostEntity>> call = mScalemodelsApi.getScalemodelsPosts();
        call.enqueue(new Callback<List<PostEntity>>() {
            @Override
            public void onResponse(@NonNull Call<List<PostEntity>> call,
                                   @NonNull Response<List<PostEntity>> response) {
                Set<PostEntity> onlineSet = new HashSet<>(response.body());
                Log.d("DATABASE R onlineSet ", String.valueOf(onlineSet.size()));

                Executor executor = Executors.newSingleThreadExecutor();
                executor.execute(() -> {
                    final List<PostEntity> dbList = mDatabase.postDao().getAllPosts();
                    Set<PostEntity> dbSet = new HashSet<>(dbList);
                    Log.d("DATABASE R dbSet", String.valueOf(dbSet.size()));
                    ArrayList<PostEntity> uniqueList = new ArrayList<>();

                    HashSet<String> bIds = new HashSet<>(dbSet.size());
                    for (PostEntity b : dbSet){
                        bIds.add(b.getStoryid());

                    }
                    Log.d("DATABASE R bids ", String.valueOf(bIds.size()));

                    for (PostEntity a : response.body()) {
                        if (!bIds.contains(a.getStoryid())){
                            uniqueList.add(a);
                        }
                    }
                    Log.d("DATABASE R unique ", String.valueOf(uniqueList.size()));

                    mDatabase.postDao().insertAll(uniqueList);
                    Log.d("DATABASE R inserted", String.valueOf(uniqueList.size()));
                });
            }

            @Override
            public void onFailure(@NonNull Call<List<PostEntity>> call, @NonNull Throwable t) {
                Log.e("Retrofit Error", t.getMessage());
            }
        });
    }


    public void getScalemodelsFeatured(){
        Call<List<FeaturedEntity>> call = mScalemodelsApi.getScalemodelsFeaturedPosts();
        call.enqueue(new Callback<List<FeaturedEntity>>() {
            @Override
            public void onResponse(@NonNull Call<List<FeaturedEntity>> call,
                                   @NonNull Response<List<FeaturedEntity>> response) {
                Executor executor = Executors.newSingleThreadExecutor();
                executor.execute(() -> {
                    mDatabase.featuredDao().deleteAllFeatured();
                    mDatabase.featuredDao().insertAll(response.body());
                });
            }

            @Override
            public void onFailure(@NonNull Call<List<FeaturedEntity>> call, @NonNull Throwable t) {
                Log.e("Retrofit Featured Error", t.getMessage());
            }
        });
    }
}
