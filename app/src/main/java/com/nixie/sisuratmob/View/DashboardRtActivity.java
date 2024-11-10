package com.nixie.sisuratmob.View;

import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.nixie.sisuratmob.R;
import com.nixie.sisuratmob.databinding.ActivityDashboardRtBinding;

public class DashboardRtActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityDashboardRtBinding binding;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the layout and set content view
        binding = ActivityDashboardRtBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize BottomNavigationView after setting the content view
        bottomNavigationView = findViewById(R.id.btm_viewRt);

        // Load DashboardFragment as the default fragment
        if (savedInstanceState == null) {
            loadFragment(new DashboardRtFragment()); // Correct the closing parenthesis
            bottomNavigationView.setSelectedItemId(R.id.home); // Correct method to select item
        }

        // Set up navigation item selection listener
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.home) {
                selectedFragment = new DashboardRtFragment();
            } else if (item.getItemId() == R.id.surat) {
                selectedFragment = new StatusSuratFragment();
            } else if (item.getItemId() == R.id.rekap) {
                selectedFragment = new ProfileFragment();
            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment);
            }
            return true;
        });
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
