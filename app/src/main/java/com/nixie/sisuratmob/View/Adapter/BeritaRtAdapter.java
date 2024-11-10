package com.nixie.sisuratmob.View.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nixie.sisuratmob.Models.BeritaRt;
import com.nixie.sisuratmob.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BeritaRtAdapter extends RecyclerView.Adapter<BeritaRtAdapter.BeritaRtViewHolder> {

    private Context context;
    private List<BeritaRt> beritaRtList;

    public BeritaRtAdapter(Context context, List<BeritaRt> beritaRtList) {
        this.context = context;
        this.beritaRtList = beritaRtList;
    }

    @NonNull
    @Override
    public BeritaRtViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_berita, parent, false);
        return new BeritaRtViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BeritaRtAdapter.BeritaRtViewHolder holder, int position) {
        BeritaRt beritaRt = beritaRtList.get(position);

        // Mengatur judul
        holder.judulTextview.setText(beritaRt.getJudul());

        // Mengatur gambar menggunakan Picasso
        Picasso.get().load(beritaRt.getGambarUrl()).into(holder.gambarImageView);
    }

    @Override
    public int getItemCount() {
        return beritaRtList.size();
    }

    public static class BeritaRtViewHolder extends RecyclerView.ViewHolder {
        TextView judulTextview;
        ImageView gambarImageView;

        public BeritaRtViewHolder(@NonNull View itemView) {
            super(itemView);
            judulTextview = itemView.findViewById(R.id.judulTextView);
            gambarImageView = itemView.findViewById(R.id.gambarImageView);
        }
    }
}
