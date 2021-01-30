package com.example.cleanup.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cleanup.R;
import com.example.cleanup.ui.GuidelinesActivity;
import com.google.android.material.button.MaterialButton;

public class DetailCleanupDrivesFragment extends Fragment {
    ImageView btnBack;
    TextView name, distance, date, time;
    String nameDetail, distanceDetail, dateDetail, timeDetail, linkDetail, getPosition;
    MaterialButton btnGuidelines, btnSubscribe;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_cleanup_drives, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnBack = view.findViewById(R.id.ic_back);
        btnGuidelines = view.findViewById(R.id.btn_guidlines);
        btnSubscribe = view.findViewById(R.id.btn_subscribe);
        name = view.findViewById(R.id.tv_beach_name_detail);
        distance = view.findViewById(R.id.tv_distance_detail);
        date = view.findViewById(R.id.tv_event_date_detail);
        time = view.findViewById(R.id.tv_event_time_detail);

        name.setText(nameDetail);
        distance.setText(distanceDetail);
        date.setText(dateDetail);
        time.setText(timeDetail);
        btnSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(linkDetail)));
            }
        });
        btnGuidelines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), GuidelinesActivity.class));
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getPosition == "list") {
                    Fragment fragment = new UpcomingFragment();
                    getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment, fragment.getClass().getSimpleName()).commit();
                }else {
                    Fragment fragment = new HomeFragment();
                    getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment, fragment.getClass().getSimpleName()).commit();

                }
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String name = bundle.getString("BEACH_NAME", null);
            String distance = bundle.getString("DISTANCE", null);
            String date = bundle.getString("EVENT_DATE", null);
            String time = bundle.getString("EVENT_TIME", null);
            String link = bundle.getString("LINK", null);
            String position = bundle.getString("EXTRA_NAME", "home");

            nameDetail = name;
            distanceDetail = distance;
            dateDetail = date;
            timeDetail = time;
            linkDetail = link;
            getPosition = position;

        }
    }

}