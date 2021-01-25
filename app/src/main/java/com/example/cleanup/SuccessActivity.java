package com.example.cleanup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class SuccessActivity extends AppCompatActivity implements View.OnClickListener {
    MaterialButton btnToDashboard, btnReportAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        btnToDashboard = findViewById(R.id.btn_back_home);
        btnReportAgain = findViewById(R.id.btn_report_again);

        btnReportAgain.setOnClickListener(this);
        btnToDashboard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back_home:
                Intent in = new Intent(this, MainActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(in);
                finish();
                break;
            case R.id.btn_report_again:
                Intent report = new Intent(this, ChooseReportActivity.class);
                report.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(report);
                finish();
                break;
        }
    }
}