package com.nixie.sisuratmob.View.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nixie.sisuratmob.Models.ListKkModel;
import com.nixie.sisuratmob.R;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class ListKkAdapter extends RecyclerView.Adapter<ListKkAdapter.DataViewHolder> {
    private List<ListKkModel> dataList;

    // Constructor
    public ListKkAdapter(List<ListKkModel> dataList) {
        this.dataList = dataList;
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
        holder.nikTextView.setText(data.getNomorKK());
        holder.namaTextView.setText(data.getnama());
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