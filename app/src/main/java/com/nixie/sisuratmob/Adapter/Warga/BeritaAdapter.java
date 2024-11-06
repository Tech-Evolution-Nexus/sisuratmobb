package com.nixie.sisuratmob.Adapter.Warga;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nixie.sisuratmob.Models.Warga.Berita;
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
        holder.gambarImageView.setImageResource(berita.getGambarUrl()); // Menggunakan resource ID
    }
@Override
    public int getItemCount() {
        return beritaList.size();
    }

    public static class BeritaViewHolder extends RecyclerView.ViewHolder {
        TextView judulTextView;
        ImageView gambarImageView;

        public BeritaViewHolder(@NonNull View itemView) {
            super(itemView);
            judulTextView = itemView.findViewById(R.id.judulTextView);
            gambarImageView = itemView.findViewById(R.id.gambarImageView);
        }
    }
}
