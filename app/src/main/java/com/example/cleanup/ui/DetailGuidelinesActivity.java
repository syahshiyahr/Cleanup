package com.example.cleanup.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cleanup.R;

public class DetailGuidelinesActivity extends AppCompatActivity {
    ImageView btnBack;
    TextView name, desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_guidelines);

        name = findViewById(R.id.tv_name_detail);
        desc = findViewById(R.id.tv_desc_detail);

        btnBack = findViewById(R.id.ic_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Bundle bundle = getIntent().getExtras();
        String nameExtra = bundle.getString("NAME");
        String descExtra = bundle.getString("DESC");

        name.setText(nameExtra);
        desc.setText(descExtra);

    }
}