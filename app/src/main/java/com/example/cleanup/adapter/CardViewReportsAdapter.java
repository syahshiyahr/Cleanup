package com.example.cleanup.adapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cleanup.R;
import com.example.cleanup.model.Report;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class CardViewReportsAdapter extends FirebaseRecyclerAdapter<Report, CardViewReportsAdapter.reportViewholder> {


    public CardViewReportsAdapter(@NonNull FirebaseRecyclerOptions<Report> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull reportViewholder holder, int position, @NonNull Report model) {
        holder.location.setText(model.getLocation());
        holder.date.setText(model.getTimestamp());

        if (model.getStatus().equalsIgnoreCase("Reported")){
            holder.status.setText(Html.fromHtml("<font color='#474748'>Status: </font>" + "<font color='#1CA0D1'><b>"+model.getStatus()+"</b></font>"));
        }else if(model.getStatus().equalsIgnoreCase("Cleaned")){
            holder.status.setText(Html.fromHtml("<font color='#474748'>Status: </font>" + "<font color='#68C773'><b>"+model.getStatus()+"</b></font>"));
        }else if(model.getStatus().equalsIgnoreCase("On Progress")){
            holder.status.setText(Html.fromHtml("<font color='#474748'>Status: </font>" + "<font color='#F2C94C'><b>"+model.getStatus()+"</b></font>"));
        }

        holder.total.setText("Total registered complaint : 20");
    }

    @NonNull
    @Override
    public reportViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reports, parent, false);
        return new CardViewReportsAdapter.reportViewholder(view);
    }

    class reportViewholder extends RecyclerView.ViewHolder {
        TextView location, date, total, status;
        public reportViewholder(@NonNull View itemView){
            super(itemView);

            location = itemView.findViewById(R.id.tv_report_location);
            date = itemView.findViewById(R.id.tv_report_date);
            total = itemView.findViewById(R.id.tv_report_total_registered);
            status = itemView.findViewById(R.id.tv_report_status);
        }

    }
}
