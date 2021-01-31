package com.example.cleanup.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.cleanup.R;
import com.example.cleanup.adapter.GuidelinesAdapter;
import com.example.cleanup.adapter.RedeemAdapter;
import com.example.cleanup.model.Guidelines;
import com.example.cleanup.model.Redeem;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GuidelinesActivity extends AppCompatActivity {
    ImageView btnBack;
    DatabaseReference ref;
    GuidelinesAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidelines);

        btnBack = findViewById(R.id.ic_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ref = FirebaseDatabase.getInstance().getReference().child("guidelines");

        recyclerView = findViewById(R.id.rv_guidelines);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Guidelines> options = new FirebaseRecyclerOptions.Builder<Guidelines>()
                .setQuery(ref, Guidelines.class)
                .build();


        adapter = new GuidelinesAdapter(this, options);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}