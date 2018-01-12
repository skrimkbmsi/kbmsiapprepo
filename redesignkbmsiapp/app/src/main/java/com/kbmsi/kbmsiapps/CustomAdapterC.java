package com.kbmsi.kbmsiapps;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;


public class CustomAdapterC extends RecyclerView.Adapter <CustomAdapterC.CustomViewHolder> {

    LayoutInflater mInflater;
    ArrayList<Cuaca> osList;
    Context mContext;
    public CustomAdapterC(Context context,
                          ArrayList<Cuaca> namakota) {
        this.mInflater = LayoutInflater.from(context);
        this.osList = namakota;
        mContext = context;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        CustomAdapterC mAdapter;
        TextView namaItemView, info, kehadiran;
//        TextView idItemView;
        ImageView kondisiItemView;
        CardView cardcolor;

        public CustomViewHolder(View itemView, CustomAdapterC adapter) {
            super(itemView);
            namaItemView = itemView.findViewById(R.id.tampilnama);
            info = itemView.findViewById(R.id.infonya);
            kehadiran = itemView.findViewById(R.id.kehadiran);
            kondisiItemView= itemView.findViewById(R.id.kondisinya);
            cardcolor = itemView.findViewById(R.id.cardcolor);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

        }
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(
                R.layout.rowviewc, parent, false);
        return new CustomViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder (CustomViewHolder holder, final int position) {
        final Cuaca current = osList.get(position);
        holder.namaItemView.setText(current.nama);
        holder.info.setText(current.info);
        holder.kehadiran.setText(current.kehadiran);
//        holder.info.setText(current.info);
        if (!current.kehadiran.equalsIgnoreCase("Tidak Hadir")){
            holder.cardcolor.setCardBackgroundColor(0xFF4CBE83);
        } else {
            holder.cardcolor.setCardBackgroundColor(0xFFD9534F);

        }

        Glide.with(mContext).load(current.foto)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.kondisiItemView);
    }

    @Override
    public int getItemCount() {
        return osList.size();
    }




}
