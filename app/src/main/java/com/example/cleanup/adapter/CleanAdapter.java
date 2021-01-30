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
import com.example.cleanup.model.Beaches;
import com.example.cleanup.model.Clean;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

public class CleanAdapter extends FirebaseRecyclerAdapter<Clean, CleanAdapter.cleanViewholder> {


    public CleanAdapter(@NonNull FirebaseRecyclerOptions<Clean> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull CleanAdapter.cleanViewholder holder, int position, @NonNull Clean model) {
        holder.name.setText(model.getName());
        holder.distance.setText(model.getDistance());
        holder.report.setText("Total report on this beach : "+model.getReportSum());
        Picasso.with(holder.itemView.getContext())
                .load(model.getImage())
                .fit()
                .centerCrop()
                .into(holder.img);
        holder.btnLearnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(model.getLink())));
            }
        });
    }

    @NonNull
    @Override
    public CleanAdapter.cleanViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clean_or_not, parent, false);
        return new CleanAdapter.cleanViewholder(view);
    }

    class cleanViewholder extends RecyclerView.ViewHolder {
        TextView name, distance, report;
        ImageView img;
        MaterialButton btnLearnMore;
        public cleanViewholder(@NonNull View itemView){
            super(itemView);

            img = itemView.findViewById(R.id.img_near);
            name = itemView.findViewById(R.id.tv_beach_name_near);
            distance = itemView.findViewById(R.id.tv_distance_near);
            report = itemView.findViewById(R.id.tv_report);
            btnLearnMore = itemView.findViewById(R.id.btn_learn_more);
        }

    }
}
