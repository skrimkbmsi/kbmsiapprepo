package com.kbmsi.kbmsiapps;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Rinov on 3/20/2017.
 */

public class EventPengumumanAdapter extends RecyclerView.Adapter<EventPengumumanAdapter.MyViewHolder> {

    private Context mContext;
    private List<EventPengumuman> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView judul, tanggal;

        public MyViewHolder(View view) {
            super(view);
            judul = (TextView) view.findViewById(R.id.judul);
            tanggal = (TextView) view.findViewById(R.id.tanggal);
        }
    }


    public EventPengumumanAdapter(Context mContext, List<EventPengumuman> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        EventPengumuman album = albumList.get(position);
        holder.judul.setText(album.getJudul());
        holder.tanggal.setText(album.getTanggal());

    }
    @Override
    public int getItemCount() {
        return albumList.size();
    }
}
