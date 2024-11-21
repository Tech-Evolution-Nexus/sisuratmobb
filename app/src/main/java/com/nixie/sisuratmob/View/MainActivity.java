package com.nixie.sisuratmob.View;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.nixie.sisuratmob.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        FirebaseApp.initializeApp(this);
        FirebaseMessaging.getInstance().subscribeToTopic("global");
       new Handler().postDelayed(() ->{

           SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
           boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
           String role = sharedPreferences.getString("role", "");
           if (!isLoggedIn) {
               Intent intent = new Intent(MainActivity.this,TutorialActivity.class);
               startActivity(intent);
               finish();
           }else{
               if(role.equals("masyarakat")){
                   Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                   startActivity(intent);
                   finish();
               }else if(role.equals("rt")||role.equals("rw")){
                   Intent intent = new Intent(MainActivity.this, DashboardRtActivity.class);
                   startActivity(intent);
                   finish();
               }else {
                   Intent intent = new Intent(MainActivity.this,TutorialActivity.class);
                   startActivity(intent);
                   finish();
               }
           }
        }, 5000 );

    }
}