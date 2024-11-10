package com.nixie.sisuratmob.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nixie.sisuratmob.Models.ListKkModel;
import com.nixie.sisuratmob.R;
import com.nixie.sisuratmob.View.FormPengajuanActivity;
import com.nixie.sisuratmob.View.ListKeluargaActivity;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


    public class ListKkAdapter extends RecyclerView.Adapter<ListKkAdapter.DataViewHolder> {
        private List<ListKkModel> dataList;
        private Context context;
        private String datasurat;
        // Constructor
        public ListKkAdapter(Context context,List<ListKkModel> dataList,String datasurat) {
            this.dataList = dataList;
            this.context =context;
            this.datasurat =datasurat;
        }

        @NonNull
        @Override
        public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.listitemkk, parent, false);
            return new DataViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
            ListKkModel data = dataList.get(position);
            holder.nikTextView.setText(data.getNik());
            holder.namaTextView.setText(data.getNama_lengkap());
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, FormPengajuanActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// Replace with your desired activity
                intent.putExtra("id_surat", datasurat);
                intent.putExtra("nik", data.getNik());
                context.startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public static class DataViewHolder extends RecyclerView.ViewHolder {
            TextView nikTextView, namaTextView;

            public DataViewHolder(View itemView) {
                super(itemView);
                nikTextView = itemView.findViewById(R.id.nama);
                namaTextView = itemView.findViewById(R.id.listnik);
            }
        }
    }


