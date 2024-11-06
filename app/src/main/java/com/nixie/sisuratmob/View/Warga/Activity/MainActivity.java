package com.nixie.sisuratmob.View.Warga.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.nixie.sisuratmob.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

       new Handler().postDelayed(() ->{
           Intent intent = new Intent(MainActivity.this,TutorialActivity.class);
           startActivity(intent);
           finish();
        }, 5000 );

    }
}