package com.example.cleanup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ChooseReportActivity extends AppCompatActivity {

    private CardView cardCurrent, cardNotCurrent;
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_report);

        cardCurrent = findViewById(R.id.card_currently);
        cardNotCurrent = findViewById(R.id.card_not_at_location);
        btnBack = findViewById(R.id.ic_back);

        cardCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ChooseReportActivity.this, ReportCurrentActivity.class);
                startActivity(in);

            }
        });

        cardNotCurrent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ChooseReportActivity.this, ReportNotCurrentActivity.class);
                startActivity(in);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent in = new Intent(this, MainActivity.class);
        startActivity(in);
        finish();
    }
}