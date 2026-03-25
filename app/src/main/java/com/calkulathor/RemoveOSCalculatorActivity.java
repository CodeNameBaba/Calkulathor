package com.calkulathor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class RemoveOSCalculatorActivity extends AppCompatActivity {

    TextView tvDisplay;
    StringBuilder input = new StringBuilder();
    Handler handler = new Handler();
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_simple);

        prefs = getSharedPreferences("settings", MODE_PRIVATE);

        tvDisplay = findViewById(R.id.tvCalcDisplay);

        int[] btnIds = new int[] {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
                R.id.btnPlus, R.id.btnMinus, R.id.btnMul, R.id.btnDiv, R.id.btnDot
        };

        View.OnClickListener appendListener = v -> {
            Button b = (Button) v;
            input.append(b.getText());
            tvDisplay.setText(input.toString());
        };

        for (int id : btnIds) findViewById(id).setOnClickListener(appendListener);

        findViewById(R.id.btnClear).setOnClickListener(v -> {
            input.setLength(0);
            tvDisplay.setText("");
        });

        findViewById(R.id.btnEquals).setOnClickListener(v -> {

            final String[] messages = new String[] {
                    "ERROR: Kernel panic at 0x0",
                    "ERROR: Filesystem corrupted",
                    "ERROR: Unauthorized access detected",
                    "ERROR: Shutting down modules..."
            };

            for (int i = 0; i < messages.length; i++) {
                final int idx = i;
                handler.postDelayed(() -> tvDisplay.setText(messages[idx]), 500L * i);
            }

            handler.postDelayed(() -> {

                // Load selected media index
                int selectedIndex = prefs.getInt("ending_selected_index", -1);
                List<Uri> saved = loadSavedUris();

                if (selectedIndex >= 0 && selectedIndex < saved.size()) {
                    Uri selectedUri = saved.get(selectedIndex);

                    // Pass URI to fullscreen crash activity
                    Intent intent = new Intent(this, FullscreenCrashActivity.class);
                    intent.putExtra("uri", selectedUri.toString());
                    startActivity(intent);

                } else {
                    tvDisplay.setText("No ending media selected");
                }

            }, 500L * (messages.length));
        });
    }

    private List<Uri> loadSavedUris() {
        List<Uri> uris = new ArrayList<>();
        String json = prefs.getString("ending_media", "[]");

        try {
            JSONArray arr = new JSONArray(json);
            for (int i = 0; i < arr.length(); i++) {
                uris.add(Uri.parse(arr.getString(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return uris;
    }
}
