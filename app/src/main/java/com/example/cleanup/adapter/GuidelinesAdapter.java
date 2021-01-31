package com.example.cleanup.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cleanup.R;
import com.example.cleanup.model.Guidelines;
import com.example.cleanup.ui.DetailGuidelinesActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class GuidelinesAdapter extends FirebaseRecyclerAdapter<Guidelines, GuidelinesAdapter.guidelinesViewholder> {
    private Context context;

    public GuidelinesAdapter(Context context, @NonNull FirebaseRecyclerOptions<Guidelines> options) {
        super(options);
        this.context = context;
    }


    @Override
    protected void onBindViewHolder(@NonNull GuidelinesAdapter.guidelinesViewholder holder, int position, @NonNull Guidelines model) {
        holder.use.setText(model.getUse());
        Picasso.with(holder.itemView.getContext())
                .load(model.getImage())
                .fit()
                .centerCrop()
                .into(holder.imgLogo);
        Picasso.with(holder.itemView.getContext())
                .load(model.getImgcategory())
                .fit()
                .centerCrop()
                .into(holder.imgCategory);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailGuidelinesActivity.class);
                intent.putExtra("NAME", model.getName());
                intent.putExtra("DESC", model.getDesc());
                context.startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public GuidelinesAdapter.guidelinesViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_guidelines, parent, false);
        return new GuidelinesAdapter.guidelinesViewholder(view);
    }

    class guidelinesViewholder extends RecyclerView.ViewHolder {

        ImageView imgLogo, imgCategory;
        TextView use;
        public guidelinesViewholder(@NonNull View itemView){
            super(itemView);

            imgCategory = itemView.findViewById(R.id.img_category);
            imgLogo = itemView.findViewById(R.id.img_logo);
            use = itemView.findViewById(R.id.tv_use);
        }

    }
}
