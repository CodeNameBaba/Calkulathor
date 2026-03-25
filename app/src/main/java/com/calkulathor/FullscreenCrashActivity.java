package com.calkulathor;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

public class FullscreenCrashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // hide everything
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        );

        setContentView(R.layout.activity_fullscreen_crash);

        String uriStr = getIntent().getStringExtra("uri");

        if (uriStr == null) finish();
        Uri uri = Uri.parse(uriStr);

        ImageView img = findViewById(R.id.crashImage);
        VideoView video = findViewById(R.id.crashVideo);

        String type = getContentResolver().getType(uri);
        if (type != null && type.startsWith("video")) {
            img.setVisibility(View.GONE);
            video.setVideoURI(uri);
            video.start();
        } else {
            video.setVisibility(View.GONE);
            img.setImageURI(uri);
        }
    }
}
