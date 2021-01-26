package com.example.cleanup;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.cleanup.adapter.SectionPagerAdapter;
import com.example.cleanup.ui.home.HomeFragment;
import com.example.cleanup.ui.home.clean.CleanFragment;
import com.example.cleanup.ui.home.clean.DirtyFragment;
import com.example.cleanup.ui.reward.PointsHistoryFragment;
import com.example.cleanup.ui.reward.RedeemFragment;
import com.google.android.material.tabs.TabLayout;


public class RewardFragment extends Fragment {
    ViewPager viewPager;
    TabLayout tabLayout;
    ImageView btnBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reward, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);
        btnBack = view.findViewById(R.id.ic_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new HomeFragment();
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment, fragment.getClass().getSimpleName()).commit();

            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setUpViewPager(ViewPager viewPager) {
        SectionPagerAdapter adapter = new SectionPagerAdapter(getChildFragmentManager());

        adapter.addFragment(new RedeemFragment(), "Redeem");
        adapter.addFragment(new PointsHistoryFragment(), "Points History");

        viewPager.setAdapter(adapter);
    }
}