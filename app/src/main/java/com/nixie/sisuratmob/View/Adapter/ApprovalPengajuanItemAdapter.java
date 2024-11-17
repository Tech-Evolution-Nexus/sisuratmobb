package com.nixie.sisuratmob.View.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.nixie.sisuratmob.Helpers.Helpers;
import com.nixie.sisuratmob.Models.PengajuanSuratModel;
import com.nixie.sisuratmob.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ApprovalPengajuanItemAdapter extends RecyclerView.Adapter<ApprovalPengajuanItemAdapter.StatusPengajuanViewHolder>{
    private List<PengajuanSuratModel> listPengajuan;
    private Context context;
    private String type;

    public ApprovalPengajuanItemAdapter(Context context,List<PengajuanSuratModel> listPengajuan,String type) {
        this.listPengajuan = listPengajuan;
        this.type = type;
        this.context = context;
    }

    @NonNull
    @Override
    public StatusPengajuanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diajukan, parent, false);
        return new StatusPengajuanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusPengajuanViewHolder holder, int position) {
        PengajuanSuratModel pengajuan = listPengajuan.get(position);
        String formattedDate = Helpers.formatTanggal(pengajuan.getTanggal_pengajuan());
        String status;


        if(this.type == "pending"){
             status = pengajuan.getStatus().equals("pending")  || pengajuan.getStatus().equals("di_terima_rt")  ? "Menunggu Persetujuan" : "Selesai";
        }else{
             status = pengajuan.getStatus().equals("di_terima_rt")  || pengajuan.getStatus().equals("di_terima_rw")  || pengajuan.getStatus().equals("selesai")   ? "Selesai" : "Ditolak";
            if(pengajuan.getStatus().equals("di_terima_rt")  || pengajuan.getStatus().equals("di_terima_rw")  || pengajuan.getStatus().equals("selesai")   ){
                holder.statusText.setBackground(ContextCompat.getDrawable(context,R.drawable.bg_status_tosca));
            }else{
                holder.statusText.setBackground(ContextCompat.getDrawable(context,R.drawable.bg_status_danger));
            }
        }
        holder.statusText.setText(status);
        holder.nikText.setText(pengajuan.getNik());
        holder.tanggal_pengajuan.setText(formattedDate);
        holder.nama_surat.setText(pengajuan.getNama_surat());
        holder.nama_lengkap.setText(pengajuan.getNama_lengkap());
    }

    @Override
    public int getItemCount() {
        return listPengajuan.size();
    }

    static class StatusPengajuanViewHolder extends RecyclerView.ViewHolder {
        TextView nama_surat, statusText, nikText, tanggal_pengajuan,nama_lengkap;
        public StatusPengajuanViewHolder(@NonNull View itemView) {
            super(itemView);
            statusText = itemView.findViewById(R.id.status);
            nikText = itemView.findViewById(R.id.nikText);
            tanggal_pengajuan = itemView.findViewById(R.id.dateText);
            nama_lengkap = itemView.findViewById(R.id.nameText);
            nama_surat = itemView.findViewById(R.id.jenisText);
        }
    }


}
