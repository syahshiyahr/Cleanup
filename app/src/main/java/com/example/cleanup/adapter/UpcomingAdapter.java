package com.example.cleanup.adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cleanup.R;
import com.example.cleanup.model.Upcoming;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

public class UpcomingAdapter extends FirebaseRecyclerAdapter<Upcoming, UpcomingAdapter.upcomingViewholder> {


    public UpcomingAdapter(@NonNull FirebaseRecyclerOptions<Upcoming> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull UpcomingAdapter.upcomingViewholder holder, int position, @NonNull Upcoming model) {
        holder.name.setText(model.getBeachName());
        holder.date.setText(model.getEventDate() + " - " + model.getEventTime());
        holder.distance.setText(model.getDistance());
        Picasso.with(holder.itemView.getContext())
                .load(model.getImage())
                .fit()
                .centerCrop()
                .into(holder.img);
        holder.btnSubscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(model.getLink())));
            }
        });
    }

    @NonNull
    @Override
    public UpcomingAdapter.upcomingViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_upcoming, parent, false);
        return new UpcomingAdapter.upcomingViewholder(view);
    }

    class upcomingViewholder extends RecyclerView.ViewHolder {
        TextView name, distance, date;
        ImageView img;
        MaterialButton btnSubscribe;
        public upcomingViewholder(@NonNull View itemView){
            super(itemView);

            img = itemView.findViewById(R.id.img_upcoming);
            name = itemView.findViewById(R.id.tv_beach_name);
            distance = itemView.findViewById(R.id.tv_distance);
            date = itemView.findViewById(R.id.tv_date);
            btnSubscribe = itemView.findViewById(R.id.btn_subscribe);
        }

    }
}