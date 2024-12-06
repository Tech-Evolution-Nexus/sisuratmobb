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

import com.google.android.material.textfield.TextInputEditText;
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
    private Spinner selectagama, selectpendidikan, selectperkawinan, selectstatuskeluarga;
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
        ArrayAdapter hubunganAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, hubunganArray);
        hubunganAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectstatuskeluarga.setAdapter(hubunganAdapter);

        // Agama
        String[] agamaArray = getResources().getStringArray(R.array.agama);
        ArrayAdapter agamaAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, agamaArray);
        agamaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectagama.setAdapter(agamaAdapter);

        String[] pendidikanArray = getResources().getStringArray(R.array.pendidikan);
        ArrayAdapter pendidikanAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, pendidikanArray);
        pendidikanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectpendidikan.setAdapter(pendidikanAdapter);

        String[] perkawinanArray = getResources().getStringArray(R.array.status_perkawinan);
        ArrayAdapter perkawinanAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, perkawinanArray);
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
                String agama = selectagama.getSelectedItem().toString().trim();
                String pendidikan = selectpendidikan.getSelectedItem().toString().trim();
                String pekerjaan = EditPekerjaan.getText().toString().trim();
                String statusPernikahan = selectperkawinan.getSelectedItem().toString().trim();
                String statusKeluarga = selectstatuskeluarga.getSelectedItem().toString().trim();
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
                validateFields();
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
        selectagama = findViewById(R.id.registrasi_agama);
        selectpendidikan = findViewById(R.id.registrasi_pendidikan);
        EditPekerjaan = findViewById(R.id.registrasi_pekerjaan);
        selectperkawinan = findViewById(R.id.registrasi_status_perkawinan);
        selectstatuskeluarga = findViewById(R.id.registrasi_status_keluarga);
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

    private void validateFields() {
        // EditText validations
        setFieldError(EditNik, "Wajib diisi");
        setFieldError(EditPassword, "Wajib diisi");
        setFieldError(EditNohp, "Wajib diisi");
        setFieldError(EditNamalengkap, "Wajib diisi");
        setFieldError(EditTempatlahir, "Wajib diisi");
        setFieldError(EditTanggal, "Wajib diisi");
        setFieldError(EditPekerjaan, "Wajib diisi");
        setFieldError(EditNamaayah, "Wajib diisi");
        setFieldError(EditNamaibu, "Wajib diisi");
        setFieldError(EditAlamat, "Wajib diisi");
        setFieldError(EditRt, "Wajib diisi");
        setFieldError(EditRw, "Wajib diisi");
        setFieldError(EditKodepos, "Wajib diisi");
        setFieldError(EditKelurahan, "Wajib diisi");
        setFieldError(EditKecamatan, "Wajib diisi");
        setFieldError(EditKabupaten, "Wajib diisi");
        setFieldError(EditProvinsi, "Wajib diisi");
        setFieldError(EditKkTanggal, "Wajib diisi");
        setFieldError(EditEmail, "Wajib diisi");

        // Spinner validations
        setSpinnerError(selectagama, "Wajib diisi");
        setSpinnerError(selectpendidikan, "Wajib diisi");
        setSpinnerError(selectperkawinan, "Wajib diisi");
        setSpinnerError(selectstatuskeluarga, "Wajib diisi");
        if (EditNik.getText().toString().trim().length() != 16) {
            EditNik.setError("NIK harus memiliki panjang 16 karakter");
            return;
        }
        if (EditPassword.getText().toString().trim().length() < 8) {
            EditPassword.setError("Password harus memiliki minimal 8 karakter");
            return;
        }
        setFieldError(EditNokk, "Wajib diisi");
        if (EditNokk.getText().toString().trim().length() != 16) {
            EditNokk.setError("No KK harus memiliki panjang 16 karakter");
            return;
        }
        // RadioButton validation (gender)
        if (getSelectedGender().isEmpty()) {
            showGenderError(); // Show error for gender selection
        }

        // RadioButton validation (nationality)
        if (getSelectedKewarganegaraan().isEmpty()) {
            showKewarganegaraanError(); // Show error for nationality selection
        }
    }

    private void setFieldError(TextInputEditText field, String errorMessage) {
        if (field.getText().toString().trim().isEmpty()) {
            field.setError(errorMessage);
        }
    }

    // Helper method to check and set error for empty Spinner selections
    private void setSpinnerError(Spinner spinner, String errorMessage) {
        if (spinner.getSelectedItem().toString().trim().isEmpty()) {
            ((TextView) spinner.getSelectedView()).setError(errorMessage);
        }
    }

    // Helper method for RadioButton validation (for gender)
    private void showGenderError() {
        // Implement your error handling for gender RadioButton
        // Example: Toast or custom error message
        Toast.makeText(this, "Jenis kelamin harus dipilih", Toast.LENGTH_SHORT).show();
    }

    // Helper method for RadioButton validation (for nationality)
    private void showKewarganegaraanError() {
        // Implement your error handling for nationality RadioButton
        // Example: Toast or custom error message
        Toast.makeText(this, "Kewarganegaraan harus dipilih", Toast.LENGTH_SHORT).show();
    }

    private void eventClickRegis(
            String nik, String password, String noTelp, String namaLengkap, String jenisKelamin, String tempatLahir,
            String tanggalLahir, String agama, String pendidikan, String pekerjaan, String statusPernikahan,
            String statusKeluarga, String kewarganegaraan, String namaAyah, String namaIbu, String noKK,
            String alamat, String rt, String rw, String kodepos, String kelurahan, String kecamatan,
            String kabupaten, String provinsi, String kkTanggal, String email) {
        SweetAlertDialog pDialog = new SweetAlertDialog(getBaseContext(), SweetAlertDialog.PROGRESS_TYPE);
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
