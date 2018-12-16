package com.example.myinterview.myfragment.activity;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myinterview.R;

public class PhotoActivity extends AppCompatActivity {

    public static final String PHOTO_NAME = "photo_name";
    public static final String PHOTO_IMAGE_ID = "photo_image_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_photo);

        Intent intent = getIntent();
        String photoName = intent.getStringExtra(PHOTO_NAME);
        int photoImageId = intent.getIntExtra(PHOTO_IMAGE_ID, 0);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        ImageView photoImageView = (ImageView) findViewById(R.id.photo_image_view);
        TextView photoContentText = (TextView) findViewById(R.id.photo_content_text);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        collapsingToolbar.setTitle(photoName);
        Glide.with(this).load(photoImageId).into(photoImageView);
        String photoContent = generateFruitContent(photoName);
        photoContentText.setText(photoContent);

    }

    private String generateFruitContent(String fruitName) {
        StringBuilder fruitContent = new StringBuilder();
        for (int i = 0; i < 500; i++) {
            fruitContent.append(fruitName);
        }
        return fruitContent.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
