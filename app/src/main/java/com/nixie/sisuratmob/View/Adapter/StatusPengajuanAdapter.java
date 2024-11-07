package com.nixie.sisuratmob.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nixie.sisuratmob.Models.RiwayatSurat;
import com.nixie.sisuratmob.Models.Surat;
import com.nixie.sisuratmob.R;
import com.nixie.sisuratmob.View.StatusSuratActivity;

import java.util.ArrayList;
import java.util.List;

public class StatusPengajuanAdapter extends RecyclerView.Adapter<StatusPengajuanAdapter.StatusPengajuanViewHolder>{
    private List<RiwayatSurat> listRiwayat;
    private Context context;

    public StatusPengajuanAdapter(List<RiwayatSurat> listRiwayat) {
        this.listRiwayat = listRiwayat;
    }

    @NonNull
    @Override
    public StatusPengajuanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diajukan, parent, false);
        return new StatusPengajuanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusPengajuanViewHolder holder, int position) {
        RiwayatSurat riwayatSurat = listRiwayat.get(position);
        holder.statusText.setText(riwayatSurat.getStatus());
        holder.nikText.setText(riwayatSurat.getNik());
        holder.createdAtText.setText(riwayatSurat.getCreated_at());
    }

    @Override
    public int getItemCount() {
        return listRiwayat.size();
    }

    static class StatusPengajuanViewHolder extends RecyclerView.ViewHolder {
        TextView nomorSuratText, statusText, nikText, createdAtText;
        public StatusPengajuanViewHolder(@NonNull View itemView) {
            super(itemView);
            statusText = itemView.findViewById(R.id.status);
            nikText = itemView.findViewById(R.id.nikText);
            createdAtText = itemView.findViewById(R.id.dateText);
        }
    }
}
