package com.example.cleanup.ui.home;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.cleanup.R;
import com.example.cleanup.ui.ReportFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {
    Button btnReports, btnVisited;
    DatabaseReference ref;
    String userId;
    int size = 0;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        btnReports = view.findViewById(R.id.btn_reports);
        btnVisited = view.findViewById(R.id.btn_visited_beach);

        ref = FirebaseDatabase.getInstance().getReference().child("report");
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        ref.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    size = (int) snapshot.getChildrenCount();
                    btnReports.setText(Html.fromHtml("<font color='#FFE27D'><b>" + size +"</b></font>" + "<font color='#FFFFFF'> Reports</font>"));
                }else {
                    btnReports.setText(Html.fromHtml("<font color='#FFE27D'><b>" + 0 +"</b></font>" + "<font color='#FFFFFF'> Reports</font>"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btnVisited.setText(Html.fromHtml("<font color='#FFE27D'><b>" + 0 +"</b></font>" + "<font color='#FFFFFF'> Visited Beach</font>"));

        btnReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ReportFragment();
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment, fragment.getClass().getSimpleName()).commit();

            }
        });
    }
}