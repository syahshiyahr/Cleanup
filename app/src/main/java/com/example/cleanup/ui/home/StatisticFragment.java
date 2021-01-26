package com.example.cleanup.ui.home;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.cleanup.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;


public class StatisticFragment extends Fragment {
    PieChart pieChartAll, yourPieChart;
    ImageView btnBack;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_statistic, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnBack = view.findViewById(R.id.ic_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new HomeFragment();
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, fragment, fragment.getClass().getSimpleName()).commit();

            }
        });

        pieChartAll = view.findViewById(R.id.pieChartAll);
        statisticAll();

        yourPieChart = view.findViewById(R.id.yourPieChart);
        statisticYour();


    }

    private void statisticYour() {
        ArrayList<PieEntry> reports = new ArrayList<>();
        reports.add(new PieEntry(1, "Reported"));
        reports.add(new PieEntry(1, "Cleaned"));
        reports.add(new PieEntry(1, "On Progress"));

        PieDataSet pieDataSet = new PieDataSet(reports, "");

        pieDataSet.setDrawIcons(false);

        pieDataSet.setSliceSpace(3f);
        pieDataSet.setIconsOffset(new MPPointF(0, 40));
        pieDataSet.setSelectionShift(5f);

        pieDataSet.setColors(CUSTOM_COLOR);
        pieDataSet.setSliceSpace(3f);

        PieData pieData = new PieData(pieDataSet);

        pieData.setValueTextSize(14f);
        pieData.setValueTextColor(Color.BLACK);

        yourPieChart.setData(pieData);
        yourPieChart.setDrawEntryLabels(false);
        yourPieChart.getDescription().setEnabled(false);
        yourPieChart.setCenterText(Html.fromHtml("<center><font color='#474748'><b>3</b><br><small>Reports</small></font></center>"));
        yourPieChart.setCenterTextColor(R.color.text_black);
        yourPieChart.setCenterTextSize(18f);
        yourPieChart.animate();
    }

    private void statisticAll() {
        ArrayList<PieEntry> reports = new ArrayList<>();
        reports.add(new PieEntry(1080, "Reported"));
        reports.add(new PieEntry(403, "Cleaned"));
        reports.add(new PieEntry(213, "On Progress"));

        PieDataSet pieDataSet = new PieDataSet(reports, "");

        pieDataSet.setDrawIcons(false);

        pieDataSet.setSliceSpace(3f);
        pieDataSet.setIconsOffset(new MPPointF(0, 40));
        pieDataSet.setSelectionShift(5f);

        pieDataSet.setColors(CUSTOM_COLOR);
        pieDataSet.setSliceSpace(3f);

        PieData pieData = new PieData(pieDataSet);

        pieData.setValueTextSize(14f);
        pieData.setValueTextColor(Color.BLACK);

        pieChartAll.setData(pieData);
        pieChartAll.setDrawEntryLabels(false);
        pieChartAll.getDescription().setEnabled(false);
        pieChartAll.setCenterText(Html.fromHtml("<center><font color='#474748'><b>1696</b><br><small>Reports</small></font></center>"));
        pieChartAll.setCenterTextColor(R.color.text_black);
        pieChartAll.setCenterTextSize(18f);
        pieChartAll.animate();
    }

    public static final int[] CUSTOM_COLOR = {
            rgb("#52CAE4"), rgb("#67CC6F"), rgb("#F2C94C")
    };


    public static int rgb(String hex) {
        int color = (int) Long.parseLong(hex.replace("#", ""), 16);
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = (color >> 0) & 0xFF;
        return Color.rgb(r, g, b);
    }
}