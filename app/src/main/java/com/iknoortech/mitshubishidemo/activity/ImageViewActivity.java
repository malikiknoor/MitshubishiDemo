package com.iknoortech.mitshubishidemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.iknoortech.mitshubishidemo.R;
import com.iknoortech.mitshubishidemo.utils.ZoomImageView;

public class ImageViewActivity extends AppCompatActivity {

    private ZoomImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        imageView = findViewById(R.id.imageView2);
        Glide.with(this).load(getIntent().getStringExtra("imageUrl")).into(imageView);
    }
}
