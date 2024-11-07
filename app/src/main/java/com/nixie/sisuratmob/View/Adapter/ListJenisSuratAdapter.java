package com.nixie.sisuratmob.View.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nixie.sisuratmob.Models.ListKkModel;
import com.nixie.sisuratmob.Models.Surat;
import com.nixie.sisuratmob.R;

import java.util.List;

public class ListJenisSuratAdapter extends RecyclerView.Adapter<ListJenisSuratAdapter.DataViewHolder> {
    private List<Surat> dataList;

    // Constructor
    public ListJenisSuratAdapter(List<Surat> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_daftarsurat, parent, false);
        return new DataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        Surat data = dataList.get(position);
        holder.namaTextView.setText(data.getNama_surat());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        TextView namaTextView;

        public DataViewHolder(View itemView) {
            super(itemView);
            namaTextView = itemView.findViewById(R.id.jenistxt);
        }
    }
}
