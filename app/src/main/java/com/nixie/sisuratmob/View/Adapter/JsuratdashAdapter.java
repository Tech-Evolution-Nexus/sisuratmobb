package com.nixie.sisuratmob.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nixie.sisuratmob.Helpers.Helpers;
import com.nixie.sisuratmob.Models.ListKkModel;
import com.nixie.sisuratmob.Models.Surat;
import com.nixie.sisuratmob.R;
import com.nixie.sisuratmob.View.ListKeluargaActivity;

import java.util.List;

public class JsuratdashAdapter extends RecyclerView.Adapter<JsuratdashAdapter.DataViewHolder> {
    private List<Surat> dataList;
    private Context context;

    // Constructor
    public JsuratdashAdapter(Context ctx, List<Surat> dataList) {
        this.context = ctx;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_jsurat_dash, parent, false);
        return new DataViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {

        Surat data = dataList.get(position);
        holder.namaTextView.setText(data.getNama_surat());
        // Get the ConstraintLayout of the current item
        GridLayoutManager.LayoutParams layoutParams = (GridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();

        // Apply margins based on even or odd position
        if (position % 2 == 0) {
            // Even index: MarginRight = 8dp, MarginLeft = 4dp
            layoutParams.setMargins(
                    dpToPx(context, 4),  // Left margin
                    8,                   // Top margin
                    dpToPx(context, 8),  // Right margin
                    8                    // Bottom margin
            );
        } else {
            // Odd index: MarginLeft = 8dp, MarginRight = 4dp
            layoutParams.setMargins(
                    dpToPx(context, 8),  // Left margin
                    8,                   // Top margin
                    dpToPx(context, 4),  // Right margin
                    8                    // Bottom margin
            );
        }
        Glide.with(context)
                .load(Helpers.BASE_URL+"admin/assetssurat/"+data.getImage())
                .placeholder(R.drawable.baground_rtrw)
                .error(R.drawable.baground_rtrw)
                .into(holder.imageView);

        holder.itemView.setLayoutParams(layoutParams);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ListKeluargaActivity.class);
            intent.putExtra("id_surat", data.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder {
        TextView namaTextView;
        ImageView imageView;

        public DataViewHolder(View itemView) {
            super(itemView);
            namaTextView = itemView.findViewById(R.id.namej);
            imageView = itemView.findViewById(R.id.icj);

        }
    }
    private int dpToPx(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density);
    }
}

