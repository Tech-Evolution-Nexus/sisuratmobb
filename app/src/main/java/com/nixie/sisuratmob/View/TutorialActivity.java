package com.nixie.sisuratmob.View;

// TutorialActivity.java
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.nixie.sisuratmob.View.Adapter.TutorialPagerAdapter;
import com.nixie.sisuratmob.R;

public class TutorialActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private Button doneButton, nextButton, backButton;
    private TutorialPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        viewPager = findViewById(R.id.viewPager);
        doneButton = findViewById(R.id.doneButton);
        nextButton = findViewById(R.id.nextButton);
        backButton = findViewById(R.id.backButton);

        adapter = new TutorialPagerAdapter(this);
        viewPager.setAdapter(adapter);

        doneButton.setOnClickListener(v -> {
            Intent intent = new Intent(TutorialActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        // Menangani Tombol Next
        nextButton.setOnClickListener(v -> {
            int currentItem = viewPager.getCurrentItem();
            if (currentItem < adapter.getCount() - 1) {
                viewPager.setCurrentItem(currentItem + 1);
            }
        });

        // Menangani Tombol Back
        backButton.setOnClickListener(v -> {
            int currentItem = viewPager.getCurrentItem();
            if (currentItem > 0) {
                viewPager.setCurrentItem(currentItem - 1);
            }
        });

        // Menangani perubahan halaman pada ViewPager
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // Tombol "Selesai" hanya muncul di slide terakhir
                doneButton.setVisibility(position == adapter.getCount() - 1 ? View.VISIBLE : View.GONE);

                // Tombol Back hanya muncul di slide selain pertama
                backButton.setVisibility(position == 0 ? View.GONE : View.VISIBLE);

                // Tombol Next hanya muncul di slide selain terakhir
                nextButton.setVisibility(position == adapter.getCount() - 1 ? View.GONE : View.VISIBLE);
            }
        });
    }
}
