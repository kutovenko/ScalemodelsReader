package com.blogspot.alexeykutovenko.scalemodelsreader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.blogspot.alexeykutovenko.scalemodelsreader.db.AppDatabase;
import com.blogspot.alexeykutovenko.scalemodelsreader.db.entity.Category;
import com.blogspot.alexeykutovenko.scalemodelsreader.model.FeaturedEntity;
import com.blogspot.alexeykutovenko.scalemodelsreader.model.PostEntity;
import com.blogspot.alexeykutovenko.scalemodelsreader.network.GetValueCallback;
import com.blogspot.alexeykutovenko.scalemodelsreader.network.ScalemodelsApi;
import com.blogspot.alexeykutovenko.scalemodelsreader.util.MyAppConctants;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataRepository {
    private static DataRepository sInstance;

    private final AppDatabase database;
    private final ScalemodelsApi scalemodelsApi;
    private MediatorLiveData<List<PostEntity>> observablePosts;
    private MediatorLiveData<List<PostEntity>> observableBookmarks;
    private MediatorLiveData<List<PostEntity>> observableFeatureds;

    @SuppressLint("VisibleForTests")
    private DataRepository(final AppDatabase database, final ScalemodelsApi scalemodelsApi) {
        this.database = database;
        this.scalemodelsApi = scalemodelsApi;

        observablePosts = new MediatorLiveData<>();
        observablePosts.addSource(database.postDao().loadAllPosts(),
                postEntities -> {
                    if (this.database.getDatabaseCreated().getValue() != null) {
                        observablePosts.postValue(postEntities);
                    }
                });

        observableBookmarks = new MediatorLiveData<>();
        observableBookmarks.addSource(this.database.postDao().loadAllBookmarks(),
                postEntities -> {
                    if (this.database.getDatabaseCreated().getValue() != null) {
                        observableBookmarks.postValue(postEntities);
                    }
                });

        observableFeatureds = new MediatorLiveData<>();
        observableFeatureds.addSource(this.database.postDao().loadAllFeatureds(),
                postEntities -> {
                    if (this.database.getDatabaseCreated().getValue() != null) {
                        observableFeatureds.postValue(postEntities);
                    }
                });
    }

    static DataRepository getInstance(final AppDatabase database, final ScalemodelsApi scalemodelsApi) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database, scalemodelsApi);
                }
            }
        }
        return sInstance;
    }

    public LiveData<List<PostEntity>> getAllFeaturedPosts() {
        return observableFeatureds;
    }

    public LiveData<List<PostEntity>> getAllBookmarks() {
        return observableBookmarks;
    }

    @SuppressLint("VisibleForTests")
    public LiveData<List<PostEntity>> filterBookmarks(String searchQuery) {
        return database.postDao().loadFilteredBookmarks(searchQuery);
    }

    @SuppressLint("VisibleForTests")
    public LiveData<PostEntity> loadPost(final long postId) {
        return database.postDao().loadPost(postId);
    }

    /**
     * Set number of days to keep posts in database
     *
     * @param days number of days
     */
    @SuppressLint("VisibleForTests")
    public void setNumberOfNews(int days) {
        long period = TimeUnit.DAYS.toMillis(days);
        String dateToDelete = String.valueOf(System.currentTimeMillis() - period);
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> database.postDao().deletePostsOlderThen(dateToDelete));
    }

    /**
     * Gets unique new posts (by story id) from Scalemodels in reverse chronological order.
     * Updates local database.
     */
    public void getScalemodelsPosts(Context context, GetValueCallback callback) {
        Log.d("MyNOTE", "DR getSPostsEntered");
        SharedPreferences preferences = context.getSharedPreferences(MyAppConctants.NEWS_NUMBER, Context.MODE_PRIVATE);
        int finalNumberOfNews = preferences.getInt(MyAppConctants.NEWS_NUMBER, 0);
        Call<List<PostEntity>> call = scalemodelsApi.getScalemodelsPosts();
        call.enqueue(new Callback<List<PostEntity>>() {
            @SuppressLint("VisibleForTests")
            @Override
            public void onResponse(@NonNull Call<List<PostEntity>> call,
                                   @NonNull Response<List<PostEntity>> response) {
                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.execute(() -> {
                    @SuppressLint("VisibleForTests") Set<String> expectedNames = database.postDao()
                            .getAllPosts().stream()
                            .map(PostEntity::getStoryid)
                            .collect(Collectors.toSet());
                    if (expectedNames.size() == 0) {
                        database.postDao().insertAll(response.body());
                        assert response.body() != null;
                        callback.onSuccess(response.body().size());
                    } else {
                        assert response.body() != null;
                        List<PostEntity> uniqueList = response.body().stream()
                                .filter(p -> !expectedNames.contains(p.getStoryid()))
                                .collect(Collectors.toList());
                        database.postDao().insertAll(uniqueList);
                        callback.onSuccess(uniqueList.size());
                    }

                    if (finalNumberOfNews > 0) {
                        //trim database to size.
                        setNumberOfNews(finalNumberOfNews);
                    }
                });
                closeExecutorCorrectly(executor);
            }

            @Override
            public void onFailure(@NonNull Call<List<PostEntity>> call, @NonNull Throwable t) {
                callback.onError(t);
            }
        });
    }

    /**
     * Gets featured posts from Scalemodels.ru. The list automatically updates on server side.
     */
    public void getScalemodelsFeatured(){
        Call<List<FeaturedEntity>> call = scalemodelsApi.getScalemodelsFeaturedPosts();
        call.enqueue(new Callback<List<FeaturedEntity>>() {
            @SuppressLint("VisibleForTests")
            @Override
            public void onResponse(@NonNull Call<List<FeaturedEntity>> call,
                                   @NonNull Response<List<FeaturedEntity>> response) {
                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.execute(() -> {
                    database.postDao().deleteAllFeatureds();
                    assert response.body() != null;
                    database.postDao().insertAll(convertFeaturedToPost(response.body()));
                });

                closeExecutorCorrectly(executor);
            }

            @Override
            public void onFailure(@NonNull Call<List<FeaturedEntity>> call, @NonNull Throwable t) {
                Log.e("Retrofit FeaturedEntity Error", t.getMessage());
            }
        });
    }

    private List<PostEntity> convertFeaturedToPost(List<FeaturedEntity> featuredList) {
        List<PostEntity> postList = new ArrayList<>(featuredList.size());
        for (FeaturedEntity featuredEntity : featuredList) {
            PostEntity post = new PostEntity();
            post.setId(featuredEntity.getId());
            post.setLastUpdate(featuredEntity.getLastUpdate());
            post.setOriginalUrl(featuredEntity.getOriginalUrl());
            post.setThumbnailUrl(featuredEntity.getThumbnailUrl());
            post.setPrintingUrl(featuredEntity.getPrintingUrl());
            post.setImagesUrls(featuredEntity.getImagesUrls());
            post.setType(featuredEntity.getType());
            post.setDate(featuredEntity.getDate());
            post.setStoryid(featuredEntity.getStoryid());

            post.setTitle(featuredEntity.getTitle());
            post.setDescription(featuredEntity.getDescription());
            post.setAuthor(featuredEntity.getAuthor());
            post.setCategory(new Category());

            post.setIsBookmark(false);
            post.setIsFeatured(true);

            postList.add(post);
        }
        return postList;
    }

    @SuppressLint("VisibleForTests")
    public LiveData<List<PostEntity>> getLivedataForCategory(Set<Category> categories) {
        return database.postDao().loadFilteredPosts(categories);
    }

    @SuppressLint("VisibleForTests")
    public LiveData<List<PostEntity>> getLivedataForNews() {
        return database.postDao().loadAllPosts();
    }

    /**
     * Gets list of categories from actual posts in database
     *
     * @return list of actual categories
     */
    public List<Category> getCategories() {
        List<Category>[] cats = new ArrayList[]{new ArrayList<>()};

        @SuppressLint("VisibleForTests")
        Callable<List<Category>> task = () -> database.postDao().getAllCategories();

        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<List<Category>> future = executor.submit(task);

        closeExecutorCorrectly(executor);

        try {
            cats[0] = future.get(3, TimeUnit.SECONDS);
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            Log.e("DR", "getCategories returned " + e.getMessage());
        }
        return cats[0];
    }

    private void closeExecutorCorrectly(ExecutorService executor) {
        try {
            Log.e("DR", "attempt to shutdown executor");
            executor.shutdown();
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Log.e("DR", "tasks interrupted");
        } finally {
            if (!executor.isTerminated()) {
                Log.e("DR", "cancel non-finished tasks");
            }
            executor.shutdownNow();
            Log.e("DR", "shutdown finished");
        }
    }
}