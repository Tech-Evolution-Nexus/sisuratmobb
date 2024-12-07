package com.nixie.sisuratmob.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.nixie.sisuratmob.Api.ApiClient;
import com.nixie.sisuratmob.Api.ApiService;
import com.nixie.sisuratmob.Models.RegistrasiModel;
import com.nixie.sisuratmob.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText EditNik, EditPassword, EditNohp, EditNamalengkap, EditEmail;
    private TextInputEditText EditTempatlahir, EditTanggal, EditPekerjaan;
    private TextInputEditText EditNamaayah;
    private TextInputEditText EditNamaibu, EditNokk, EditAlamat, EditRt, EditRw, EditKodepos, EditKelurahan;
    private TextInputEditText EditKecamatan, EditKabupaten, EditProvinsi, EditKkTanggal;
    private MaterialAutoCompleteTextView selectagama, selectpendidikan, selectperkawinan, selectstatuskeluarga;
    private TextInputLayout ltNik, ltPassword, ltNohp, ltNamaLengkap, ltEmail;
    private TextInputLayout ltTempatLahir, ltTanggal, ltPekerjaan;
    private TextInputLayout ltNamaAyah, ltNamaIbu, ltNokk, ltAlamat, ltRt, ltRw, ltKodePos, ltKelurahan;
    private TextInputLayout ltKecamatan, ltKabupaten, ltProvinsi, ltKkTanggal;
    private TextInputLayout ltAgama, ltPendidikan, ltPerkawinan, ltStatusKeluarga;
    private TextView errortxtimg;
    private RadioGroup registrasi_jenis_kelamin, registrasi_kewarganegaraan;
    private Button btn_register;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int CAMERA_REQUEST = 100;
    MultipartBody.Part imagePart;
    private ImageView imageViewLampiran;
    private ConstraintLayout buttonPilihFile;
    private Uri imageUri;
    private File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); // Pastikan layout ini benar
        imageViewLampiran = findViewById(R.id.imageViewLampiranregis);
        buttonPilihFile = findViewById(R.id.button_pilih_file_regis);

        // Listener pada tombol pilih file
        buttonPilihFile.setOnClickListener(v -> showImagePickerDialog());
        // Bind semua komponen
        bindViews();
        // Setup tanggal lahir dan KK
        setupDatePickers();
        String[] hubunganArray = getResources().getStringArray(R.array.status_hubungan);
        ArrayAdapter hubunganAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, hubunganArray);
        hubunganAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectstatuskeluarga.setAdapter(hubunganAdapter);

        // Agama
        String[] agamaArray = getResources().getStringArray(R.array.agama);
        ArrayAdapter agamaAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, agamaArray);
        agamaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectagama.setAdapter(agamaAdapter);

        String[] pendidikanArray = getResources().getStringArray(R.array.pendidikan);
        ArrayAdapter pendidikanAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, pendidikanArray);
        pendidikanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectpendidikan.setAdapter(pendidikanAdapter);

        String[] perkawinanArray = getResources().getStringArray(R.array.status_perkawinan);
        ArrayAdapter perkawinanAdapter = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, perkawinanArray);
        perkawinanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectperkawinan.setAdapter(perkawinanAdapter);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ambil data input
                String nik = EditNik.getText().toString().trim();
                String password = EditPassword.getText().toString().trim();
                String noTelp = EditNohp.getText().toString().trim();
                String namaLengkap = EditNamalengkap.getText().toString().trim();

                // Get selected RadioButton values
                String jenisKelamin = getSelectedGender();
                String kewarganegaraan = getSelectedKewarganegaraan();

                String tempatLahir = EditTempatlahir.getText().toString().trim();
                String tanggalLahir = EditTanggal.getText().toString().trim();
                String agama = selectagama.getText().toString().trim();
                String pendidikan = selectpendidikan.getText().toString().trim();
                String pekerjaan = EditPekerjaan.getText().toString().trim();
                String statusPernikahan = selectperkawinan.getText().toString().trim();
                String statusKeluarga = selectstatuskeluarga.getText().toString().trim();
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
                String email = EditEmail.getText().toString().trim();
                errortxtimg.setVisibility(View.GONE);
                if(validateFields()){
                    return;
                }

                new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Konfirmasi")
                        .setContentText("Apakah Anda yakin ingin melanjutkan?")
                        .setConfirmText("Ya")
                        .setCancelText("Tidak")
                        .setConfirmButtonBackgroundColor(Color.parseColor("#4CAF50")) // Tombol Yes (Hijau)
                        .setCancelButtonBackgroundColor(Color.parseColor("#F44336")) // Tombol No (Merah)
                        .setConfirmClickListener(sDialog -> {
                            sDialog.dismissWithAnimation();
                            eventClickRegis(nik, password, noTelp, namaLengkap, jenisKelamin, tempatLahir, tanggalLahir,
                                    agama, pendidikan, pekerjaan, statusPernikahan, statusKeluarga, kewarganegaraan,
                                    namaAyah, namaIbu, noKK, alamat, rt, rw, kodepos, kelurahan, kecamatan,
                                    kabupaten, provinsi, kkTanggal, email);
                        })
                        .setCancelClickListener(sDialog -> {
                            sDialog.dismissWithAnimation();
                            // Tambahkan logika untuk No di sini
                        })
                        .show();

            }
        });

    }

    private void bindViews() {

        EditNik = findViewById(R.id.registrasi_nik);
        EditPassword = findViewById(R.id.registrasi_password);
        EditNohp = findViewById(R.id.registrasi_no_hp);
        EditNamalengkap = findViewById(R.id.registrasi_nama_lengkap);
        registrasi_jenis_kelamin = findViewById(R.id.registrasi_jenis_kelamin);
        EditTempatlahir = findViewById(R.id.registrasi_tempat_lahir);
        EditTanggal = findViewById(R.id.registrasi_tgl_lahir);
        selectagama = findViewById(R.id.selectagama);
        selectpendidikan = findViewById(R.id.selectpendidikan);
        EditPekerjaan = findViewById(R.id.registrasi_pekerjaan);
        selectperkawinan = findViewById(R.id.selectstatus_perkawinan);
        selectstatuskeluarga = findViewById(R.id.selectstatus_keluarga);
        registrasi_kewarganegaraan = findViewById(R.id.registrasi_kewarganegaraan);
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
        EditEmail = findViewById(R.id.registrasi_email);
        errortxtimg = findViewById(R.id.errortxtimg2);

        ltNik = findViewById(R.id.textregistrasi_nik);
        ltPassword = findViewById(R.id.textregistrasi_password);
        ltNohp = findViewById(R.id.textregistrasi_no_hp);
        ltNamaLengkap = findViewById(R.id.textregistrasi_nama_lengkap);
        ltEmail = findViewById(R.id.textregistrasi_email);

        ltTempatLahir = findViewById(R.id.textregistrasi_tempat_lahir);
        ltTanggal = findViewById(R.id.textregistrasi_tgl_lahir);
        ltPekerjaan = findViewById(R.id.textregistrasi_pekerjaan);

        ltNamaAyah = findViewById(R.id.textregistrasi_nama_ayah);
        ltNamaIbu = findViewById(R.id.textregistrasi_nama_ibu);
        ltNokk = findViewById(R.id.textregistrasi_no_kk);
        ltAlamat = findViewById(R.id.textregistrasi_alamat);
        ltRt = findViewById(R.id.textregistrasi_rt);
        ltRw = findViewById(R.id.textregistrasi_rw);
        ltKodePos = findViewById(R.id.textregistrasi_kode_pos);
        ltKelurahan = findViewById(R.id.textregistrasi_kelurahan);

        ltKecamatan = findViewById(R.id.textregistrasi_kecamatan);
        ltKabupaten = findViewById(R.id.textregistrasi_kabupaten);
        ltProvinsi = findViewById(R.id.textregistrasi_provinsi);
        ltKkTanggal = findViewById(R.id.textregistrasi_kk_tgl);

        ltAgama = findViewById(R.id.textregistrasi_agama);
        ltPendidikan = findViewById(R.id.textregistrasi_pendidikan);
        ltPerkawinan = findViewById(R.id.textregistrasi_perkawinan);
        ltStatusKeluarga = findViewById(R.id.textregistrasi_status_keluarga);
        EditNik.setText(getIntent().getStringExtra("nik"));
    }

    private String getSelectedGender() {
        int selectedId = registrasi_jenis_kelamin.getCheckedRadioButtonId();
        RadioButton selectedButton = findViewById(selectedId);
        return selectedButton != null ? selectedButton.getText().toString() : "";
    }

    private String getSelectedKewarganegaraan() {
        int selectedId = registrasi_kewarganegaraan.getCheckedRadioButtonId();
        RadioButton selectedButton = findViewById(selectedId);
        return selectedButton != null ? selectedButton.getText().toString() : "";
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

    private void showImagePickerDialog() {
        String[] options = {"Pilih dari Galeri", "Ambil Foto"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih Gambar");
        builder.setItems(options, (dialog, which) -> {
            if (which == 0) {
                pickImageFromGallery();
            } else if (which == 1) {
                captureImageWithCamera();
            }
        });
        builder.show();
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void captureImageWithCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            try {
                photoFile = File.createTempFile("IMG_", ".jpg", getExternalFilesDir(null));
                Uri photoURI = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".fileprovider", photoFile);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private File createImageFile() throws IOException {
        String fileName = "IMG_" + System.currentTimeMillis();
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(fileName, ".jpg", storageDir);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null) {
                Uri selectedImageUri = data.getData();
                processImage(selectedImageUri);
            } else if (requestCode == CAMERA_REQUEST && photoFile != null) {
                Uri cameraUri = Uri.fromFile(photoFile);
                processImage(cameraUri);
            }
        }
    }

    private void processImage(Uri imageUri) {
        try (InputStream inputStream = getContentResolver().openInputStream(imageUri)) {
            this.imageUri = imageUri;
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            bitmap = handleImageRotation(bitmap, imageUri);
            imageViewLampiran.setImageBitmap(bitmap);
            imageViewLampiran.setVisibility(ImageView.VISIBLE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Bitmap handleImageRotation(Bitmap bitmap, Uri imageUri) {
        try {
            ExifInterface exif = new ExifInterface(getContentResolver().openInputStream(imageUri));
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    return rotateImage(bitmap, 90);
                case ExifInterface.ORIENTATION_ROTATE_180:
                    return rotateImage(bitmap, 180);
                case ExifInterface.ORIENTATION_ROTATE_270:
                    return rotateImage(bitmap, 270);
                default:
                    return bitmap; // Tidak ada rotasi
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public String getRealPathFromURI(Uri uri) {
        String realPath = null;

        if ("content".equalsIgnoreCase(uri.getScheme())) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                    if (columnIndex != -1) {
                        realPath = cursor.getString(columnIndex);
                    } else {
                        int displayNameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                        if (displayNameIndex != -1) {
                            realPath = getTempFilePathFromInputStream(uri, cursor.getString(displayNameIndex));
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            realPath = uri.getPath();
        }
        return realPath;
    }

    private String getTempFilePathFromInputStream(Uri uri, String fileName) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            File tempFile = new File(getCacheDir(), fileName);
            try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                return tempFile.getAbsolutePath();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean validateFields() {
        resetErrors();
        boolean isValid = false;

        // EditText validations
        if (setFieldError(EditNik, ltNik, "Wajib diisi")) isValid = true;
        if (setFieldError(EditNokk, ltNokk, "Wajib diisi")) isValid = true;
        if (setFieldError(EditPassword, ltPassword, "Wajib diisi")) isValid = true;
        if (setFieldError(EditNohp, ltNohp, "Wajib diisi")) isValid = true;
        if (setFieldError(EditNamalengkap, ltNamaLengkap, "Wajib diisi")) isValid = true;
        if (setFieldError(EditTempatlahir, ltTempatLahir, "Wajib diisi")) isValid = true;
        if (setFieldError(EditTanggal, ltTanggal, "Wajib diisi")) isValid = true;
        if (setFieldError(EditPekerjaan, ltPekerjaan, "Wajib diisi")) isValid = true;
        if (setFieldError(EditNamaayah, ltNamaAyah, "Wajib diisi")) isValid = true;
        if (setFieldError(EditNamaibu, ltNamaIbu, "Wajib diisi")) isValid = true;
        if (setFieldError(EditAlamat, ltAlamat, "Wajib diisi")) isValid = true;
        if (setFieldError(EditRt, ltRt, "Wajib diisi")) isValid = true;
        if (setFieldError(EditRw, ltRw, "Wajib diisi")) isValid = true;
        if (setFieldError(EditKodepos, ltKodePos, "Wajib diisi")) isValid = true;
        if (setFieldError(EditKelurahan, ltKelurahan, "Wajib diisi")) isValid = true;
        if (setFieldError(EditKecamatan, ltKecamatan, "Wajib diisi")) isValid = true;
        if (setFieldError(EditKabupaten, ltKabupaten, "Wajib diisi")) isValid = true;
        if (setFieldError(EditProvinsi, ltProvinsi, "Wajib diisi")) isValid = true;
        if (setFieldError(EditKkTanggal, ltKkTanggal, "Wajib diisi")) isValid = true;
        if (setFieldError(EditEmail, ltEmail, "Wajib diisi")) isValid = true;

        // Spinner validations
        if (setSpinnerError(selectagama, ltAgama, "Wajib diisi")) isValid = true;
        if (setSpinnerError(selectpendidikan, ltPendidikan, "Wajib diisi")) isValid = true;
        if (setSpinnerError(selectperkawinan, ltPerkawinan, "Wajib diisi")) isValid = true;
        if (setSpinnerError(selectstatuskeluarga, ltStatusKeluarga, "Wajib diisi")) isValid = true;

        // Gender validation
        if (getSelectedGender().isEmpty()) {
            showGenderError();
            isValid = true;
        }

        // Nationality validation
        if (getSelectedKewarganegaraan().isEmpty()) {
            showKewarganegaraanError();
            isValid = true;
        }

        // Specific field length validations
        if (EditNik.getText().toString().trim().length() != 16) {
            ltNik.setError("NIK harus memiliki panjang 16 karakter");
            isValid = true;
        }
        if (EditPassword.getText().toString().trim().length() < 8) {
            ltPassword.setError("Password harus memiliki minimal 8 karakter");
            isValid = true;
        }
        if (EditNokk.getText().toString().trim().length() != 16) {
            ltNokk.setError("No KK harus memiliki panjang 16 karakter");
            isValid = true;
        }

        // Image validation
        if (imageUri == null) {
            errortxtimg.setVisibility(View.VISIBLE);
            isValid = true;
        }
        return isValid;

    }

    private boolean setFieldError(TextInputEditText field, TextInputLayout lt, String errorMessage) {
        if (field.getText().toString().trim().isEmpty()) {
            lt.setError(errorMessage);
            return true; // Error found
        }
        return false; // No error
    }

    // Helper method to check and set error for empty Spinner selections
    private boolean setSpinnerError(MaterialAutoCompleteTextView spinner, TextInputLayout lt, String errorMessage) {
        if (spinner.getText().toString().trim().isEmpty()) {
            lt.setError(errorMessage);
            return true; // Error found
        }
        return false; // No error
    }

    // Helper method for RadioButton validation (for gender)
    private void showGenderError() {
        TextView tvGenderError = findViewById(R.id.tvGenderError);
        tvGenderError.setVisibility(View.VISIBLE);
    }

    // Helper method for RadioButton validation (for nationality)
    private void showKewarganegaraanError() {
        TextView tvGenderError = findViewById(R.id.tvKewarganegaraanError);
        tvGenderError.setVisibility(View.VISIBLE);
    }private void resetErrors() {
        // Reset errors for TextInputLayout
        ltNik.setError(null);
        ltPassword.setError(null);
        ltNohp.setError(null);
        ltNamaLengkap.setError(null);
        ltEmail.setError(null);
        ltTempatLahir.setError(null);
        ltTanggal.setError(null);
        ltPekerjaan.setError(null);
        ltNamaAyah.setError(null);
        ltNamaIbu.setError(null);
        ltAlamat.setError(null);
        ltRt.setError(null);
        ltRw.setError(null);
        ltKodePos.setError(null);
        ltKelurahan.setError(null);
        ltKecamatan.setError(null);
        ltKabupaten.setError(null);
        ltProvinsi.setError(null);
        ltKkTanggal.setError(null);

        // Reset errors for Spinners
        ltAgama.setError(null);
        ltPendidikan.setError(null);
        ltPerkawinan.setError(null);
        ltStatusKeluarga.setError(null);

        // Hide TextView errors for gender and nationality
        TextView tvGenderError = findViewById(R.id.tvGenderError);
        if (tvGenderError != null) {
            tvGenderError.setVisibility(View.GONE);
        }

        TextView tvKewarganegaraanError = findViewById(R.id.tvKewarganegaraanError);
        if (tvKewarganegaraanError != null) {
            tvKewarganegaraanError.setVisibility(View.GONE);
        }

        // Reset image error visibility
        if (errortxtimg != null) {
            errortxtimg.setVisibility(View.GONE);
        }
    }

    private void eventClickRegis(
            String nik, String password, String noTelp, String namaLengkap, String jenisKelamin, String tempatLahir,
            String tanggalLahir, String agama, String pendidikan, String pekerjaan, String statusPernikahan,
            String statusKeluarga, String kewarganegaraan, String namaAyah, String namaIbu, String noKK,
            String alamat, String rt, String rw, String kodepos, String kelurahan, String kecamatan,
            String kabupaten, String provinsi, String kkTanggal, String email) {
        SweetAlertDialog pDialog = new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.setTitleText("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();
        if (imageUri != null) {
            String filePath = getRealPathFromURI(imageUri);
            if (filePath != null) {
                File file = new File(filePath);
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                imagePart = MultipartBody.Part.createFormData("images", file.getName(), requestBody);
            } else {
                Log.e("Error", "Failed to get real path from URI.");
            }
            Gson gson = new Gson();
            RegistrasiModel registrasiModel = new RegistrasiModel(nik, password, noTelp, namaLengkap, jenisKelamin, tempatLahir,
                    tanggalLahir, agama, pendidikan, pekerjaan, statusPernikahan, statusKeluarga, kewarganegaraan,
                    namaAyah, namaIbu, noKK, alamat, rt, rw, kodepos, kelurahan, kecamatan, kabupaten, provinsi, kkTanggal, email);
            String jsonData = gson.toJson(registrasiModel);
            RequestBody data = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonData);

            ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
            apiService.reqRegister(data, imagePart).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    pDialog.dismissWithAnimation();
                    if (response.isSuccessful()) {
                        try {
                            assert response.body() != null;
                            String responseBody = response.body().string();
                            JSONObject jsonObject = new JSONObject(responseBody);
                            boolean status = jsonObject.getBoolean("status");
                            String message = jsonObject.getString("message");
                            Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                            if (status) {
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                intent.putExtra("nik", nik);
                                startActivity(intent);
                                finish();
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    pDialog.dismissWithAnimation();
                    Toast.makeText(RegisterActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            errortxtimg.setVisibility(View.VISIBLE);
        }
    }

}
