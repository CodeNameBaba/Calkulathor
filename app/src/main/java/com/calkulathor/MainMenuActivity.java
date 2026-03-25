package com.calkulathor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainMenuActivity extends AppCompatActivity {

    Button btnOpenCalculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        // Button
        btnOpenCalculator = findViewById(R.id.btnOpenCalculator);

        // Button click → open selected model
        btnOpenCalculator.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
            String model = prefs.getString("calculator_model", "HelloWorld");

            if (model.equals("HelloWorld")) {
                startActivity(new Intent(this, HelloWorldCalculatorActivity.class));
            }
            else if (model.equals("Smartest")) {
                startActivity(new Intent(this, SmartestCalculatorActivity.class));
            }
            else if (model.equals("RemoveOS")) {
                startActivity(new Intent(this, RemoveOSCalculatorActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_overflow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
