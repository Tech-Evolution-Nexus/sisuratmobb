// DashboardRtFragment.java
package com.nixie.sisuratmob.View;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.nixie.sisuratmob.Models.Berita;
import com.nixie.sisuratmob.Models.Surat;
import com.nixie.sisuratmob.R;
import com.nixie.sisuratmob.View.Adapter.BeritaAdapter;

import java.util.ArrayList;
import java.util.List;

public class DashboardRtFragment extends Fragment {

    private RecyclerView recyclerView;
    private BeritaAdapter beritaRtAdapter;
    private List<Berita> beritaRtList;
    private ImageView icon;

    public DashboardRtFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard_rt, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewBerita);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        beritaRtList = new ArrayList<>();
        beritaRtAdapter = new BeritaRtAdapter(getActivity(), beritaRtList);
        recyclerView.setAdapter(beritaRtAdapter);

        recyclerView.setAdapter(beritaRtAdapter);
        recyclerView.setHasFixedSize(true);
        ambilDataBerita();

        icon = view.findViewById(R.id.iconn);
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionsDialog();
            }
        });
        return view;
    }

    private void showOptionsDialog() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.options_dialog);
        dialog.setCancelable(true);

        Button aboutAppButton = dialog.findViewById(R.id.aboutAppButton);
        Button logoutButton = dialog.findViewById(R.id.logoutButton);

        aboutAppButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                showAboutApp();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                logoutUser();
            }
        });


        Window window = dialog.getWindow();
        if (window != null) {
            // Mengatur posisi dialog di kanan atas
            window.setGravity(Gravity.TOP | Gravity.RIGHT);

            // Mengatur margin atau jarak dari tepi
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.x = 30; // Jarak dari kanan (untuk memberi jarak dengan ikon)
            layoutParams.y = 40; // Jarak dari atas (ubah sesuai keinginan)
            window.setAttributes(layoutParams);
        }

        dialog.show();
    }

    private void showAboutApp() {
        Intent intent = new Intent(getActivity(), DashboardActivity.class);
        startActivity(intent);
    }

    private void logoutUser() {
        Toast.makeText(getActivity(), "Logout Berhasil", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void ambilDataBerita() {
        beritaRtList.add(new BeritaRt("Pengembangan aplikasi ", "Sub Judul 1", "Deskripsi 1", R.drawable.berita));
        beritaRtList.add(new BeritaRt("Pengembangan aplikasi2", "Sub Judul 2", "Deskripsi 2", R.drawable.beritaw));
        beritaRtList.add(new BeritaRt("Pengembangan aplikasi2", "Sub Judul 2", "Deskripsi 2", R.drawable.beritaw));
        beritaRtList.add(new BeritaRt("Pengembangan aplikasi", "sda","",1));
        beritaRtList.add(new BeritaRt("Pengembangan aplikasi", "sda","",1));
        beritaRtList.add(new BeritaRt("Pengembangan aplikasi", "sda","",1));
        beritaRtList.add(new BeritaRt("Pengembangan aplikasi", "sda","",1));
        beritaRtList.add(new BeritaRt("Pengembangan aplikasi", "sda","",1));
        beritaRtList.add(new BeritaRt("Pengembangan aplikasi", "sda","",1));
        beritaRtList.add(new BeritaRt("Pengembangan aplikasi", "sda","",1));
        // Tambahkan berita lain sesuai kebutuhan
        beritaRtAdapter.notifyDataSetChanged();
    }
}
