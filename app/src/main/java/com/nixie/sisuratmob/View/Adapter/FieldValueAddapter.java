package com.nixie.sisuratmob.View.Adapter;

import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.nixie.sisuratmob.Models.FieldModel;
import com.nixie.sisuratmob.Models.FieldValue;
import com.nixie.sisuratmob.Models.FormModel;
import com.nixie.sisuratmob.Models.LampiranSuratModel;
import com.nixie.sisuratmob.R;
import com.nixie.sisuratmob.komponen.ImagePickerCallback;

import java.util.List;

public class FieldValueAddapter extends RecyclerView.Adapter<FieldValueAddapter.ViewHolder> {
    private final List<FieldValue> fieldList;

    public FieldValueAddapter(List<FieldValue> fieldList) {
        this.fieldList = fieldList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout for each RecyclerView item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_edittext_field, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FieldValue field = fieldList.get(position);
        holder.elfield.setHint(field.getNama_field().replace("_", " "));
//        FieldValue fieldValue = findFieldValueByFieldId(field.getId());
       holder.efield.setText(field.getValue());
        holder.efield.setEnabled(false);

    }

    @Override
    public int getItemCount() {
        return fieldList.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextInputEditText efield;
        TextInputLayout elfield;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            elfield = itemView.findViewById(R.id.txtinputlayoutformrec);
            efield = itemView.findViewById(R.id.etfield);

        }
    }
}
