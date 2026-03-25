package com.calkulathor;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class SelectModelActivity extends AppCompatActivity {

    RadioGroup rg;
    RadioButton rbHelloWorld, rbSmartest, rbRemoveOS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_model);

        rg = findViewById(R.id.rgModels);
        rbHelloWorld = findViewById(R.id.rbHelloWorld);
        rbSmartest = findViewById(R.id.rbSmartest);
        rbRemoveOS = findViewById(R.id.rbRemoveOS);

        SharedPreferences prefs = getSharedPreferences("settings", MODE_PRIVATE);
        String saved = prefs.getString("calculator_model", "HelloWorld");

        if (saved.equals("HelloWorld"))
            rbHelloWorld.setChecked(true);
        else if (saved.equals("Smartest"))
            rbSmartest.setChecked(true);
        else if (saved.equals("RemoveOS"))
            rbRemoveOS.setChecked(true);

        rg.setOnCheckedChangeListener((group, checkedId) -> {
            String selected = "HelloWorld";

            if (checkedId == R.id.rbHelloWorld)
                selected = "HelloWorld";

            else if (checkedId == R.id.rbSmartest)
                selected = "Smartest";

            else if (checkedId == R.id.rbRemoveOS)
                selected = "RemoveOS";

            prefs.edit().putString("calculator_model", selected).apply();
            finish();
        });
    }
}
