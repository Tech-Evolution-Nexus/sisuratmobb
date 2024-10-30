package com.nixie.sisuratmob.View;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nixie.sisuratmob.R;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dashboard);

        // Memuat DashboardFragment secara default saat activity dibuka
        if (savedInstanceState == null) {
            loadFragment(new DasboardFragment()); // Ganti dengan fragment yang sesuai
        }

        // Mengatur BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                if (item.getItemId() == R.id.nav_home) {
                    selectedFragment = new DasboardFragment(); // Fragment untuk halaman Home
                } else if (item.getItemId() == R.id.nav_statussurat) {
                    selectedFragment = new StatusSuratFragment(); // Fragment untuk halaman Status Surat
                } else if (item.getItemId() == R.id.nav_profile) {
                    selectedFragment = new ProfileFragment(); // Fragment untuk halaman Profil
                }

                if (selectedFragment != null) {
                    loadFragment(selectedFragment);
                }
                return true;
            }
        });
    }

    // Fungsi untuk memuat fragment
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment); // Ganti dengan ID layout container fragment Anda
        transaction.commit();
    }
}
