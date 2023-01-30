package com.example.docs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class FullScreen extends AppCompatActivity {
    Context context;
    ImageView fullScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);

        fullScreen = findViewById(R.id.iv_fullScreen);

        Bundle extras = getIntent().getBundleExtra("userdata");
        byte[] bytes = extras.getByteArray("avatar");
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        fullScreen.setImageBitmap(bitmap);

        //getActionBar().setTitle(extras.getString("name"));
        getSupportActionBar().setTitle(extras.getString("name"));

    }
}