package com.blogspot.alexeykutovenko.scalemodelsreader.network;

import com.blogspot.alexeykutovenko.scalemodelsreader.db.entity.FeaturedEntity;
import com.blogspot.alexeykutovenko.scalemodelsreader.db.entity.PostEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ScalemodelsApi {
    @GET("index.php?op=show_json")
    Call<List<PostEntity>> getScalemodelsPosts();

    @GET("index.php?op=show_json&action=featured")
    Call<List<FeaturedEntity>> getScalemodelsFeaturedPosts();
}