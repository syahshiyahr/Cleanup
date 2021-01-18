package com.example.cleanup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.material.textfield.TextInputLayout;

public class ReportActivity extends AppCompatActivity {
    private TextInputLayout edtLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        edtLevel = findViewById(R.id.til_level_polution);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, LEVEL);
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.et_level_polution);
        textView.setAdapter(adapter);

    }

    private static final String[] LEVEL = new String[]{
            "A", "B", "C"
    };
}