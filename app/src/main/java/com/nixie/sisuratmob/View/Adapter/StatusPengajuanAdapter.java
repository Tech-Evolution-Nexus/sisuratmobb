package com.nixie.sisuratmob.View.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nixie.sisuratmob.Models.RiwayatSurat;
import com.nixie.sisuratmob.R;
import com.nixie.sisuratmob.komponen.DataPopup;

import java.util.List;

public class StatusPengajuanAdapter extends RecyclerView.Adapter<StatusPengajuanAdapter.StatusPengajuanViewHolder>{
    private List<RiwayatSurat> listRiwayat;
    private Context context;
    private Fragment fragment;

    public StatusPengajuanAdapter(Context context, List<RiwayatSurat> listRiwayat, Fragment fragment) {
        this.context = context;
        this.listRiwayat = listRiwayat;
        this.fragment = fragment;
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
//        holder.nikText.setText(riwayatSurat.getNik());
        holder.createdAtText.setText(riwayatSurat.getCreated_at());
        Glide.with(context)
                .load("http://192.168.100.205/SISURAT/admin/assetssurat/"+riwayatSurat.getImage())
                .placeholder(R.drawable.baground_rtrw)
                .error(R.drawable.baground_rtrw)
                .into(holder.img);
        holder.icbtn.setOnClickListener(v -> {
            DataPopup biodataDialog = new DataPopup();
            Bundle bundle = new Bundle();
            bundle.putString("title", riwayatSurat.getNama_surat());
            bundle.putString("status", riwayatSurat.getStatus());
            bundle.putString("date", riwayatSurat.getCreated_at());
            bundle.putString("nik", riwayatSurat.getNik());
            bundle.putInt("idpengajuan", riwayatSurat.getId());



            biodataDialog.setArguments(bundle);
            biodataDialog.show(fragment.getChildFragmentManager(), "BiodataDialog");
            });
    }

    @Override
    public int getItemCount() {
        return listRiwayat.size();
    }

    static class StatusPengajuanViewHolder extends RecyclerView.ViewHolder {
        TextView nomorSuratText, statusText, nikText, createdAtText;
        ImageView img,icbtn;
        public StatusPengajuanViewHolder(@NonNull View itemView) {
            super(itemView);
            statusText = itemView.findViewById(R.id.status);
//            nikText = itemView.findViewById(R.id.nikText);
            createdAtText = itemView.findViewById(R.id.dateText);
            img = itemView.findViewById(R.id.imagesurataju);
            icbtn = itemView.findViewById(R.id.infoIcon);
        }
    }
}
