package com.geek.android3_3.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.geek.android3_3.data.models.Post;
import com.geek.android3_3.databinding.ActivityFormBinding;

public class FormActivity extends AppCompatActivity {
    private ActivityFormBinding binding;
    private Post post;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getIntent() != null) {
            binding.etTitle.setText(getIntent().getStringExtra("updateTitle"));
            binding.etContent.setText(getIntent().getStringExtra("updateContent"));
            binding.etUser.setText(getIntent().getStringExtra("updateUser"));
            binding.etGroup.setText(getIntent().getStringExtra("updateGroup"));
        }


        binding.btnSave.setOnClickListener(v -> {
            Intent i = new Intent();
            i.putExtra("title", binding.etTitle.getText().toString());
            i.putExtra("content", binding.etContent.getText().toString());
            i.putExtra("user", binding.etUser.getText().toString());
            i.putExtra("group", binding.etGroup.getText().toString());
            setResult(RESULT_OK, i);
            finish();

        });
    }
}