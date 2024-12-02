package com.nixie.sisuratmob.View.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.nixie.sisuratmob.Models.VericikasimasModel;
import com.nixie.sisuratmob.R;
import com.nixie.sisuratmob.komponen.DataPopup;
import com.nixie.sisuratmob.komponen.DataPopup3;

import java.util.List;

public class VerivikasimasAdapter extends RecyclerView.Adapter<VerivikasimasAdapter.DataViewHolder> {
    private List<VericikasimasModel> dataList;
    private Context context;
    private Fragment fragment;

    // Constructor
    public VerivikasimasAdapter(Context context, List<VericikasimasModel> dataList,Fragment fragment) {
        this.dataList = dataList;
        this.context = context;
        this.fragment = fragment;

    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_verif_masyarakat, parent, false);
        return new DataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        VericikasimasModel data = dataList.get(position);
        holder.nikTextView.setText(data.getNik());
        holder.namaTextView.setText(data.getNama_lengkap());
        holder.iverifacc.setOnClickListener(v -> {
            DataPopup3 biodataDialog = new DataPopup3();
            Bundle bundle = new Bundle();
//            bundle.putString("title", riwayatSurat.getNama_surat());
//            bundle.putString("status", riwayatSurat.getStatus());
//            bundle.putString("date", riwayatSurat.getCreated_at());
            bundle.putString("nik", data.getNik());
//            bundle.putInt("idpengajuan", riwayatSurat.getId());
            biodataDialog.setArguments(bundle);
            biodataDialog.show(fragment.getChildFragmentManager(), "BiodataDialog");
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        TextView nikTextView, namaTextView;
        ImageView iverifacc;
        public DataViewHolder(View itemView) {
            super(itemView);
            nikTextView = itemView.findViewById(R.id.nikverifacc);
            namaTextView = itemView.findViewById(R.id.namaverifacc);
            iverifacc = itemView.findViewById(R.id.iconverifacc);
        }
    }
}
