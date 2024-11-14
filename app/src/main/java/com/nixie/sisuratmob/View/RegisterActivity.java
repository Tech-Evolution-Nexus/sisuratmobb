package com.nixie.sisuratmob.View;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.nixie.sisuratmob.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    private TextInputEditText EditNik, EditPassword, EditNohp, EditNamalengkap, EditJeniskelamin;
    private TextInputEditText EditTempatlahir, EditTanggal, EditAgama, EditPendidikan, EditPekerjaan;
    private TextInputEditText EditStatusperkawinan, EditStatuskeluarga, EditKwarganegaraan, EditNamaayah;
    private TextInputEditText EditNamaibu, EditNokk, EditAlamat, EditRt, EditRw, EditKodepos, EditKelurahan;
    private TextInputEditText EditKecamatan, EditKabupaten, EditProvinsi, EditKkTanggal;
    private Spinner EditRole;
    private Button btn_register;
    private TextView masuklogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inisialisasi EditText dan Button
        EditNik = findViewById(R.id.registrasi_nik);
        EditPassword = findViewById(R.id.registrasi_password);
        EditNamalengkap = findViewById(R.id.registrasi_nama_lengkap);
        EditRole = findViewById(R.id.registrasi_role); // Perbaikan: variabel EditRole tidak perlu dideklarasikan ulang
        EditNohp = findViewById(R.id.registrasi_no_hp);
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

        // Menyiapkan spinner dengan data role
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.roles, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        EditRole.setAdapter(adapter);

        // DatePicker untuk EditTanggal (Tanggal Lahir)
        EditTanggal.setOnClickListener(v -> showDatePickerDialog(EditTanggal));

        // DatePicker untuk EditKkTanggal (Tanggal KK)
        EditKkTanggal.setOnClickListener(v -> showDatePickerDialog(EditKkTanggal));

        // Button register klik
        btn_register = findViewById(R.id.registrasi_button);
        btn_register.setOnClickListener(v -> register());
    }

    // Fungsi untuk membuka DatePickerDialog
    private void showDatePickerDialog(TextInputEditText editText) {
        // Ambil tanggal hari ini
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Buat DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Menampilkan tanggal yang dipilih ke EditText
                    editText.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    // Fungsi untuk melakukan registrasi
    public void register() {
        String nik = EditNik.getText().toString();
        String password = EditPassword.getText().toString();
        String namaLengkap = EditNamalengkap.getText().toString();
        String role = EditRole.getSelectedItem().toString();
        String noHp = EditNohp.getText().toString();
        String jenisKelamin = EditJeniskelamin.getText().toString();
        String tempatLahir = EditTempatlahir.getText().toString();
        String tglLahir = EditTanggal.getText().toString();
        String agama = EditAgama.getText().toString();
        String pendidikan = EditPendidikan.getText().toString();
        String pekerjaan = EditPekerjaan.getText().toString();
        String statusPerkawinan = EditStatusperkawinan.getText().toString();
        String statusKeluarga = EditStatuskeluarga.getText().toString();
        String kewarganegaraan = EditKwarganegaraan.getText().toString();
        String namaAyah = EditNamaayah.getText().toString();
        String namaIbu = EditNamaibu.getText().toString();
        String noKk = EditNokk.getText().toString();
        String alamat = EditAlamat.getText().toString();
        String rt = EditRt.getText().toString();
        String rw = EditRw.getText().toString();
        String kodePos = EditKodepos.getText().toString();
        String kelurahan = EditKelurahan.getText().toString();
        String kecamatan = EditKecamatan.getText().toString();
        String kabupaten = EditKabupaten.getText().toString();
        String provinsi = EditProvinsi.getText().toString();
        String kkTanggal = EditKkTanggal.getText().toString();
        Toast.makeText(this, "Registrasi berhasil", Toast.LENGTH_SHORT).show();
    }
}
