package com.nixie.sisuratmob.View.Adapter;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.nixie.sisuratmob.Models.LampiranSuratModel;
import com.nixie.sisuratmob.R;
import com.nixie.sisuratmob.komponen.ImagePickerCallback;

import java.util.List;

public class LampiranAdapter extends RecyclerView.Adapter<LampiranAdapter.ViewHolder> {
    private final List<LampiranSuratModel> lampiranList;
    private final ImagePickerCallback callback;

    public LampiranAdapter(List<LampiranSuratModel> lampiranList, ImagePickerCallback callback) {
        this.lampiranList = lampiranList;
        this.callback = callback;
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
        LampiranSuratModel lampiran = lampiranList.get(position);

        // Set the title of the lampiran
        holder.textTitle.setText("Silahkan Masukan " + lampiran.getNamaLampiran());
        holder.titlelampiran.setText(lampiran.getNamaLampiran());

        // Handle button click to pick an image
        holder.button.setOnClickListener(v -> {
            if (callback != null) {
                callback.onPickImage(position);
            }
        });

        // Set the image if it exists
        if (lampiran.getImageUri() != null) {
            holder.setImage(lampiran.getImageUri());
        } else {
            holder.setImage(null);  // No image, hide the ImageView
        }
    }

    @Override
    public int getItemCount() {
        return lampiranList.size();
    }

    // Method to update the image URI for a specific item
    public void updateImage(Uri imageUri, int position) {
        if (position >= 0 && position < lampiranList.size()) {
            lampiranList.get(position).setImageUri(imageUri);
            notifyItemChanged(position);  // Notify the item to refresh with new image
        }
    }

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

        public void setImage(Uri imageUri) {
            if (imageUri != null) {
                imageViewLampiran.setImageURI(imageUri);
                imageViewLampiran.setVisibility(View.VISIBLE);
                textLabel.setVisibility(View.GONE);  // Hide text label when image is shown
                textTitle.setVisibility(View.GONE); // Optionally hide textTitle when image is shown
            } else {
                imageViewLampiran.setVisibility(View.GONE);
                textLabel.setVisibility(View.VISIBLE); // Show textLabel if no image
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (!payloads.isEmpty() && payloads.get(0) instanceof Uri) {
            Uri imageUri = (Uri) payloads.get(0);
            holder.setImage(imageUri);
        } else {
            super.onBindViewHolder(holder, position, payloads);
        }
    }
    public List<LampiranSuratModel> getLampiranList() {
        return lampiranList;
    }
}


