package com.geek.android3_3.data.remote;

import com.geek.android3_3.data.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface HerokuApi {

    @GET("/posts")
    Call<List<Post>> getPosts();

    @POST("/posts/")
    Call<Post> createPost(
            @Body Post pos
    );

    @DELETE("{postId}")
    Call<Post> deletePost(
            @Path("postId") String id
    );


    @PUT("{postId}")
    Call<Post> updatePost(
        @Path("postId") String id,
        @Body Post post
    );
}
