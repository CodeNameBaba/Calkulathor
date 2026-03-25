package com.calkulathor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SmartestCalculatorActivity extends AppCompatActivity {

    TextView tvDisplay;
    StringBuilder input = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator_simple);

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
            tvDisplay.setText("Bhai Akela chor de");
            input.setLength(0);
        });
    }
}
