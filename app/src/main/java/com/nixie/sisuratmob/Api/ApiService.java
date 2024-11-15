package com.nixie.sisuratmob.Api;

import com.nixie.sisuratmob.Models.AktivasiModel;
import com.nixie.sisuratmob.Models.RegistrasiModel;
import com.nixie.sisuratmob.Models.ResponModel;
import com.nixie.sisuratmob.Models.UserLoginModel;
import com.nixie.sisuratmob.Models.VerivModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
            @Path("status") String status
    );
    @GET("getlistkk/{nokk}")
    Call<ResponModel> getkk(
            @Path("nokk") String nokk
    );
    @GET("getlistsurat/")
    Call<ResponModel> getsurat();


    @POST("login")
    Call<ResponseBody> reqLogin(@Body UserLoginModel user);

    @POST("verivikasi")
    Call<ResponseBody> reqVerivikasi(@Body VerivModel veriv);

    @POST("aktivasi")
    Call<ResponseBody> reqAKtivasi(@Body AktivasiModel aktiv);

    @POST("register")
    Call<ResponseBody> reqRegister(@Body RegistrasiModel register);







}
