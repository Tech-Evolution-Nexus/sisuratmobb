package com.nixie.sisuratmob.View;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.nixie.sisuratmob.R;
import com.nixie.sisuratmob.View.Adapter.ViewPagerStatusAdapter;

public class StatusSuratActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_status_surat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager = findViewById(R.id.viewPager);

        // Set up adapter untuk ViewPager2
        ViewPagerStatusAdapter adapter = new ViewPagerStatusAdapter(this);
        viewPager.setAdapter(adapter);

        // Hubungkan ViewPager2 dengan TabLayout
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Surat Diajukan");
                            break;
                        case 1:
                            tab.setText("Surat Diproses");
                            break;
                        case 2:
                            tab.setText("Surat Selesai");
                            break;
                        case 3:
                            tab.setText("Surat Ditolak");
                            break;
                    }
                }
        ).attach();
    }
}