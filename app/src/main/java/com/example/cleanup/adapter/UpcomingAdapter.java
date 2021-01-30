package com.example.cleanup.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cleanup.R;
import com.example.cleanup.model.Upcoming;
import com.example.cleanup.ui.home.DetailCleanupDrivesFragment;
import com.example.cleanup.ui.home.ReportFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

public class UpcomingAdapter extends FirebaseRecyclerAdapter<Upcoming, UpcomingAdapter.upcomingViewholder> {
    private Context context;


    public UpcomingAdapter(Context context, @NonNull FirebaseRecyclerOptions<Upcoming> options) {
        super(options);
        this.context = context;
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
                bundle.putString("EXTRA_NAME", "list");
                fragment.setArguments(bundle);
                FragmentManager manager = ((FragmentActivity)context).getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.nav_host_fragment, fragment, fragment.getClass().getSimpleName()).commit();
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