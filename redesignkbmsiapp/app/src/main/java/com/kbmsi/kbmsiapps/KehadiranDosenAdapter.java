package com.kbmsi.kbmsiapps;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by Rinov on 12/25/2017.
 */

public class KehadiranDosenAdapter extends RecyclerView.Adapter<KehadiranDosenAdapter.MyViewHolder> {

    private Context mContext;
    private List<Cuaca> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView namaDosen, tempat, kehadiran;
        public ImageView fotoProfil;
        public CardView cardcolor;

        public MyViewHolder(View view) {
            super(view);
            namaDosen = (TextView) view.findViewById(R.id.tampilnama);
            tempat = (TextView) view.findViewById(R.id.infonya);
            kehadiran = (TextView) view.findViewById(R.id.kehadiran);
            fotoProfil = (ImageView) view.findViewById(R.id.kondisinya);
            cardcolor = (CardView) view.findViewById(R.id.cardcolor);
        }
    }


    public KehadiranDosenAdapter(Context mContext, List<Cuaca> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public KehadiranDosenAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rowviewc, parent, false);

        return new KehadiranDosenAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final KehadiranDosenAdapter.MyViewHolder holder, int position) {
        final Cuaca current = albumList.get(position);
        holder.namaDosen.setText(current.nama);
        holder.tempat.setText(current.info);
        holder.kehadiran.setText(current.kehadiran);
//        holder.info.setText(current.info);
        if (!current.kehadiran.equalsIgnoreCase("Tidak Hadir")){
            holder.cardcolor.setBackgroundColor(ContextCompat.getColor(mContext, R.color.hadir));
        } else {
            holder.cardcolor.setBackgroundColor(ContextCompat.getColor(mContext, R.color.ghadir));

        }

        Glide.with(mContext).load(current.foto)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.fotoProfil);
    }
    public void clearData() {
        // clear the data
        albumList.clear();
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
//    private void showPopupMenu(View view) {
//        // inflate menu
//        PopupMenu popup = new PopupMenu(mContext, view);
//        MenuInflater inflater = popup.getMenuInflater();
//        inflater.inflate(R.menu.menu_album, popup.getMenu());
//        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
//        popup.show();
//    }

    /**
     * Click listener for popup menu items
     */
//    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
//
//        public MyMenuItemClickListener() {
//        }
//
//        @Override
//        public boolean onMenuItemClick(MenuItem menuItem) {
//            switch (menuItem.getItemId()) {
//                case R.id.action_add_favourite:
//                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
//                    return true;
//                case R.id.action_play_next:
//                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
//                    return true;
//                default:
//            }
//            return false;
//        }
//    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}
