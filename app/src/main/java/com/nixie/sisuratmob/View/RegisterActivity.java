package com.nixie.sisuratmob.View;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.nixie.sisuratmob.Api.ApiClient;
import com.nixie.sisuratmob.Api.ApiService;
import com.nixie.sisuratmob.Models.RegistrasiModel;
import com.nixie.sisuratmob.R;

import org.json.JSONObject;

import java.util.Calendar;

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
    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); // Pastikan layout ini benar

        // Bind semua komponen
        bindViews();

        // Setup tanggal lahir dan KK
        setupDatePickers();

        // Button Register Click Listener
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ambil data input
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

                // Validasi input
                if (nik.isEmpty() || password.isEmpty() || noTelp.isEmpty() || namaLengkap.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (nik.length() != 16 || password.length() < 6 || noTelp.length() != 12) {
                    Toast.makeText(RegisterActivity.this, "Data tidak valid!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Buat objek registrasi
                RegistrasiModel registrasiModel = new RegistrasiModel(nik, password, noTelp, namaLengkap, jenisKelamin, tempatLahir,
                        tanggalLahir, agama, pendidikan, pekerjaan, statusPernikahan, statusKeluarga, kewarganegaraan,
                        namaAyah, namaIbu, noKK, alamat, rt, rw, kodepos, kelurahan, kecamatan, kabupaten, provinsi, kkTanggal);

                // Kirim ke server menggunakan Retrofit
                ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
                apiService.reqRegister(registrasiModel).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                String responseBody = response.body().string();
                                Log.d("ResponseBody", responseBody); // Debug response body

                                // Cek apakah responsnya dalam format JSON
                                if (responseBody.startsWith("{") || responseBody.startsWith("[")) {
                                    JSONObject jsonObject = new JSONObject(responseBody);
                                    JSONObject data = jsonObject.getJSONObject("data");
                                    boolean status = data.getBoolean("status");
                                    String message = data.getString("msg");

                                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();

                                    if (status) {
                                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                        finish();
                                    }
                                } else {
                                    // Respons bukan JSON, tampilkan pesan error
                                    Log.d("TAG", responseBody);
                                    Toast.makeText(RegisterActivity.this, "Respons tidak dalam format JSON: " + responseBody, Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(RegisterActivity.this, "Error parsing: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RegisterActivity.this, "Register gagal", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void bindViews() {
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
        btn_register = findViewById(R.id.registrasi_button);
    }

    private void setupDatePickers() {
        // DatePicker untuk Tanggal Lahir
        EditTanggal.setOnClickListener(v -> showDatePicker(EditTanggal));
        // DatePicker untuk Tanggal KK
        EditKkTanggal.setOnClickListener(v -> showDatePicker(EditKkTanggal));
    }

    private void showDatePicker(TextInputEditText targetField) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String formattedDate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                    targetField.setText(formattedDate);
                },
                year, month, day
        );
        datePickerDialog.show();
    }
}
