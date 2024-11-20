package com.nixie.sisuratmob.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.nixie.sisuratmob.Api.ApiClient;
import com.nixie.sisuratmob.Api.ApiService;
import com.nixie.sisuratmob.Models.RegistrasiModel;
import com.nixie.sisuratmob.R;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText EditNik, EditPassword, EditNohp, EditNamalengkap, EditJeniskelamin;
    private TextInputEditText EditTempatlahir, EditTanggal, EditAgama, EditPendidikan, EditPekerjaan;
    private TextInputEditText EditStatusperkawinan, EditStatuskeluarga, EditKwarganegaraan, EditNamaayah;
    private TextInputEditText EditNamaibu, EditNokk, EditAlamat, EditRt, EditRw, EditKodepos, EditKelurahan;
    private TextInputEditText EditKecamatan, EditKabupaten, EditProvinsi, EditKkTanggal;
    private Spinner EditRole;
    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditNik = findViewById(R.id.registrasi_nik);
        EditPassword = findViewById(R.id.registrasi_password);
        EditNohp = findViewById(R.id.registrasi_no_hp);
        EditNamalengkap = findViewById(R.id.registrasi_nama_lengkap);
        EditJeniskelamin = findViewById(R.id.registrasi_jenis_kelamin);
        EditTempatlahir = findViewById(R.id.registrasi_tempat_lahir);
        EditTanggal = findViewById(R.id.registrasi_tgl_lahir);
        EditAgama = findViewById(R.id.registrasi_agama);
        EditPendidikan = findViewById(R.id.registrasi_pendidikan);
        EditPekerjaan = findViewById(R.id.registrasi_pekerjaan);
        EditStatusperkawinan = findViewById(R.id.registrasi_status_perkawinan);
        EditStatuskeluarga = findViewById(R.id.registrasi_status_keluarga);
        EditKwarganegaraan = findViewById(R.id.registrasi_kewarganegaraan);
        EditNamaayah = findViewById(R.id.registrasi_nama_ayah);
        EditNamaibu = findViewById(R.id.registrasi_nama_ibu);
        EditNokk = findViewById(R.id.registrasi_no_kk);
        EditAlamat = findViewById(R.id.registrasi_alamat);
        EditRt = findViewById(R.id.registrasi_rt);
        EditRw = findViewById(R.id.registrasi_rw);
        EditKodepos = findViewById(R.id.registrasi_kode_pos);
        EditKelurahan = findViewById(R.id.registrasi_kelurahan);
        EditKecamatan = findViewById(R.id.registrasi_kecamatan);
        EditKabupaten = findViewById(R.id.registrasi_kabupaten);
        EditProvinsi = findViewById(R.id.registrasi_provinsi);
        EditKkTanggal = findViewById(R.id.registrasi_kk_tgl);
        Spinner spinner = findViewById(R.id.registrasi_role);
        btn_register = findViewById(R.id.registrasi_button);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        // Ambil data dari EditText
        String nik = EditNik.getText().toString().trim();
        String password = EditPassword.getText().toString().trim();
        String noTelp = EditNohp.getText().toString().trim();
        String namaLengkap = EditNamalengkap.getText().toString().trim();
        String jenisKelamin = EditJeniskelamin.getText().toString().trim();
        String tempatLahir = EditTempatlahir.getText().toString().trim();
        String tanggalLahir = EditTanggal.getText().toString().trim();
        String agama = EditAgama.getText().toString().trim();
        String pendidikan = EditPendidikan.getText().toString().trim();
        String pekerjaan = EditPekerjaan.getText().toString().trim();
        String statusPernikahan = EditStatusperkawinan.getText().toString().trim();
        String statusKeluarga = EditStatuskeluarga.getText().toString().trim();
        String kewarganegaraan = EditKwarganegaraan.getText().toString().trim();
        String namaAyah = EditNamaayah.getText().toString().trim();
        String namaIbu = EditNamaibu.getText().toString().trim();
        String noKK = EditNokk.getText().toString().trim();
        String alamat = EditAlamat.getText().toString().trim();
        String rt = EditRt.getText().toString().trim();
        String rw = EditRw.getText().toString().trim();
        String kodepos = EditKodepos.getText().toString().trim();
        String kelurahan = EditKelurahan.getText().toString().trim();
        String kecamatan = EditKecamatan.getText().toString().trim();
        String kabupaten = EditKabupaten.getText().toString().trim();
        String provinsi = EditProvinsi.getText().toString().trim();
        String kkTanggal = EditKkTanggal.getText().toString().trim();
        String role = EditRole.getSelectedItem().toString().trim();

      RegistrasiModel userRegister = new RegistrasiModel(nik, password, noTelp, namaLengkap, jenisKelamin,
                tempatLahir, tanggalLahir, agama, pendidikan, pekerjaan, statusPernikahan, statusKeluarga,
                kewarganegaraan, namaAyah, namaIbu, noKK, alamat, rt, rw, kodepos, kelurahan, kecamatan,
                kabupaten, provinsi, kkTanggal, role);

        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseBody> call = apiService.reqRegister(userRegister);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Registrasi Berhasil", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                } else {
                    Toast.makeText(RegisterActivity.this, "Registrasi Gagal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
