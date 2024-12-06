package com.nixie.sisuratmob.View;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.nixie.sisuratmob.Api.ApiClient;
import com.nixie.sisuratmob.Api.ApiService;
import com.nixie.sisuratmob.Models.FieldModel;
import com.nixie.sisuratmob.Models.LampiranSuratModel;
import com.nixie.sisuratmob.R;
import com.nixie.sisuratmob.View.Adapter.FieldAdapter;
import com.nixie.sisuratmob.View.Adapter.LampiranAdapter;
import com.nixie.sisuratmob.komponen.ImagePickerCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormPengajuanActivity extends AppCompatActivity implements ImagePickerCallback {
    private TextInputEditText etNamaLengkap, etNoKk, etKkTgl, etNik, etAlamat, etRt, etRw, etKodePos, etKelurahan, etKecamatan, etKabupaten, etProvinsi, etKeterangan;
    private TextInputLayout lketform;
    private RecyclerView recyclerViewLampiran, recyclerViewField;
    private LampiranAdapter lampiranAdapter;
    private FieldAdapter fieldAdapter;
    private List<LampiranSuratModel> lampiranList = new ArrayList<>();
    private List<FieldModel> fieldList = new ArrayList<>();
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
        lketform = findViewById(R.id.lketform);


        recyclerViewLampiran = findViewById(R.id.recimgform);
        recyclerViewField = findViewById(R.id.recfield);
        Toolbar toolbar = findViewById(R.id.toolbar);

        recyclerViewLampiran.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewField.setLayoutManager(new LinearLayoutManager(this));

        idSurat = getIntent().getStringExtra("id_surat");
        nik = getIntent().getStringExtra("nik");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        fetchDataFromApi(nik, idSurat);
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
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Konfirmasi")
                .setContentText("Apakah Anda yakin ingin melanjutkan?")
                .setConfirmText("Ya")
                .setCancelText("Tidak")
                .setConfirmClickListener(sDialog -> {
                    eventClick();
                })
                .setCancelClickListener(sDialog -> {
                    sDialog.dismissWithAnimation();
                    // Tambahkan logika untuk No di sini
                })
                .show();
    }

    public void onPickImage(int position) {
        // Store the position for which the image picker was triggered
        selectedPosition = position;

        // Open the image picker
        Intent pickImageIntent = new Intent(Intent.ACTION_PICK);
        pickImageIntent.setType("image/*");
        startActivityForResult(pickImageIntent, IMAGE_PICK_CODE);
    }

    private void fetchDataFromApi(String nik, String idsur) {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseBody> call = apiService.getDataForm(nik, Integer.parseInt(idsur));

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String responseBody = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseBody);
                        JSONObject biodataObject = jsonObject.getJSONObject("data");
                        JSONObject databiodata = biodataObject.getJSONObject("biodata");
                        JSONArray lampirandata = biodataObject.getJSONArray("datalampiran");
                        JSONArray fielddata = biodataObject.getJSONArray("datafield");


                        boolean st = jsonObject.getBoolean("status");
                        String msg = jsonObject.getString("message");
                        if (st) {
                            etNamaLengkap.setText(databiodata.getString("nama_lengkap"));
                            etNoKk.setText(databiodata.getString("no_kk"));
                            etNik.setText(databiodata.getString("nik"));
                            etAlamat.setText(databiodata.getString("alamat"));
                            etKkTgl.setText(databiodata.getString("kk_tgl"));
                            etRt.setText(String.valueOf(databiodata.getInt("rt")));
                            etRw.setText(String.valueOf(databiodata.getInt("rw")));
                            etKodePos.setText(String.valueOf(databiodata.getInt("kode_pos")));
                            etKelurahan.setText(databiodata.getString("kelurahan"));
                            etKecamatan.setText(databiodata.getString("kecamatan"));
                            etKabupaten.setText(databiodata.getString("kabupaten"));
                            etProvinsi.setText(databiodata.getString("provinsi"));
                            for (int i = 0; i < lampirandata.length(); i++) {
                                JSONObject dataObject = lampirandata.getJSONObject(i);
                                LampiranSuratModel listlampiran = new LampiranSuratModel(dataObject.getInt("id_surat"), dataObject.getInt("id"), dataObject.getString("nama_lampiran")
//                                        dataObject.getString("image")
                                );

                                lampiranList.add(listlampiran);
                                lampiranAdapter = new LampiranAdapter(lampiranList, FormPengajuanActivity.this, recyclerViewLampiran);
                                recyclerViewLampiran.setAdapter(lampiranAdapter);
                                recyclerViewLampiran.setNestedScrollingEnabled(false);
                                lampiranAdapter.notifyDataSetChanged();
                            }
                            for (int i = 0; i < fielddata.length(); i++) {
                                JSONObject dataObject = fielddata.getJSONObject(i);
                                FieldModel listlampiran = new FieldModel(dataObject.getString("id"), dataObject.getString("id_surat"), dataObject.getString("nama_field"), dataObject.getString("tipe"));

                                fieldList.add(listlampiran);
                                fieldAdapter = new FieldAdapter(fieldList, recyclerViewField);
                                recyclerViewField.setAdapter(fieldAdapter);
                                recyclerViewField.setNestedScrollingEnabled(false);
                                fieldAdapter.notifyDataSetChanged();
                            }

                        } else {
                            Toast.makeText(FormPengajuanActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException | IOException e) {
                        throw new RuntimeException(e);
                    }
//                    List<BiodataModel> biodataList = response.body().getData().getBiodata();
//                    List<LampiranSuratModel> lampiranList = response.body().getData().getDatalampiran();

//                    if (!biodataList.isEmpty()) {
//                        BiodataModel biodata = biodataList.get(0);
//                        etNamaLengkap.setText(biodata.getNamaLengkap());
//                        etNoKk.setText(biodata.getNoKk());
//                        etNik.setText(biodata.getNik());
//                        etAlamat.setText(biodata.getAlamat());
//                        etKkTgl.setText(biodata.getKkTgl());
//                        etRt.setText(String.valueOf(biodata.getRt()));
//                        etRw.setText(String.valueOf(biodata.getRw()));
//                        etKodePos.setText(String.valueOf(biodata.getKodePos()));
//                        etKelurahan.setText(biodata.getKelurahan());
//                        etKecamatan.setText(biodata.getKecamatan());
//                        etKabupaten.setText(biodata.getKabupaten());
//                        etProvinsi.setText(biodata.getProvinsi());
//                    } else {
//                        Toast.makeText(FormPengajuanActivity.this, "Data biodata kosong", Toast.LENGTH_SHORT).show();
//                    }


                } else {
                    Toast.makeText(FormPengajuanActivity.this, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(FormPengajuanActivity.this, "Kesalahan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
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

    private void eventClick(){
        boolean hasError = false;
        lketform.setError(null);
        StringBuilder nikErrors = new StringBuilder();
        if (etKeterangan.getText().toString().isEmpty()) {
            nikErrors.append("Keterangan tidak boleh kosong.\n");
            hasError = true;
        }
        if (nikErrors.length() > 0) {
            lketform.setError(nikErrors.toString().trim());
        }
        if (fieldAdapter != null) {
            if (!fieldAdapter.validateFields()) {
                hasError = true;
            }
        }
        if (!lampiranAdapter.validateImageFields()) {
            hasError = true;
        }
        if (hasError) {
            return;
        }
        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.setTitleText("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();
        String ket = String.valueOf(etKeterangan.getText());
        List<LampiranSuratModel> lampiranData = lampiranAdapter.getLampiranList();

        List<MultipartBody.Part> partList = new ArrayList<>();
        if (fieldAdapter != null) {
            List<FieldModel> dataField = fieldAdapter.getList();
            if (dataField != null && !dataField.isEmpty()) {
                for (FieldModel field : dataField) {
                    RequestBody value = RequestBody.create(MediaType.parse("text/plain"), field.getValue());
                    MultipartBody.Part valuePart = MultipartBody.Part.createFormData("fields[]", field.getValue(), value);
                    partList.add(valuePart);
                }
            }
        }

        if (partList.isEmpty()) {
            RequestBody emptyBody = RequestBody.create(MediaType.parse("text/plain"), "");
            MultipartBody.Part emptyPart = MultipartBody.Part.createFormData("fields[]", "", emptyBody);
            partList.add(emptyPart);
        }
        List<MultipartBody.Part> imageParts = new ArrayList<>();
        for (LampiranSuratModel lampiran : lampiranData) {
            Uri imageUri = lampiran.getImageUri(); // Assuming LampiranSuratModel has getImageUri()
            if (imageUri != null) {
                String realPath = getRealPathFromURI(imageUri);
                File imageFile = new File(realPath);
                RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
                MultipartBody.Part imagePart = MultipartBody.Part.createFormData("images[]", imageFile.getName(), imageBody);
                imageParts.add(imagePart);
            }
        }
        RequestBody nikBody = RequestBody.create(MediaType.parse("text/plain"), nik);
        RequestBody keteranganBody = RequestBody.create(MediaType.parse("text/plain"), ket);
        RequestBody idsurBody = RequestBody.create(MediaType.parse("text/plain"), idSurat);
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseBody> call = apiService.submitFormData(nikBody, idsurBody, keteranganBody, imageParts, partList);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                pDialog.dismissWithAnimation();
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String responseBody = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseBody);
                        boolean st = jsonObject.getBoolean("status");
                        String msg = jsonObject.getString("message");
                        if (st) {
                            Toast.makeText(FormPengajuanActivity.this, msg, Toast.LENGTH_SHORT).show();
//                            Intent i = new Intent(FormPengajuanActivity.this, DashboardActivity.class);
//                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(FormPengajuanActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException | IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    Toast.makeText(FormPengajuanActivity.this, "Gagal mengirim data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                pDialog.dismissWithAnimation();
                Toast.makeText(FormPengajuanActivity.this, "Kesalahan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
