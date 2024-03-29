package com.example.cleanup.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cleanup.R;
import com.example.cleanup.model.Clean;
import com.example.cleanup.model.PointsHistory;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

public class PointsHistoryAdapter extends FirebaseRecyclerAdapter<PointsHistory, PointsHistoryAdapter.pointsHistoryViewholder> {
    private Context context;

    public PointsHistoryAdapter(Context context, @NonNull FirebaseRecyclerOptions<PointsHistory> options) {
        super(options);
        this.context = context;
    }


    @Override
    protected void onBindViewHolder(@NonNull PointsHistoryAdapter.pointsHistoryViewholder holder, int position, @NonNull PointsHistory model) {
        holder.name.setText(model.getName());
        holder.timestamp.setText(model.getTimestamp());
        holder.points.setText(model.getPoints()+" Points");
        holder.status.setText(model.getStatus());

        if(model.getStatus().equals("Earn Points")){
            holder.points.setTextColor(context.getResources().getColor(R.color.green_primary));
            holder.status.setTextColor(context.getResources().getColor(R.color.green_primary));
        }else{
            holder.points.setTextColor(context.getResources().getColor(R.color.blue_secondary));
            holder.status.setTextColor(context.getResources().getColor(R.color.blue_secondary));
        }
    }

    @NonNull
    @Override
    public PointsHistoryAdapter.pointsHistoryViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_points_history, parent, false);
        return new PointsHistoryAdapter.pointsHistoryViewholder(view);
    }

    class pointsHistoryViewholder extends RecyclerView.ViewHolder {
        TextView name, status, timestamp, points;

        public pointsHistoryViewholder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_name_history);
            status = itemView.findViewById(R.id.tv_status_history);
            timestamp = itemView.findViewById(R.id.tv_timestamp_history);
            points = itemView.findViewById(R.id.tv_points);
        }

    }
}
