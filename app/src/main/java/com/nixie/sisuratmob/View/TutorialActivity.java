package com.nixie.sisuratmob.View;

// TutorialActivity.java
 // Sesuaikan dengan package Anda

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.nixie.sisuratmob.Adapter.TutorialPagerAdapter;
import com.nixie.sisuratmob.R;

public class TutorialActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private Button doneButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        viewPager = findViewById(R.id.viewPager);
        doneButton = findViewById(R.id.doneButton);
        TutorialPagerAdapter adapter = new TutorialPagerAdapter(this);
        viewPager.setAdapter(adapter);

        doneButton.setOnClickListener(v -> {
            Intent intent = new Intent(TutorialActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                doneButton.setVisibility(position == adapter.getCount() - 1 ? View.VISIBLE : View.GONE);
            }
        });
    }
}
