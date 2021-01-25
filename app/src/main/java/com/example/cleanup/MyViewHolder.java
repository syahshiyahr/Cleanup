package com.example.cleanup;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class MyViewHolder extends RecyclerView.ViewHolder {
    TextView location, date, total, status;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        location = itemView.findViewById(R.id.tv_report_location);
        date = itemView.findViewById(R.id.tv_report_date);
        total = itemView.findViewById(R.id.tv_report_total_registered);
        status = itemView.findViewById(R.id.tv_report_status);
    }
}
