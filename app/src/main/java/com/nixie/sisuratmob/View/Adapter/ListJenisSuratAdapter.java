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

import com.bumptech.glide.Glide;
import com.nixie.sisuratmob.Models.ListKkModel;
import com.nixie.sisuratmob.Models.Surat;
import com.nixie.sisuratmob.R;
import com.nixie.sisuratmob.View.ListKeluargaActivity;

import java.util.List;

public class ListJenisSuratAdapter extends RecyclerView.Adapter<ListJenisSuratAdapter.DataViewHolder> {
    private List<Surat> dataList;
    private Context context;
    // Constructor
    public ListJenisSuratAdapter(Context context,List<Surat> dataList) {
        this.dataList = dataList;
        this.context = context;
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
        Glide.with(context)
                .load("http://192.168.100.205/SISURAT/admin/assetssurat/"+data.getImage())
                .placeholder(R.drawable.baground_rtrw) // Gambar placeholder
                .error(R.drawable.baground_rtrw) // Gambar error jika URL tidak valid
                .into(holder.imageView);
        holder.itemView.setOnClickListener(v -> {
            // Handle the click event
            // For example, pass the Surat object to a new activity
            Intent intent = new Intent(context, ListKeluargaActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// Replace with your desired activity
            intent.putExtra("id_surat", data.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    public void updateList(List<Surat> filteredList) {
        dataList = filteredList;
        notifyDataSetChanged();
    }
    public static class DataViewHolder extends RecyclerView.ViewHolder {
        TextView namaTextView;
        ImageView imageView;
        public DataViewHolder(View itemView) {
            super(itemView);
            namaTextView = itemView.findViewById(R.id.jenistxt);
            imageView = itemView.findViewById(R.id.icsuratt);

        }
    }
}
