package com.example.cleanup.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cleanup.R;
import com.example.cleanup.adapter.CardViewReportsAdapter;
import com.example.cleanup.model.Report;
import com.example.cleanup.ui.home.HomeFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ReportFragment extends Fragment {
    private RecyclerView recyclerView;
    DatabaseReference ref, refUid;
    private FirebaseRecyclerOptions<Report> options;
    //private FirebaseRecyclerAdapter<Report, MyViewHolder> adapter;
    CardViewReportsAdapter adapter;
    ImageView btnBack;
    int sum = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ref = FirebaseDatabase.getInstance().getReference().child("report");
        refUid = ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        Query query = ref.child(FirebaseAuth.getInstance().getCurrentUser().getUid());

            btnBack = view.findViewById(R.id.ic_back);
            recyclerView = view.findViewById(R.id.rv_reports);
            //recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            FirebaseRecyclerOptions<Report> options = new FirebaseRecyclerOptions.Builder<Report>()
                    .setQuery(query, Report.class)
                    .build();


            adapter = new CardViewReportsAdapter(options);
            recyclerView.setAdapter(adapter);

            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = new HomeFragment();
                    getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment, fragment.getClass().getSimpleName()).commit();

                }
            });

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