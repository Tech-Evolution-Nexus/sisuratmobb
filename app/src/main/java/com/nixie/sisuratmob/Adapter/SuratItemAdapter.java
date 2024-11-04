package com.nixie.sisuratmob.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nixie.sisuratmob.Models.Surat;
import com.nixie.sisuratmob.R;
import com.nixie.sisuratmob.View.StatusSuratActivity;

import java.util.List;

public class SuratItemAdapter extends RecyclerView.Adapter<SuratItemAdapter.SuratViewHolder> {
    private List<Surat> listSurat;
    private Context context;

    public SuratItemAdapter(Context context, List<Surat> listSurat) {
        this.context = context;
        this.listSurat = listSurat;
    }

    @NonNull
    @Override
    public SuratViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_surat, parent, false);
        return new SuratViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SuratViewHolder holder, int position) {
        Surat surat = listSurat.get(position);
        holder.gambarSurat.setImageResource(surat.getGambarSurat());
        holder.namaSurat.setText(surat.getNamaSurat());

        // Handle click event to open new activity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, StatusSuratActivity.class);
            intent.putExtra("NAMA_SURAT", surat.getNamaSurat());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listSurat.size();
    }

    static class SuratViewHolder extends RecyclerView.ViewHolder {
        ImageView gambarSurat;
        TextView namaSurat;

        public SuratViewHolder(@NonNull View itemView) {
            super(itemView);
            gambarSurat = itemView.findViewById(R.id.gambarSurat);
            namaSurat = itemView.findViewById(R.id.namaSurat);
        }
    }
}
