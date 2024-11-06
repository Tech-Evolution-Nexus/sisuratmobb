package com.nixie.sisuratmob.View.Adapter;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.nixie.sisuratmob.Models.LampiranSuratModel;
import com.nixie.sisuratmob.R;

import java.util.List;

public class LampiranAdapter extends RecyclerView.Adapter<LampiranAdapter.ViewHolder> {

    private List<LampiranSuratModel> lampiranList;;

    public LampiranAdapter(List<LampiranSuratModel> lampiranList) {
        this.lampiranList = lampiranList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_button_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LampiranSuratModel lampiran = lampiranList.get(position);

        holder.texttitle.setText("Silahkan Masukan "+lampiran.getNamaLampiran());

        holder.button.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "Tombol diklik"+position, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return lampiranList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout button;
        TextView texttitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.button_pilih_file);
            texttitle=itemView.findViewById(R.id.texttitleflampiran);
        }
    }
}
