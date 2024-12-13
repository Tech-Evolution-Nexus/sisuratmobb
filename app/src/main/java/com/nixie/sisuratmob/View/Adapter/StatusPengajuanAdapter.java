package com.nixie.sisuratmob.View.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
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
import com.nixie.sisuratmob.Helpers.Helpers;
import com.nixie.sisuratmob.Models.RiwayatSurat;
import com.nixie.sisuratmob.R;
import com.nixie.sisuratmob.komponen.DataPopup;
import com.nixie.sisuratmob.komponen.DataPopup2;

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
        String status = riwayatSurat.getStatus();
        String formattedStatus;
        int backgroundColor;
        switch (status) {
            case "pending":
                formattedStatus = "Menunggu Persetujuan";
                backgroundColor = 0xFFE0A800;
                break;
            case "di_terima_rt":
                formattedStatus = "Diterima oleh RT";
                backgroundColor = 0xFF117A8B;
                break;
            case "di_terima_rw":
                formattedStatus = "Diterima oleh RW";
                backgroundColor = 0xFF117A8B;
                break;
            case "di_tolak_rt":
                formattedStatus = "Ditolak oleh RT";
                backgroundColor = 0xFFC82333;
                break;
            case "di_tolak_rw":
                formattedStatus = "Ditolak oleh RW";
                backgroundColor = 0xFFC82333;
                break;
            case "dibatalkan":
                formattedStatus = "Dibatalkan";
                backgroundColor = 0xFFC82333;
                break;
            case "selesai":
                formattedStatus = "Proses Selesai";
                backgroundColor = 0xFF218838;
                break;
            default:
                formattedStatus = "Status Tidak Diketahui";
                backgroundColor = 0xFFE0A800;
                break;
        }
        holder.statusText.setText(formattedStatus);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(backgroundColor); // Set the background color
        drawable.setCornerRadius(16f); // Set the corner radius (e.g., 16dp)
        ColorStateList tintColor = ColorStateList.valueOf(backgroundColor);
        holder.icbtn.setImageTintList(tintColor);
        holder.statusText.setBackground(drawable);
        holder.createdAtText.setText(riwayatSurat.getCreated_at());
        holder.jenistext.setText(riwayatSurat.getNama_surat());
        Glide.with(context)
                .load(Helpers.BASE_URL+"admin/assetssurat/"+riwayatSurat.getImage())
                .placeholder(R.drawable.baground_rtrw)
                .error(R.drawable.baground_rtrw)
                .into(holder.img);
        holder.icbtn.setOnClickListener(v -> {
            DataPopup2 biodataDialog = new DataPopup2();
            Bundle bundle = new Bundle();
            bundle.putString("title", riwayatSurat.getNama_surat());
            bundle.putString("status", riwayatSurat.getStatus());
            bundle.putString("date", riwayatSurat.getCreated_at());
            bundle.putString("nik", riwayatSurat.getNik());
            bundle.putString("keterangan", riwayatSurat.getKeterangan());
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
        TextView jenistext,nomorSuratText, statusText, nikText, createdAtText;
        ImageView img,icbtn;
        public StatusPengajuanViewHolder(@NonNull View itemView) {
            super(itemView);
            statusText = itemView.findViewById(R.id.status);
//            nikText = itemView.findViewById(R.id.nikText);
            jenistext = itemView.findViewById(R.id.jenisText);

            createdAtText = itemView.findViewById(R.id.dateText);
            img = itemView.findViewById(R.id.imagesurataju);
            icbtn = itemView.findViewById(R.id.infoIcon);
        }
    }
}
