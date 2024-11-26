package com.nixie.sisuratmob.Models;

public class RegistrasiModel {

    private String nik;
    private String password;
    private String no_hp;
    private String nama_lengkap;
    private String jenis_kelamin;
    private String tempat_lahir;
    private String tgl_lahir;
    private String agama;
    private String pendidikan;
    private String pekerjaan;
    private String status_perkawinan;
    private String status_keluarga;
    private String kewarganegaraan;
    private String nama_ayah;
    private String nama_ibu;
    private String no_kk;
    private String alamat;
    private String rt;
    private String rw;
    private String kode_pos;
    private String kelurahan;
    private String kecamatan;
    private String kabupaten;
    private String provinsi;
    private String kk_tgl;
    private String email;




    public RegistrasiModel(String nik, String password, String no_hp, String nama_lengkap, String jenis_kelamin, String tempat_lahir, String tgl_lahir, String agama, String pendidikan, String pekerjaan, String status_perkawinan, String status_keluarga, String kewarganegaraan, String nama_ayah, String nama_ibu, String no_kk, String alamat, String rt, String rw, String kode_pos, String kelurahan, String kecamatan, String kabupaten, String provinsi, String kk_tgl,String email) {
        this.nik = nik;
        this.password = password;
        this.no_hp = no_hp;
        this.nama_lengkap = nama_lengkap;
        this.jenis_kelamin = jenis_kelamin;
        this.tempat_lahir = tempat_lahir;
        this.tgl_lahir = tgl_lahir;
        this.agama = agama;
        this.pendidikan = pendidikan;
        this.pekerjaan = pekerjaan;
        this.status_perkawinan = status_perkawinan;
        this.status_keluarga = status_keluarga;
        this.kewarganegaraan = kewarganegaraan;
        this.nama_ayah = nama_ayah;
        this.nama_ibu = nama_ibu;
        this.no_kk = no_kk;
        this.alamat = alamat;
        this.rt = rt;
        this.rw = rw;
        this.kode_pos = kode_pos;
        this.kelurahan = kelurahan;
        this.kecamatan = kecamatan;
        this.kabupaten = kabupaten;
        this.provinsi = provinsi;
        this.kk_tgl = kk_tgl;
        this.email = email;

    }
    public String getKk_tgl() {
        return kk_tgl;
    }

    public void setKk_tgl(String kk_tgl) {
        this.kk_tgl = kk_tgl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
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

    public String getNo_kk() {
        return no_kk;
    }

    public void setNo_kk(String no_kk) {
        this.no_kk = no_kk;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getRt() {
        return rt;
    }

    public void setRt(String rt) {
        this.rt = rt;
    }

    public String getRw() {
        return rw;
    }

    public void setRw(String rw) {
        this.rw = rw;
    }

    public String getKode_pos() {
        return kode_pos;
    }

    public void setKode_pos(String kode_pos) {
        this.kode_pos = kode_pos;
    }

    public String getKelurahan() {
        return kelurahan;
    }

    public void setKelurahan(String kelurahan) {
        this.kelurahan = kelurahan;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getKabupaten() {
        return kabupaten;
    }

    public void setKabupaten(String kabupaten) {
        this.kabupaten = kabupaten;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

    public String getKk_tanggal() {
        return kk_tgl;
    }

    public void setKk_tanggal(String kk_tgl) {
        this.kk_tgl = kk_tgl;
    }
}