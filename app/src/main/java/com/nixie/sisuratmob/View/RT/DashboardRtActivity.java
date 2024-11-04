package com.nixie.sisuratmob.View.RT;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.nixie.sisuratmob.Adapter.Warga.BeritaAdapter;
import com.nixie.sisuratmob.Models.Warga.Berita;
import com.nixie.sisuratmob.R;
import com.nixie.sisuratmob.View.Warga.Activity.DasboardFragment;
import com.nixie.sisuratmob.View.Warga.Activity.HomeFragment;
import com.nixie.sisuratmob.View.Warga.Activity.ProfileFragment;
import com.nixie.sisuratmob.View.Warga.Activity.StatusSuratFragment;
import com.nixie.sisuratmob.databinding.ActivityDashboardRtBinding;

import java.util.List;

public class DashboardRtActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_rt);
        bottomNavigationView = findViewById(R.id.btm_viewRt);


        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            loadFragment(new DashboardRtFragment());  // Memuat DashboardRtFragment pertama kali
            bottomNavigationView.setSelectedItemId(R.id.home);
        }
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            if (item.getItemId() == R.id.home) {
                selectedFragment = new DashboardRtFragment();
            } else if (item.getItemId() == R.id.surat) {
                selectedFragment = new Surat_Rt_Fragment();
            } else if (item.getItemId() == R.id.rekap) {
                selectedFragment = new Recap_Rt_Fragment();
            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
            }
            return true;

        });



    }


    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_dashboard_rt);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}