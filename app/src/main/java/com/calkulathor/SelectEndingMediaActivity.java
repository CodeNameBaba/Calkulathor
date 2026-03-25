package com.calkulathor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class SelectEndingMediaActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PICK = 1001;
    private SharedPreferences prefs;
    private LinearLayout container;
    private List<Uri> uris = new ArrayList<>();
    private int selectedIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_ending_media);

        Toolbar toolbar = findViewById(R.id.toolbarMedia);
        setSupportActionBar(toolbar);

        prefs = getSharedPreferences("settings", MODE_PRIVATE);
        container = findViewById(R.id.mediaContainer);
        Button btnAdd = findViewById(R.id.btnAddMedia);

        loadSavedUris();
        selectedIndex = prefs.getInt("ending_selected_index", -1);
        refreshList();

        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[] {"image/*", "video/*"});
            startActivityForResult(intent, REQUEST_CODE_PICK);
        });
    }

    private void loadSavedUris() {
        uris.clear();
        String json = prefs.getString("ending_media", "[]");
        try {
            JSONArray arr = new JSONArray(json);
            for (int i = 0; i < arr.length(); i++) {
                uris.add(Uri.parse(arr.getString(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void saveUris() {
        JSONArray arr = new JSONArray();
        for (Uri u : uris) arr.put(u.toString());
        prefs.edit().putString("ending_media", arr.toString()).apply();
        prefs.edit().putInt("ending_selected_index", selectedIndex).apply();
    }

    private void refreshList() {
        container.removeAllViews();
        for (int i = 0; i < uris.size(); i++) {
            final int idx = i;
            Uri u = uris.get(i);
            View item = getLayoutInflater().inflate(R.layout.item_media, container, false);
            AppCompatTextView tv = item.findViewById(R.id.tvMediaUri);
            Button btnDelete = item.findViewById(R.id.btnDeleteMedia);
            Button btnPreview = item.findViewById(R.id.btnPreviewMedia);
            Button btnSelectDefault = item.findViewById(R.id.btnSelectDefault);

            tv.setText(u.toString());
            btnDelete.setOnClickListener(v -> {
                uris.remove(idx);
                if (selectedIndex == idx) selectedIndex = -1;
                else if (selectedIndex > idx) selectedIndex--;
                saveUris();
                refreshList();
            });

            btnPreview.setOnClickListener(v -> {
                Intent view = new Intent(Intent.ACTION_VIEW);
                view.setData(u);
                view.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                if (view.resolveActivity(getPackageManager()) != null) {
                    startActivity(view);
                } else {
                    Toast.makeText(this, "No app to preview", Toast.LENGTH_SHORT).show();
                }
            });

            btnSelectDefault.setOnClickListener(v -> {
                selectedIndex = idx;
                saveUris();
                refreshList();
            });

            // indicate default
            if (selectedIndex == idx) {
                btnSelectDefault.setText("Selected for Remove.os");
                btnSelectDefault.setEnabled(false);
            } else {
                btnSelectDefault.setText("Select for Remove.os");
                btnSelectDefault.setEnabled(true);
            }

            container.addView(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                // Persist permission
                try {
                    final int takeFlags = data.getFlags() &
                            (Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    getContentResolver().takePersistableUriPermission(uri, takeFlags);
                } catch (Exception ex) { /* ignore */ }

                uris.add(uri);
                saveUris();
                refreshList();
            }
        }
    }
}
