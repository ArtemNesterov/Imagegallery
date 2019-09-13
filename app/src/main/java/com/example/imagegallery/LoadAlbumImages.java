package com.example.imagegallery;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.imagegallery.adapters.SingleAlbumAdapter;
import com.example.imagegallery.helper.Function;
import com.example.imagegallery.helper.MapComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

class LoadAlbumImages extends AsyncTask<String, Void, String> {
    private ArrayList<HashMap<String, String>> imageList;
    private Activity activity;
    private GridView galleryGridView;

    LoadAlbumImages(ArrayList<HashMap<String, String>> imageList, Activity context, GridView galleryGridView) {
        this.imageList = imageList;
        this.activity = context;
        this.galleryGridView = galleryGridView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        imageList.clear();
    }

    protected String doInBackground(String... args) {
        String xml = "";

        String path = null;
        String album = null;
        String timestamp = null;
        Uri uriExternal = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Uri uriInternal = android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI;

        String[] projection = {MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.MediaColumns.DATE_MODIFIED};

        Cursor cursorExternal = activity.getContentResolver().query(uriExternal, projection, "bucket_display_name = \"" + uriExternal + "\"", null, null);
        Cursor cursorInternal = activity.getContentResolver().query(uriInternal, projection, "bucket_display_name = \"" + uriInternal + "\"", null, null);
        Cursor cursor = new MergeCursor(new Cursor[]{cursorExternal, cursorInternal});
        while (cursor.moveToNext()) {

            path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
            album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
            timestamp = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_MODIFIED));

            imageList.add(Function.mappingInbox(album, path, timestamp, Function.converToTime(timestamp), null));
        }
        cursor.close();
        Collections.sort(imageList, new MapComparator(Function.KEY_TIMESTAMP, "dsc")); // Arranging photo album by timestamp decending
        return xml;
    }

    @Override
    protected void onPostExecute(String xml) {

        SingleAlbumAdapter adapter = new SingleAlbumAdapter(activity, imageList);
        galleryGridView.setAdapter(adapter);
        galleryGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
                Intent intent = new Intent(activity, GalleryPreview.class);
                intent.putExtra("path", imageList.get(+position).get(Function.KEY_PATH));
                activity.startActivity(intent);
            }
        });
    }
}
