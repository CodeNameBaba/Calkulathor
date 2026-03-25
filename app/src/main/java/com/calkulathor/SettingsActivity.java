package com.calkulathor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    Button btnSelectModel, btnSelectEnding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        btnSelectModel = findViewById(R.id.btnSelectModel);
        btnSelectEnding = findViewById(R.id.btnSelectEnding);

        btnSelectModel.setOnClickListener(v ->
                startActivity(new Intent(this, SelectModelActivity.class)));

        btnSelectEnding.setOnClickListener(v ->
                startActivity(new Intent(this, SelectEndingMediaActivity.class)));
    }
}
