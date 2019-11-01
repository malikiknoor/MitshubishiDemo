package com.iknoortech.mitshubishidemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.iknoortech.mitshubishidemo.R;
import com.iknoortech.mitshubishidemo.utils.ZoomImageView;

public class ImageViewActivity extends AppCompatActivity {

    private ZoomImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        imageView2 = findViewById(R.id.imageView2);

        Glide.with(this).load(getIntent().getStringExtra("imageUrl")).into(imageView2);
    }
}
