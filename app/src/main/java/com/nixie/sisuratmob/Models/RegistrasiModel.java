package com.nixie.sisuratmob.Models;

public class RegistrasiModel {
    private int id;
    private String nik;
    private String nama_lengkap;
    private String jenis_kelamin;
    private String tempat_lahir;
    private String tgl_lahir;
    private String agama;
    private String pendidikan;
    private String pekerjaan;
    private String golongan_darah;
    private String status_perkawinan;
    private String status_keluarga;
    private String kewarganegaraan;
    private String nama_ayah;
    private String nama_ibu;
    private String email;
    private String password;
    private String no_hp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public void setNama_lengkap(String nama_lengkap) {
        this.nama_lengkap = nama_lengkap;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getTempat_lahir() {
        return tempat_lahir;
    }

    public void setTempat_lahir(String tempat_lahir) {
        this.tempat_lahir = tempat_lahir;
    }

    public String getTgl_lahir() {
        return tgl_lahir;
    }

    public void setTgl_lahir(String tgl_lahir) {
        this.tgl_lahir = tgl_lahir;
    }

    public String getAgama() {
        return agama;
    }

    public void setAgama(String agama) {
        this.agama = agama;
    }

    public String getPendidikan() {
        return pendidikan;
    }

    public void setPendidikan(String pendidikan) {
        this.pendidikan = pendidikan;
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(String pekerjaan) {
        this.pekerjaan = pekerjaan;
    }

    public String getGolongan_darah() {
        return golongan_darah;
    }

    public void setGolongan_darah(String golongan_darah) {
        this.golongan_darah = golongan_darah;
    }

    public String getStatus_perkawinan() {
        return status_perkawinan;
    }

    public void setStatus_perkawinan(String status_perkawinan) {
        this.status_perkawinan = status_perkawinan;
    }

    public String getStatus_keluarga() {
        return status_keluarga;
    }

    public void setStatus_keluarga(String status_keluarga) {
        this.status_keluarga = status_keluarga;
    }

    public String getKewarganegaraan() {
        return kewarganegaraan;
    }

    public void setKewarganegaraan(String kewarganegaraan) {
        this.kewarganegaraan = kewarganegaraan;
    }

    public String getNama_ayah() {
        return nama_ayah;
    }

    public void setNama_ayah(String nama_ayah) {
        this.nama_ayah = nama_ayah;
    }

    public String getNama_ibu() {
        return nama_ibu;
    }

    public void setNama_ibu(String nama_ibu) {
        this.nama_ibu = nama_ibu;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }


    public RegistrasiModel(int id, String nik, String nama_lengkap, String jenis_kelamin,
                           String tempat_lahir, String tgl_lahir, String agama,
                           String pendidikan, String pekerjaan, String golongan_darah,
                           String status_perkawinan, String status_keluarga,
                           String kewarganegaraan, String nama_ayah, String nama_ibu,
                           String email, String password, String no_hp){
        this.id = id;
        this.nik = nik;
        this.nama_lengkap = nama_lengkap;
        this.jenis_kelamin = jenis_kelamin;
        this.tempat_lahir = tempat_lahir;
        this.tgl_lahir = tgl_lahir;
        this.agama = agama;
        this.pendidikan = pendidikan;
        this.pekerjaan = pekerjaan;
        this.golongan_darah = golongan_darah;
        this.status_perkawinan = status_perkawinan;
        this.status_keluarga = status_keluarga;
        this.kewarganegaraan = kewarganegaraan;
        this.nama_ayah = nama_ayah;
        this.nama_ibu = nama_ibu;
        this.email = email;
        this.password = password;
        this.no_hp = no_hp;


    }

}
