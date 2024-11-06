package com.nixie.sisuratmob.Api;

import com.nixie.sisuratmob.Models.ResponModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("jenissurat/{nik}/{id_surat}") // Ganti dengan endpoint yang benar
    Call<ResponModel> getDataForm(
            @Path("nik") String nik,
            @Path("id_surat") int id_surat
    );
}
