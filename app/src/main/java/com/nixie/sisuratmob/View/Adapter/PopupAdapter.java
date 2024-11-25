package com.nixie.sisuratmob.View.Adapter;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nixie.sisuratmob.Models.DetailHistoriModel;
import com.nixie.sisuratmob.Models.LampiranSuratModel;
import com.nixie.sisuratmob.R;
import com.nixie.sisuratmob.komponen.ImagePickerCallback;

import java.util.List;

public class PopupAdapter extends RecyclerView.Adapter<PopupAdapter.ViewHolder> {
    private final List<DetailHistoriModel> lampiranList;

    public PopupAdapter(List<DetailHistoriModel> lampiranList) {
        this.lampiranList = lampiranList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout for each RecyclerView item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_button_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DetailHistoriModel lampiran = lampiranList.get(position);

        // Set the title of the lampiran
        holder.textTitle.setText("Silahkan Masukan " + lampiran.getNama_lampiran());
        holder.titlelampiran.setText(lampiran.getNama_lampiran());
        Log.d("TAG", "onBindViewHolder: "+lampiran.getUrl());
        holder.setImage(lampiran.getUrl());
    }

    @Override
    public int getItemCount() {
        return lampiranList.size();
    }

    // Method to update the image URI for a specific item
//    public void updateImage(Uri imageUri, int position) {
//        if (position >= 0 && position < lampiranList.size()) {
//            lampiranList.get(position).setImageUri(imageUri);
//            notifyItemChanged(position);  // Notify the item to refresh with new image
//        }
//    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout button;
        TextView textTitle;
        TextView titlelampiran;
        TextView textLabel;
        ImageView imageViewLampiran;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize views using findViewById
            button = itemView.findViewById(R.id.button_pilih_file);
            textTitle = itemView.findViewById(R.id.texttitleflampiran);
            titlelampiran = itemView.findViewById(R.id.titlelampilarn);
            textLabel = itemView.findViewById(R.id.textView6);
            imageViewLampiran = itemView.findViewById(R.id.imageViewLampiran);
        }

        public void setImage(String imageUri) {
            if (imageUri != null) {
                // Gunakan Glide untuk memuat gambar
                Glide.with(imageViewLampiran.getContext())
                        .load("http://192.168.1.7/SISURAT/admin/assetsmasyarakat/"+imageUri)
                        .into(imageViewLampiran);

                imageViewLampiran.setVisibility(View.VISIBLE);
                textLabel.setVisibility(View.GONE);  // Sembunyikan label teks saat gambar ditampilkan
                textTitle.setVisibility(View.GONE);  // Opsional: sembunyikan teks title
            } else {
                // Sembunyikan ImageView jika gambar tidak ada
                imageViewLampiran.setVisibility(View.GONE);
                textLabel.setVisibility(View.VISIBLE); // Tampilkan label teks jika tidak ada gambar
                textTitle.setVisibility(View.VISIBLE); // Opsional: tampilkan teks title
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }
//    public List<LampiranSuratModel> getLampiranList() {
//        return lampiranList;
//    }
}


