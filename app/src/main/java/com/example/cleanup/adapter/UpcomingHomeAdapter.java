package com.example.cleanup.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cleanup.R;
import com.example.cleanup.model.Report;
import com.example.cleanup.model.Upcoming;
import com.example.cleanup.ui.home.DetailCleanupDrivesFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class UpcomingHomeAdapter extends FirebaseRecyclerAdapter<Upcoming, UpcomingHomeAdapter.upcomingViewholder> {

    private Context context;
    public UpcomingHomeAdapter(Context context, @NonNull FirebaseRecyclerOptions<Upcoming> options) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull upcomingViewholder holder, int position, @NonNull Upcoming model) {
        holder.name.setText(model.getBeachName());
        holder.date.setText(model.getEventDate() + " - " + model.getEventTime());
        holder.distance.setText(model.getDistance());
        Picasso.with(holder.itemView.getContext())
                .load(model.getImage())
                .fit()
                .centerCrop()
                .into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new DetailCleanupDrivesFragment();
                Bundle bundle = new Bundle();
                bundle.putString("BEACH_NAME", model.getBeachName());
                bundle.putString("DISTANCE", model.getDistance());
                bundle.putString("EVENT_DATE", model.getEventDate());
                bundle.putString("EVENT_TIME", model.getEventTime());
                bundle.putString("LINK", model.getLink());
                bundle.putString("EXTRA_NAME", "home");
                fragment.setArguments(bundle);
                FragmentManager manager = ((FragmentActivity)context).getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.nav_host_fragment, fragment, fragment.getClass().getSimpleName()).commit();
            }
        });
    }

    @NonNull
    @Override
    public upcomingViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_upcoming_home, parent, false);
        return new UpcomingHomeAdapter.upcomingViewholder(view);
    }

    class upcomingViewholder extends RecyclerView.ViewHolder {
        TextView name, distance, date;
        ImageView img;
        public upcomingViewholder(@NonNull View itemView){
            super(itemView);

            img = itemView.findViewById(R.id.img_upcoming);
            name = itemView.findViewById(R.id.tv_beach_name);
            distance = itemView.findViewById(R.id.tv_distance);
            date = itemView.findViewById(R.id.tv_date);
        }

    }
}