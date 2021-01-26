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
import com.example.cleanup.model.Redeem;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

public class RedeemAdapter extends FirebaseRecyclerAdapter<Redeem, RedeemAdapter.redeemViewholder> {


    public RedeemAdapter(@NonNull FirebaseRecyclerOptions<Redeem> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull RedeemAdapter.redeemViewholder holder, int position, @NonNull Redeem model) {
        holder.name.setText(model.getName());
        holder.company.setText(model.getCompany());
        Picasso.with(holder.itemView.getContext())
                .load(model.getImage())
                .fit()
                .centerCrop()
                .into(holder.img);
        holder.btnPoint.setText(model.getPoint().toString());
//        holder.btnPoint.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    @NonNull
    @Override
    public RedeemAdapter.redeemViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_redeem, parent, false);
        return new RedeemAdapter.redeemViewholder(view);
    }

    class redeemViewholder extends RecyclerView.ViewHolder {
        TextView name, company;
        ImageView img;
        MaterialButton btnPoint;
        public redeemViewholder(@NonNull View itemView){
            super(itemView);

            img = itemView.findViewById(R.id.img_redeem);
            name = itemView.findViewById(R.id.tv_name_voucher);
            company = itemView.findViewById(R.id.tv_company);
            btnPoint = itemView.findViewById(R.id.btn_redeem);
        }

    }
}