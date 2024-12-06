package com.nixie.sisuratmob.komponen;

import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.nixie.sisuratmob.Api.ApiClient;
import com.nixie.sisuratmob.Api.ApiService;
import com.nixie.sisuratmob.Helpers.Helpers;
import com.nixie.sisuratmob.Models.BiodataModel;
import com.nixie.sisuratmob.Models.DetailHistoriModel;
import com.nixie.sisuratmob.Models.ResponModel;
import com.nixie.sisuratmob.R;
import com.nixie.sisuratmob.View.Adapter.PopupAdapter;
import com.nixie.sisuratmob.View.DiajukanFragment;
import com.nixie.sisuratmob.View.VerifikasiMasyarakatFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.getstream.photoview.PhotoView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataPopup3 extends DialogFragment {
    private TextInputEditText detNamaLengkap, detNoKk, detNik, detAlamat, detRt, detRw, detKodePos, detKelurahan, detKecamatan;
    private List<DetailHistoriModel> lampiranList;
    private String nik, idSurat;
    private PhotoView img;
    private String inik = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate layout dialog_biodata
        View view =  inflater.inflate(R.layout.dialog_biodata3, container, false);
        detNamaLengkap = view.findViewById(R.id.vetNamaLengkap);
        detNoKk = view.findViewById(R.id.vetNoKk);
        detNik = view.findViewById(R.id.vetNik);
        detAlamat = view.findViewById(R.id.vetAlamat);
        detRt = view.findViewById(R.id.vetRt);
        detRw = view.findViewById(R.id.vetRw);
        detKodePos = view.findViewById(R.id.vetKodePos);
        detKelurahan = view.findViewById(R.id.vetKelurahan);
        detKecamatan = view.findViewById(R.id.vetKecamatan);
        img = view.findViewById(R.id.imgverif);


        if (getArguments() != null) {
            String title = getArguments().getString("title");
            String status = getArguments().getString("status");
            MaterialButton dbatalView = view.findViewById(R.id.btntolakverifmas);
            MaterialButton daccView = view.findViewById(R.id.btnaccverifmas);
            inik = getArguments().getString("nik");
            fetchDataFromApi();
            dbatalView.setOnClickListener(v -> {
                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Konfirmasi")
                        .setContentText("Apakah Anda yakin Untuk Membatalkan Pengajuan Surat Ini")
                        .setConfirmText("Yes")
                        .setCancelText("No")
                        .setConfirmButtonBackgroundColor(Color.GREEN) // Hijau
                        .setCancelButtonBackgroundColor(Color.RED)
                        .setConfirmClickListener(sDialog -> {
                            sDialog.dismissWithAnimation();
                            eventClikBatal();
                        })
                        .setCancelClickListener(sDialog -> {
                            sDialog.dismissWithAnimation();
                        })
                        .show();

            });
            daccView.setOnClickListener(v->{
                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Konfirmasi")
                        .setContentText("Apakah Anda yakin ingin melanjutkan?")
                        .setConfirmText("Ya")
                        .setCancelText("Tidak")
                        .setConfirmButtonBackgroundColor(Color.parseColor("#4CAF50")) // Tombol Yes (Hijau)
                        .setCancelButtonBackgroundColor(Color.parseColor("#F44336")) // Tombol No (Merah)
                        .setConfirmClickListener(sDialog -> {
                            sDialog.dismissWithAnimation();
                            eventClickTerima();

                        })
                        .setCancelClickListener(sDialog -> {
                            sDialog.dismissWithAnimation();
                            // Tambahkan logika untuk No di sini
                        })
                        .show();
            });
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null) {
            WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int screenHeight = size.y;
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = (int) (screenHeight * 0.8);
            getDialog().getWindow().setLayout(width, height);
        }
    }

    private void fetchDataFromApi() {
        SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.setTitleText("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseBody> call = apiService.getVerifikasimasDetail(inik);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                pDialog.dismissWithAnimation();
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String responseBody = response.body().string();
                        JSONObject dataobject = new JSONObject(responseBody).getJSONObject("data");
                        detNamaLengkap.setText(dataobject.getString("nama_lengkap"));
                        detNoKk.setText(dataobject.getString("no_kk"));
                        detNik.setText(dataobject.getString("nik"));
                        detAlamat.setText(dataobject.getString("alamat"));
                        detRt.setText(String.valueOf(dataobject.getString("rt")));
                        detRw.setText(String.valueOf(dataobject.getString("rw")));
                        detKodePos.setText(String.valueOf(dataobject.getString("kode_pos")));
                        detKelurahan.setText(dataobject.getString("kelurahan"));
                        detKecamatan.setText(dataobject.getString("kecamatan"));
                        Log.d("TAG",dataobject.getString("kk_file") );
                        Glide.with(getContext())
                                .load(Helpers.BASE_URL+"admin/assetsverif/"+dataobject.getString("kk_file"))
                                .into(img);
//                        detKabupaten.setText(dataobject.getString("nama_lengkap"));
//                        detProvinsi.setText(biodata.getProvinsi());
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    Toast.makeText(getContext(), "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                pDialog.dismissWithAnimation();
                Toast.makeText(getContext(), "Kesalahan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void eventClikBatal(){
        SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.setTitleText("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseBody> call = apiService.reqCancelvermas(inik);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                pDialog.dismissWithAnimation();
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = null;
                    try {
                        responseBody = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseBody);
                        boolean status = jsonObject.getBoolean("status");
                        if(status){
                            if (getParentFragment() != null) {
                                dismiss();
                                ((VerifikasiMasyarakatFragment) getParentFragment()).refreshFragment();
                            }
                        }else{
                            Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    }catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("API Response", responseBody);

                } else {
                    // Menangani error dari respons
                    Toast.makeText(getContext(), "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                pDialog.dismissWithAnimation();
                Log.d("TAG", t.getMessage());
                Toast.makeText(getContext(),"Gagal",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void eventClickTerima(){
        SweetAlertDialog pDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.setTitleText("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseBody> call = apiService.reqAccvermas(inik);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                pDialog.dismissWithAnimation();
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = null;
                    try {
                        responseBody = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseBody);
                        boolean status = jsonObject.getBoolean("status");
                        if(status){
                            if (getParentFragment() != null) {
                                ((VerifikasiMasyarakatFragment) getParentFragment()).refreshFragment();
                            }
                            dismiss();
                        }else{
                            Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    }catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("API Response", responseBody);

                } else {
                    // Menangani error dari respons
                    Toast.makeText(getContext(), "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                pDialog.dismissWithAnimation();
                Log.d("TAG", t.getMessage());
                Toast.makeText(getContext(),"Gagal",Toast.LENGTH_SHORT).show();
            }
        });
    }
}

