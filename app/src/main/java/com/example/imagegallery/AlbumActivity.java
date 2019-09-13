package com.example.imagegallery;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;



import java.util.ArrayList;
import java.util.HashMap;

public class AlbumActivity extends AppCompatActivity {
    GridView galleryGridView;
    ArrayList<HashMap<String, String>> imageList = new ArrayList<HashMap<String, String>>();
    String album_name = "";
    LoadAlbumImages loadAlbumTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        Intent intent = getIntent();
        album_name = intent.getStringExtra("name");
        setTitle(album_name);

        galleryGridView = findViewById(R.id.galleryGridView);
        Resources resources = getApplicationContext().getResources();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            galleryGridView.setColumnWidth(3);
        else galleryGridView.setColumnWidth(5);

        loadAlbumTask = new LoadAlbumImages(imageList, AlbumActivity.this, galleryGridView);
        loadAlbumTask.execute();


    }
}

