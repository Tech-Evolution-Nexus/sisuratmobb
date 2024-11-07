package com.nixie.sisuratmob.Api;

import com.nixie.sisuratmob.Models.JenisSuratModel;
import com.nixie.sisuratmob.Models.KartuKeluargaModel;
import com.nixie.sisuratmob.Models.ResponModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    // Endpoint pertama
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
    @GET("getlistkk/{nokk}")
    Call<ResponModel> getkk(
            @Path("nokk") String nokk
    );
    @GET("getlistsurat/")
    Call<ResponModel> getsurat();
}
