package com.nixie.sisuratmob.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.nixie.sisuratmob.Database.DbHelper;
import com.nixie.sisuratmob.Models.UserModel;
import com.nixie.sisuratmob.R;

public class ActivasiActivity extends AppCompatActivity {

    private TextInputEditText textNik;
    private TextInputEditText textnohp;
    private TextInputEditText textpassword;
    private TextInputEditText textconfirm;
    private Button activasimasuk;
    private TextView Login;

    DbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_activasi);

        textNik = findViewById(R.id.activasi_NIK);
        textnohp = findViewById(R.id.activasi_Nohp);
        textpassword = findViewById(R.id.activasi_password);
        textconfirm = findViewById(R.id.activasi_confirpasword);
        dbHelper = new DbHelper(this);
        activasimasuk = findViewById(R.id.activasi_masuk);
        Login = findViewById(R.id.Activasi);
        activasimasuk.setOnClickListener(v -> Activasi());


        Login.setOnClickListener(v ->{
            Intent intent = new Intent(ActivasiActivity.this,LoginActivity.class);
            startActivity(intent);
        });
    }

    public void Activasi(){
        String nik =textNik.getText().toString();
        String nohp =textnohp.getText().toString();
        String password =textpassword.getText().toString();
        String confirmpassword =textconfirm.getText().toString();

        if (nik.isEmpty() || nohp.isEmpty() || password.isEmpty() || confirmpassword.isEmpty()){
            Toast.makeText(this,"Semua kolom Wajib di isi", Toast.LENGTH_SHORT).show();
        return;
        }

        if (!password.equals(confirmpassword)){
            Toast.makeText(this, "password dan confirpassword harus sama", Toast.LENGTH_SHORT).show();
            return;
        }

        UserModel userModel = new UserModel(nik, nohp, password);
        dbHelper.addUser(userModel);
        Toast.makeText(this, "Activasi Berhasil", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(ActivasiActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}