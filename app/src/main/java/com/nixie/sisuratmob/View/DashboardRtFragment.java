// DashboardRtFragment.java
package com.nixie.sisuratmob.View;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nixie.sisuratmob.Api.ApiClient;
import com.nixie.sisuratmob.Api.ApiService;
import com.nixie.sisuratmob.Models.Berita;
import com.nixie.sisuratmob.Models.ResponModel;
import com.nixie.sisuratmob.Models.Surat;
import com.nixie.sisuratmob.R;
import com.nixie.sisuratmob.View.Adapter.BeritaAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardRtFragment extends Fragment {

    private RecyclerView recyclerView;
    private BeritaAdapter beritaAdapter;
    private TextView suratMasuk,suratSelesai,textBerita,textdashrt;
    private ImageView icon;
    private List<Berita> dberitaList = new ArrayList<>();
    public DashboardRtFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard_rt, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewBerita);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        beritaAdapter = new BeritaAdapter(getContext(), dberitaList);
        recyclerView.setAdapter(beritaAdapter);
        recyclerView.setHasFixedSize(true);
        suratMasuk = view.findViewById(R.id.suratMasuk);
        suratSelesai = view.findViewById(R.id.suratSelesai);
        textBerita = view.findViewById(R.id.textBerita);
        textdashrt = view.findViewById(R.id.textnamedashrt);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        textdashrt.setText("Hallo"+sharedPreferences.getString("nama_lengkap",""));

        icon = view.findViewById(R.id.iconn);
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionsDialog();
            }
        });
        textBerita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inten = new Intent(getActivity(), BritaallActivity.class);
                startActivity(inten);
            }
        });
        fetchdata();
        return view;
    }

    private void showOptionsDialog() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.options_dialog);
        dialog.setCancelable(true);

        Button aboutAppButton = dialog.findViewById(R.id.aboutAppButton);
        Button logoutButton = dialog.findViewById(R.id.logoutButton);

        aboutAppButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                showAboutApp();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                logoutUser();
            }
        });


        Window window = dialog.getWindow();
        if (window != null) {
            // Mengatur posisi dialog di kanan atas
            window.setGravity(Gravity.TOP | Gravity.RIGHT);

            // Mengatur margin atau jarak dari tepi
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.x = 30; // Jarak dari kanan (untuk memberi jarak dengan ikon)
            layoutParams.y = 40; // Jarak dari atas (ubah sesuai keinginan)
            window.setAttributes(layoutParams);
        }

        dialog.show();
    }
    private void fetchdata() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseBody> call = apiService.getDash(sharedPreferences.getString("nik",""));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String responseBody = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseBody);
                        if(jsonObject.getBoolean("status")){
                            JSONObject dataArray = jsonObject.getJSONObject("data");
                            suratMasuk.setText(dataArray.getString("masuk"));
                            suratSelesai.setText(dataArray.getString("selesai"));
                        }else{
                            Toast.makeText(getContext(),jsonObject.getString("message"),Toast.LENGTH_SHORT).show();
                        }


                    } catch (IOException | JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.e("API Error", "Error: " + t.getMessage());
                Toast.makeText(getContext(), "Network error", Toast.LENGTH_SHORT).show();
            }
        });

        Call<ResponseBody> call2 = apiService.getberita("s");
        call2.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String responseBody = response.body().string();
                        JSONObject jsonObject = new JSONObject(responseBody);
                        JSONArray dataArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject dataObject = dataArray.getJSONObject(i);
                            Berita berita = new Berita(dataObject.getInt("id"),
                                    dataObject.getString("judul"),
                                    dataObject.getString("sub_judul"),
                                    dataObject.getString("deskripsi"),
                                    dataObject.getString("gambar"),
                                    dataObject.getString("created_at"));
                            dberitaList.add(berita);
                            beritaAdapter.notifyDataSetChanged();
                        }

                    } catch (IOException | JSONException e) {
                        throw new RuntimeException(e);
                    }
//                    List<Berita> beritaList = response.body().getData().getDataberita();
//                    if (beritaList != null) {
//                        dberitaList.clear();
//                        dberitaList.addAll(beritaList);
//                    } else {
//                        Toast.makeText(getContext(), "No data available", Toast.LENGTH_SHORT).show();
//                    }
                } else {
                    Toast.makeText(getContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.e("API Error", "Error: " + t.getMessage());
                Toast.makeText(getContext(), "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAboutApp() {
        Intent intent = new Intent(getActivity(), DashboardActivity.class);
        startActivity(intent);
    }

    private void logoutUser() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("isLoggedIn");
        editor.remove("nik");
        editor.remove("role");
        editor.remove("namalengkap");
        editor.remove("nokk");
        editor.remove("email");
        editor.remove("no_hp");
        editor.apply();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
