package com.nixie.sisuratmob.View.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nixie.sisuratmob.Models.BeritaRw;
import com.nixie.sisuratmob.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BeritaRwAdapter extends RecyclerView.Adapter<BeritaRwAdapter.BeritaRwViewHolder> {

    private Context context;
    private List<BeritaRw> beritaRwList;

    public BeritaRwAdapter(Context context, List<BeritaRw> beritaRwList) {
        this.context = context;
        this.beritaRwList = beritaRwList;
    }

    @NonNull
    @Override
    public BeritaRwViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_berita, parent, false);
        return new BeritaRwViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BeritaRwViewHolder holder, int position) {
        BeritaRw beritaRw = beritaRwList.get(position);

        // Mengatur judul
        holder.judulTextView.setText(beritaRw.getJudul());

        // Mengatur gambar menggunakan Picasso
        Picasso.get().load(beritaRw.getGambarUrl()).into(holder.gambarImageView);
    }

    @Override
    public int getItemCount() {
        return beritaRwList.size();
    }

    public static class BeritaRwViewHolder extends RecyclerView.ViewHolder {
        TextView judulTextView;
        ImageView gambarImageView;

        public BeritaRwViewHolder(@NonNull View itemView) {
            super(itemView);
            judulTextView = itemView.findViewById(R.id.titleberita);
            gambarImageView = itemView.findViewById(R.id.gambarberita);
        }
    }
}
