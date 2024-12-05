package com.nixie.sisuratmob.View;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.nixie.sisuratmob.R;
import com.nixie.sisuratmob.View.PengajuanSurat.ApprovalPengajuanFragment;
import com.nixie.sisuratmob.databinding.ActivityDashboardRtBinding;

public class DashboardRtActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityDashboardRtBinding binding;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDashboardRtBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String role = sharedPreferences.getString("role", "");
        bottomNavigationView = findViewById(R.id.btm_viewRt);
        if(role.equals("rw")){
            bottomNavigationView.getMenu().findItem(R.id.menuverifmas).setVisible(false);
        }

        if (savedInstanceState == null) {
            loadFragment(new DashboardRtFragment());
            bottomNavigationView.setSelectedItemId(R.id.home);
        }

        // Set up navigation item selection listener
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            if(role.equals("rt")){
                if (item.getItemId() == R.id.home) {
                    selectedFragment = new DashboardRtFragment();
                } else if (item.getItemId() == R.id.surat) {
                    selectedFragment = new ApprovalPengajuanFragment();
                } else if (item.getItemId() == R.id.menuverifmas) {
                    selectedFragment = new VerifikasiMasyarakatFragment();
                }
                else if (item.getItemId() == R.id.menuprofile) {
                    selectedFragment = new ProfileFragment();
                }
            }else{
                if (item.getItemId() == R.id.home) {
                    selectedFragment = new DashboardRtFragment();
                } else if (item.getItemId() == R.id.surat) {
                    selectedFragment = new ApprovalPengajuanFragment();
                }
                else if (item.getItemId() == R.id.menuprofile) {
                    selectedFragment = new ProfileFragment();
                }
            }


            if (selectedFragment != null) {
                loadFragment(selectedFragment);
            }
            return true;
        });
        invalidateOptionsMenu();

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_dashboard_rt);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}
