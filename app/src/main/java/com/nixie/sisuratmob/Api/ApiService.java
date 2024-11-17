package com.nixie.sisuratmob.Api;

import com.google.gson.JsonObject;
import com.nixie.sisuratmob.Models.FormModel;
import com.nixie.sisuratmob.Models.JenisSuratModel;
import com.nixie.sisuratmob.Models.KartuKeluargaModel;
import com.nixie.sisuratmob.Models.LampiranSuratModel;
import com.nixie.sisuratmob.Models.ResponModel;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @GET("list-pengajuan/{nik}")
    Call<ResponModel> getListPengajuan(
            @Path("nik") String nik
    );

    @GET("getlistkk/{nokk}")
    Call<ResponModel> getkk(
            @Path("nokk") String nokk
    );
    @GET("getlistsurat/")
    Call<ResponModel> getsurat();

    @GET("getberita/")
    Call<ResponModel> getberita();

    @GET("detailberita/{id}")
    Call<ResponModel> getdetailberita( @Path("id") int id);

    @Multipart
    @POST("sendpengajuansuratmasyarakat")
//    Call<JsonObject> submitFormData(@Part List<MultipartBody.Part> images);
    Call<JsonObject> submitFormData(
            @Part("nik") RequestBody nik,
            @Part("idsurat") RequestBody idsurat,
            @Part("keterangan") RequestBody keterangan,
            @Part List<MultipartBody.Part> images
    );

}
