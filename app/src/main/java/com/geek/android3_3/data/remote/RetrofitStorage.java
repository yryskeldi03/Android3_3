package com.geek.android3_3.data.remote;

import com.geek.android3_3.data.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitStorage {

    public void getPosts(MyCallback callback) {
        RetrofitBuilder.getInstance().getPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.success(response.body());
                }else {
                    callback.failure(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                callback.failure(t.getLocalizedMessage());
            }
        });
    }

    public void addPost(Post post,FormCallback callback){
        RetrofitBuilder.getInstance().createPost(post).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.success(response.body());
                }else {
                    callback.failure(response.message());
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                callback.failure(t.getLocalizedMessage());
            }
        });
    }

    public void deletePost(String id, DeleteCallback callback){
        RetrofitBuilder.getInstance().deletePost(id).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.success(response.body());
                }else {
                    callback.failure(response.message());
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                callback.failure(t.getLocalizedMessage());
            }
        });
    }

    public void updatePost(String id, Post post, UpdateCallback callback) {
        RetrofitBuilder.getInstance().updatePost(id, post).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.success(response.message(), response.body());
                } else {
                    callback.failure(response.message());
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                callback.failure(t.getLocalizedMessage());
            }
        });
    }

    public interface MyCallback {
        void success(List<Post> posts);

        void failure(String msg);
    }

    public interface FormCallback {
        void success(Post post);

        void failure(String msg);
    }

    public interface DeleteCallback{
        void success(Post post);

        void failure(String msg);
    }

    public interface UpdateCallback {
        void success(String id, Post post);

        void failure(String msg);
    }
}
