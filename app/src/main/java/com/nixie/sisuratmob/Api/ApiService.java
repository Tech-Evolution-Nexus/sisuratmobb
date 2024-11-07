package com.nixie.sisuratmob.Api;

import com.nixie.sisuratmob.Models.ListKkModel;
import com.nixie.sisuratmob.Models.ResponModel;
import com.nixie.sisuratmob.Models.RiwayatSurat;
import com.nixie.sisuratmob.View.Adapter.ListKkAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("jenissurat/{nik}/{id_surat}")
    Call<ResponModel> getDataForm(
            @Path("nik") String nik,
            @Path("id_surat") int id_surat
    );
    @GET("getpengajuan/{nik}/{status}")
    Call<ResponModel> getPengajuan(
            @Path("nik") String nik,
            @Path("status") String status
    );
    @GET("getlistkk/{nokk}") // Gantilah dengan URL endpoint API yang sesuai
    Call<List<ListKkModel>> getkk(
            @Path("nokk") String nokk
    );
}
