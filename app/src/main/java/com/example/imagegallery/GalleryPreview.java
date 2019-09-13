package com.example.imagegallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import java.io.File;

public class GalleryPreview extends AppCompatActivity {

    ImageView GalleryPreviewImg;
    String path;
    Button loadImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.gallery_preview);
        Intent intent = getIntent();
        path = intent.getStringExtra("path");
        GalleryPreviewImg = (ImageView) findViewById(R.id.GalleryPreviewImg);
        loadImage = findViewById(R.id.loadImage);
        Glide.with(GalleryPreview.this)
                .load(new File(path))
                .into(GalleryPreviewImg);

        loadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postUrl = "https://apidocs.imgur.com/#58306db8-0a6f-4aa1-a021-bdad565f153e";
                 Gson gson = new Gson();
                 HttpClient httpClient = HttpClientBuilder.create().build();
                 HttpPost post = new HttpPost(postUrl);
                 StringEntity postingString = new StringEntity(gson.toJson(pojo1));
                 gson.tojson() converts your pojo to json post.setEntity(postingString);
                 post.setHeader("Content-type", "application/json");
                 HttpResponse response = httpClient.execute(post);
            }
        });
    }
}
