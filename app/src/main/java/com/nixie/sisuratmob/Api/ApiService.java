package com.nixie.sisuratmob.Api;

import com.nixie.sisuratmob.Models.AktivasiModel;
import com.nixie.sisuratmob.Models.ResponModel;
import com.nixie.sisuratmob.Models.UserLoginModel;
import com.nixie.sisuratmob.Models.VerivModel;

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

public interface ApiService {

    // Endpoint pertama
    @GET("getdataform/{nik}/{id_surat}")
    Call<ResponseBody> getDataForm(@Path("nik") String nik, @Path("id_surat") int id_surat);

    @GET("getpengajuan/{nik}/{status}")
    Call<ResponseBody> getPengajuan(@Path("nik") String nik, @Path(value = "status", encoded = true) String status

    );

    @GET("list-pengajuan/{nik}/{status}")
    Call<ResponModel> getListPengajuan(@Path("nik") String nik, @Path("status") String status);

    @GET("getlistkk/{nokk}")
    Call<ResponseBody> getkk(@Path("nokk") String nokk);

    @GET("getlistsurat/{data}")
    Call<ResponseBody> getsurat(@Path("data") String data);

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
    @Multipart
    @POST("register")
    Call<ResponseBody> reqRegister(@Part("data") RequestBody data,
                                   @Part MultipartBody.Part images);


    @GET("getberita/{data}")
    Call<ResponseBody> getberita(@Path("data") String data);

    @GET("detailberita/{id}")
    Call<ResponseBody> getdetailberita(@Path("id") int id);

    @Multipart
    @POST("sendpengajuansuratmasyarakat")
//    Call<JsonObject> submitFormData(@Part List<MultipartBody.Part> images);
    Call<ResponseBody> submitFormData(@Part("nik") RequestBody nik, @Part("idsurat") RequestBody idsurat, @Part("keterangan") RequestBody keterangan, @Part List<MultipartBody.Part> images, @Part List<MultipartBody.Part> fields);

    @GET("detailhistory/{idpengajuan}")
    Call<ResponseBody> getdetailhistory(@Path("idpengajuan") int idpengajuan);

    @POST("pengajuanpembatalan")
    Call<ResponseBody> batalkanpengajuan(@Body String idpengajuan);

    @Multipart
    @POST("approval-pengajuan/{nik}/{id_pengajuan}")
    Call<ResponseBody> approvalPengajuan(@Path("nik") String nik, @Path("id_pengajuan") int id_pengajuan, @Part("status") RequestBody status, @Part("keterangan") RequestBody keteranganDitolak, @Part("nopegantarrt") RequestBody nopegantarrt);

    @FormUrlEncoded
    @POST("sendemail")
    Call<ResponseBody> reqSendEmail(@Field("email") String email);

    @FormUrlEncoded
    @POST("reset-password")
    Call<ResponseBody> reqResetpass(@Field("email") String email, @Field("token") String token, @Field("password") String password);

    @GET("get-user-info/{nik}")
    Call<ResponseBody> getinfouser(@Path("nik") String nik);

    @GET("getverifmasyarakat")
    Call<ResponseBody> getVerifikasimas();

    @GET("getdash/{nik}")
    Call<ResponseBody> getDash(@Path("nik") String nik);

    @GET("getdetailver/{nik}")
    Call<ResponseBody> getVerifikasimasDetail(@Path("nik") String nik);

    @FormUrlEncoded
    @POST("accverifmasyarakat")
    Call<ResponseBody> reqAccvermas(@Field("nik") String nik);

    @FormUrlEncoded
    @POST("tolakverifmasyarakat")
    Call<ResponseBody> reqCancelvermas(@Field("nik") String nik);

    @FormUrlEncoded
    @POST("ganti-password")
    Call<ResponseBody> reqgantiPassword(@Field("nik") String nik, @Field("password") String password, @Field("new_pass") String new_pass);
    @FormUrlEncoded
    @POST("ubah-email")
    Call<ResponseBody> reqUbahEmail(@Field("nik") String nik, @Field("emailbaru") String emailbaru, @Field("emaillama") String emaillama);
    @FormUrlEncoded
    @POST("ubah-nohp")
    Call<ResponseBody> reqUbahNohp(@Field("nik") String nik, @Field("nohplama") String nohplama, @Field("nohpbaru") String nohpbaru);
    @FormUrlEncoded
    @POST("reset-fcm")
    Call<ResponseBody> reqResetfcm(@Field("nik") String nik, @Field("token") String token);
}
