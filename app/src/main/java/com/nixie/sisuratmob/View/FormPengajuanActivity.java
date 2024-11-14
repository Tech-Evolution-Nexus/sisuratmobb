package com.nixie.sisuratmob.View;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.nixie.sisuratmob.Api.ApiClient;
import com.nixie.sisuratmob.Api.ApiService;
import com.nixie.sisuratmob.Models.BiodataModel;
import com.nixie.sisuratmob.Models.FormModel;
import com.nixie.sisuratmob.Models.LampiranSuratModel;
import com.nixie.sisuratmob.Models.ResponModel;
import com.nixie.sisuratmob.R;
import com.nixie.sisuratmob.View.Adapter.LampiranAdapter;
import com.nixie.sisuratmob.komponen.ImagePickerCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FormPengajuanActivity extends AppCompatActivity implements ImagePickerCallback {
    private TextInputEditText etNamaLengkap, etNoKk,etKkTgl, etNik, etAlamat, etRt, etRw, etKodePos, etKelurahan, etKecamatan, etKabupaten, etProvinsi,etKeterangan;
    private RecyclerView recyclerViewLampiran;
    private LampiranAdapter lampiranAdapter;
    private List<LampiranSuratModel> lampiranList;
    private static final int IMAGE_PICK_CODE = 1000;
    private int selectedPosition = -1;
    private String nik, idSurat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_pengajuan);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        etNamaLengkap = findViewById(R.id.etNamaLengkap);
        etNoKk = findViewById(R.id.etNoKk);
        etNik = findViewById(R.id.etNik);
        etAlamat = findViewById(R.id.etAlamat);
        etRt = findViewById(R.id.etRt);
        etRw = findViewById(R.id.etRw);
        etKodePos = findViewById(R.id.etKodePos);
        etKelurahan = findViewById(R.id.etKelurahan);
        etKecamatan = findViewById(R.id.etKecamatan);
        etKabupaten = findViewById(R.id.etKabupaten);
        etProvinsi = findViewById(R.id.etProvinsi);
        etKkTgl = findViewById(R.id.etKkTgl);
        etKeterangan = findViewById(R.id.etketerangan);

        recyclerViewLampiran = findViewById(R.id.recyclerViewlampiran);
        Toolbar toolbar = findViewById(R.id.toolbar);

        recyclerViewLampiran.setLayoutManager(new LinearLayoutManager(this));

        idSurat = getIntent().getStringExtra("id_surat");
        nik = getIntent().getStringExtra("nik");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        fetchDataFromApi(nik,idSurat);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            String realPath = getRealPathFromURI(imageUri);
            File imageFile = new File(realPath);

            if (imageFile.exists()) {

                lampiranAdapter.updateImage(imageUri, selectedPosition);
            } else {

                Log.e("FormPengajuanActivity", "File does not exist: " + imageFile.getAbsolutePath());
                Toast.makeText(this, "File not found or inaccessible", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void onSubmit(View view) {
        String ket = String.valueOf(etKeterangan.getText());
        List<LampiranSuratModel> lampiranData = lampiranAdapter.getLampiranList();
        List<MultipartBody.Part> imageParts = new ArrayList<>();
        for (LampiranSuratModel lampiran : lampiranData) {
            Uri imageUri = lampiran.getImageUri(); // Assuming LampiranSuratModel has getImageUri()
            if (imageUri != null) {
                String realPath = getRealPathFromURI(imageUri);
                File imageFile = new File(realPath);
                RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"),imageFile);
                MultipartBody.Part imagePart = MultipartBody.Part.createFormData("images[]", imageFile.getName(), imageBody);
                imageParts.add(imagePart);
            }
        }

//        Gson gson = new Gson();
//        String lampiranJson = gson.toJson(lampiranData);
//        RequestBody lampiranInfo = RequestBody.create(MediaType.parse("application/json"), lampiranJson);

//        FormModel formData = new FormModel(lampiranData);
        RequestBody nikBody = RequestBody.create(MediaType.parse("text/plain"),nik);
        RequestBody keteranganBody = RequestBody.create(MediaType.parse("text/plain"),ket);
        RequestBody idsurBody = RequestBody.create(MediaType.parse("text/plain"),idSurat);
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);

        Call<JsonObject> call = apiService.submitFormData(nikBody,idsurBody, keteranganBody,imageParts);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null) {
                    JsonObject responseBody = response.body();
                    Log.d("TAG", "onResponse: "+responseBody.get("data").toString());
                    Toast.makeText(FormPengajuanActivity.this, "Data berhasil dikirim", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FormPengajuanActivity.this, "Gagal mengirim data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("TAG", "onFailure: "+t.getMessage());
//                Toast.makeText(FormPengajuanActivity.this, "Kesalahan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onPickImage(int position) {
        // Store the position for which the image picker was triggered
        selectedPosition = position;

        // Open the image picker
        Intent pickImageIntent = new Intent(Intent.ACTION_PICK);
        pickImageIntent.setType("image/*");
        startActivityForResult(pickImageIntent, IMAGE_PICK_CODE);
    }
    private void fetchDataFromApi(String nik,String idsur) {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponModel> call = apiService.getDataForm(nik, Integer.parseInt(idsur));

        call.enqueue(new Callback<ResponModel>() {
            @Override
            public void onResponse(Call<ResponModel> call, Response<ResponModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<BiodataModel> biodataList = response.body().getData().getBiodata();
                    List<LampiranSuratModel> lampiranList = response.body().getData().getDatalampiran();

                    if (!biodataList.isEmpty()) {
                        BiodataModel biodata = biodataList.get(0);
                        etNamaLengkap.setText(biodata.getNamaLengkap());
                        etNoKk.setText(biodata.getNoKk());
                        etNik.setText(biodata.getNik());
                        etAlamat.setText(biodata.getAlamat());
                        etKkTgl.setText(biodata.getKkTgl());
                        etRt.setText(String.valueOf(biodata.getRt()));
                        etRw.setText(String.valueOf(biodata.getRw()));
                        etKodePos.setText(String.valueOf(biodata.getKodePos()));
                        etKelurahan.setText(biodata.getKelurahan());
                        etKecamatan.setText(biodata.getKecamatan());
                        etKabupaten.setText(biodata.getKabupaten());
                        etProvinsi.setText(biodata.getProvinsi());
                    } else {
                        Toast.makeText(FormPengajuanActivity.this, "Data biodata kosong", Toast.LENGTH_SHORT).show();
                    }

                    lampiranAdapter = new LampiranAdapter(lampiranList,FormPengajuanActivity.this);
                    recyclerViewLampiran.setAdapter(lampiranAdapter);
                    recyclerViewLampiran.setNestedScrollingEnabled(false);
                } else {
                    Toast.makeText(FormPengajuanActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponModel> call, Throwable t) {
                Toast.makeText(FormPengajuanActivity.this, "Kesalahan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    public String getRealPathFromURI(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            return filePath;
        } else {
            return uri.getPath(); // Fallback in case we can't resolve it
        }
    }

}
