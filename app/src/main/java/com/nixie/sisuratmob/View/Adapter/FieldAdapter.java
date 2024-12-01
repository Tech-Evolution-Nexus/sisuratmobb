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
import com.nixie.sisuratmob.Models.FormModel;
import com.nixie.sisuratmob.Models.LampiranSuratModel;
import com.nixie.sisuratmob.R;
import com.nixie.sisuratmob.komponen.ImagePickerCallback;

import java.util.List;

public class FieldAdapter extends RecyclerView.Adapter<FieldAdapter.ViewHolder> {
    private final List<FieldModel> lampiranList;
    private RecyclerView recyclerView;

    public FieldAdapter(List<FieldModel> lampiranList, RecyclerView recyclerView) {
        this.lampiranList = lampiranList;
        this.recyclerView = recyclerView;
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
        FieldModel lampiran = lampiranList.get(position);
        holder.elfield.setHint(lampiran.getNama_field().replace("_", " "));
//        FieldValue fieldValue = findFieldValueByFieldId(field.getId());
        holder.efield.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable editable) {
                lampiran.setValue(editable.toString());
                holder.elfield.setError(null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lampiranList.size();
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
    public List<FieldModel> getList() {
        return lampiranList;
    }
    public boolean validateFields() {
        boolean isValid = true;
        for (int i = 0; i < getItemCount(); i++) {
            ViewHolder holder = (ViewHolder) recyclerView.findViewHolderForAdapterPosition(i);
            if (holder != null) {
                String text = holder.efield.getText().toString().trim();
                String namaField = lampiranList.get(i).getNama_field().replace("_", " ");
                if (text.isEmpty()) {
                    holder.elfield.setError(namaField+" tidak boleh kosong");
                    isValid = false;
                }
            }
        }
        return isValid;
    }
}
