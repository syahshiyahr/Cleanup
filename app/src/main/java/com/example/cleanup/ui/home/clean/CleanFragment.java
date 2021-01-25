package com.example.cleanup.ui.home.clean;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.cleanup.R;
import com.example.cleanup.adapter.NearAdapter;
import com.example.cleanup.model.Beaches;
import com.example.cleanup.ui.home.HomeFragment;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CleanFragment extends Fragment {
    DatabaseReference ref;
    NearAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_clean, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ref = FirebaseDatabase.getInstance().getReference().child("nearBeaches");

        recyclerView = view.findViewById(R.id.rv_near_beaches);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<Beaches> options = new FirebaseRecyclerOptions.Builder<Beaches>()
                .setQuery(ref, Beaches.class)
                .build();


        adapter = new NearAdapter(options);
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