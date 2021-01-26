package com.example.cleanup.ui.home;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cleanup.R;
import com.example.cleanup.RewardFragment;
import com.example.cleanup.adapter.CardViewReportsAdapter;
import com.example.cleanup.adapter.UpcomingHomeAdapter;
import com.example.cleanup.model.Report;
import com.example.cleanup.model.Upcoming;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {
    Button btnReports, btnVisited, point;
    TextView seeAll;
    CardView nearBeaches, cleanBeaches, cleanUpDrives;
    DatabaseReference ref, refUpcoming;
    UpcomingHomeAdapter adapter;
    String userId;
    int size = 0;
    private RecyclerView recyclerView;
    LinearLayout statistic;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        btnReports = view.findViewById(R.id.btn_reports);
        btnVisited = view.findViewById(R.id.btn_visited_beach);
        nearBeaches = view.findViewById(R.id.card_near);
        seeAll = view.findViewById(R.id.tv_see_all);
        cleanBeaches = view.findViewById(R.id.card_clean);
        cleanUpDrives = view.findViewById(R.id.card_upcoming);
        statistic = view.findViewById(R.id.statistic_layout);
        point = view.findViewById(R.id.btn_reward);

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

        cleanUpDrives.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new UpcomingFragment();
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment, fragment.getClass().getSimpleName()).commit();

            }
        });


        btnVisited.setText(Html.fromHtml("<font color='#FFE27D'><b>" + 0 +"</b></font>" + "<font color='#FFFFFF'> Visited Beach</font>"));
        btnVisited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new VisitedBeachFragment();
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment, fragment.getClass().getSimpleName()).commit();

            }
        });
        btnReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ReportFragment();
                Bundle bundle = new Bundle();
                bundle.putString("EXTRA_NAME", "home");
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment, fragment.getClass().getSimpleName()).commit();

            }
        });

        seeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new UpcomingFragment();
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment, fragment.getClass().getSimpleName()).commit();

            }
        });

        nearBeaches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new NearBeachesFragment();
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment, fragment.getClass().getSimpleName()).commit();

            }
        });

        cleanBeaches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new CleanOrNotFragment();
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment, fragment.getClass().getSimpleName()).commit();

            }
        });

        statistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new StatisticFragment();
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment, fragment.getClass().getSimpleName()).commit();
            }
        });

        point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new RewardFragment();
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment, fragment.getClass().getSimpleName()).commit();

            }
        });

        //Get Data Upcoming
        refUpcoming = FirebaseDatabase.getInstance().getReference().child("upcoming");

        recyclerView = view.findViewById(R.id.rv_upcoming_home);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        FirebaseRecyclerOptions<Upcoming> options = new FirebaseRecyclerOptions.Builder<Upcoming>()
                .setQuery(refUpcoming, Upcoming.class)
                .build();

        adapter = new UpcomingHomeAdapter(options);
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