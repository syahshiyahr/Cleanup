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
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

public class NearAdapter extends FirebaseRecyclerAdapter<Beaches, NearAdapter.nearViewholder> {


    public NearAdapter(@NonNull FirebaseRecyclerOptions<Beaches> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull NearAdapter.nearViewholder holder, int position, @NonNull Beaches model) {
        holder.name.setText(model.getName());
        holder.distance.setText(model.getDistance());
        Picasso.with(holder.itemView.getContext())
                .load(model.getImg())
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
    public NearAdapter.nearViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_near, parent, false);
        return new NearAdapter.nearViewholder(view);
    }

    class nearViewholder extends RecyclerView.ViewHolder {
        TextView name, distance;
        ImageView img;
        MaterialButton btnLearnMore;
        public nearViewholder(@NonNull View itemView){
            super(itemView);

            img = itemView.findViewById(R.id.img_near);
            name = itemView.findViewById(R.id.tv_beach_name_near);
            distance = itemView.findViewById(R.id.tv_distance_near);
            btnLearnMore = itemView.findViewById(R.id.btn_learn_more);
        }

    }
}