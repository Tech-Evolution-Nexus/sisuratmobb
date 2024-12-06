package com.nixie.sisuratmob.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nixie.sisuratmob.Helpers.Helpers;
import com.nixie.sisuratmob.View.DetailBritaActivity;
import com.nixie.sisuratmob.Models.Berita;
import com.nixie.sisuratmob.R;

import java.util.List;

public class BeritaAdapter extends RecyclerView.Adapter<BeritaAdapter.BeritaViewHolder> {

    private Context context;
    private List<Berita> beritaList;

    public BeritaAdapter(Context context, List<Berita> beritaList) {
        this.context = context;
        this.beritaList = beritaList;
    }

    @NonNull
    @Override
    public BeritaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_berita, parent, false);
        return new BeritaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BeritaViewHolder holder, int position) {
        Berita berita = beritaList.get(position);
        holder.judulTextView.setText(berita.getJudul());
        holder.tglTextView.setText(Helpers.formatTanggalnoday(berita.getCreated_at()));
//        holder.gambarImageView.setImageResource(berita.getGambarUrl());
                Glide.with(context)
                .load(Helpers.BASE_URL+"admin/assetsberita/"+berita.getGambar())
                .placeholder(R.drawable.baground_rtrw)
                .error(R.drawable.baground_rtrw)
                .into(holder.gambarImageView);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailBritaActivity.class); // Replace with your desired activity
            intent.putExtra("id_berita", String.valueOf(berita.getId()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return beritaList.size();
    }

    public static class BeritaViewHolder extends RecyclerView.ViewHolder {
        TextView judulTextView,tglTextView;
        ImageView gambarImageView;

        public BeritaViewHolder(@NonNull View itemView) {
            super(itemView);
            judulTextView = itemView.findViewById(R.id.titleberita);
            gambarImageView = itemView.findViewById(R.id.gambarberita);
            tglTextView = itemView.findViewById(R.id.tglberita);

        }
    }
}
