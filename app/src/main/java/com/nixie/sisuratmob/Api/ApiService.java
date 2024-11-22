package com.nixie.sisuratmob.Api;

import com.nixie.sisuratmob.Models.AktivasiModel;
import com.google.gson.JsonObject;
import com.nixie.sisuratmob.Models.Berita;
import com.nixie.sisuratmob.Models.RegistrasiModel;
import com.nixie.sisuratmob.Models.ResponModel;
import com.nixie.sisuratmob.Models.Surat;
import com.nixie.sisuratmob.Models.UserLoginModel;
import com.nixie.sisuratmob.Models.VerivModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.Part;
import retrofit2.http.Path;

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
            @Path(value = "status", encoded = true) String status
    );

    @GET("list-pengajuan/{nik}/{status}")
    Call<ResponModel> getListPengajuan(
            @Path("nik") String nik,
            @Path("status") String status
    );

    @GET("getlistkk/{nokk}")
    Call<ResponModel> getkk(
            @Path("nokk") String nokk
    );
    @GET("getlistsurat/")
    Call<ResponModel> getsurat();
        // Login Request
        @POST("login")
        Call<ResponseBody> reqLogin(@Body UserLoginModel userLogin);

        // Verifikasi Request
        @POST("veriv")
        Call<ResponseBody> reqVerifikasi(@Body VerivModel userVeriv);

        // Aktivasi Request
        @POST("aktivasi")
        Call<ResponseBody> reqAktivasi(@Body AktivasiModel userRegister);

        // Register Request
        @POST("register")
        Call<ResponseBody> reqRegister(@Body RegistrasiModel userRegister);



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
    @GET("detailhistory/{idpengajuan}")
    Call<ResponModel> getdetailhistory(
            @Path("idpengajuan") int idpengajuan
    );
}
