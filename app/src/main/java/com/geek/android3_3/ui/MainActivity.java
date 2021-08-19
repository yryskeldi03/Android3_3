package com.geek.android3_3.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.geek.android3_3.adapters.PostAdapter;
import com.geek.android3_3.data.models.Post;
import com.geek.android3_3.data.remote.RetrofitStorage;
import com.geek.android3_3.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity implements PostAdapter.OnItemClickListener {
    //region values
    private ActivityMainBinding binding;
    private PostAdapter adapter;
    private Post post;
    private String id;
    private boolean isChange = false;
    private int position;

    //endregion
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        adapter = new PostAdapter();
        adapter.setOnItemClickListener(this);

        new RetrofitStorage().getPosts(new RetrofitStorage.MyCallback() {
            @Override
            public void success(List<Post> posts) {
                adapter.setList(posts);
                binding.rv.setAdapter(adapter);
            }

            @Override
            public void failure(String msg) {
                Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

        addPost();
    }

    private void addPost() {
        binding.fab.setOnClickListener(v -> {
            startActivityForResult(new Intent(this, FormActivity.class), 100);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data != null && resultCode == RESULT_OK) {
            post = new Post();
            post.setTitle(data.getStringExtra("title"));
            post.setContent(data.getStringExtra("content"));
            post.setUser(Integer.parseInt(data.getStringExtra("user")));
            post.setGroup(Integer.parseInt(data.getStringExtra("group")));
            new RetrofitStorage().addPost(post, new RetrofitStorage.FormCallback() {
                @Override
                public void success(Post post) {
                    adapter.addItem(post);
                }

                @Override
                public void failure(String msg) {
                    Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            });
        }
        if (requestCode == 200 && data != null){
            post = adapter.getItem(position);
            post.setTitle(data.getStringExtra("title"));
            post.setContent(data.getStringExtra("content"));
            post.setUser(Integer.parseInt(data.getStringExtra("user")));
            post.setGroup(Integer.parseInt(data.getStringExtra("group")));
            new RetrofitStorage().updatePost(post.getId(), post, new RetrofitStorage.UpdateCallback() {
                @Override
                public void success(String id, Post post) {
                    adapter.updatePost(post, position);
                }

                @Override
                public void failure(String msg) {

                }
            });
        }

    }

    @Override
    public void onLongClick(String id, int position) {
        new AlertDialog.Builder(this)
                .setTitle("Удаление")
                .setMessage("Вы действительно хотите удалить эту запись?")
                .setNegativeButton("Нет", null)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new RetrofitStorage().deletePost(id, new RetrofitStorage.DeleteCallback() {
                            @Override
                            public void success(Post post) {
                                adapter.deletePost(position);
                            }

                            @Override
                            public void failure(String msg) {
                                Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).show();
    }

    @Override
    public void onClick(Post post, int position) {
        this.position = position;
        Intent intent = new Intent(this, FormActivity.class);
        intent.putExtra("updateTitle", post.getTitle());
        intent.putExtra("updateContent", post.getContent());
        intent.putExtra("updateUser", String.valueOf(post.getUser()));
        intent.putExtra("updateGroup", String.valueOf(post.getGroup()));
        startActivityForResult(intent, 200);
    }
}