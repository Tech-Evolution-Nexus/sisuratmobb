package com.nixie.sisuratmob.Models;

import com.google.gson.annotations.SerializedName;

public class PengajuanSuratModel {
    private int id,id_surat;
    private String nama_surat;
    private String tanggal_surat;
    private String status;
    private String nik;
    private String nama_lengkap;
    private String tanggal_pengajuan;
    private String keterangan;
    private String keterangan_ditolak;
    private String image;
    private String no_pengantar_rt;
    private String[] lampiran;


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNo_pengantar_rt() {
        return no_pengantar_rt;
    }

    public void setNo_pengantar_rt(String no_pengantar_rt) {
        this.no_pengantar_rt = no_pengantar_rt;
    }

    // Constructor
    public PengajuanSuratModel(int id,int id_surat, String nama_surat, String tanggal_surat, String status, String nik, String nama_lengkap, String tanggal_pengajuan, String[] lampiran,String keterangan,String keterangan_ditolak,String image,String no_pengantar_rt) {
        this.id = id;
        this.id_surat = id_surat;
        this.nama_surat = nama_surat;
        this.tanggal_surat = tanggal_surat;
        this.status = status;
        this.nik = nik;
        this.nama_lengkap = nama_lengkap;
        this.tanggal_pengajuan = tanggal_pengajuan;
        this.lampiran = lampiran;
        this.keterangan = keterangan;
        this.keterangan_ditolak = keterangan_ditolak;
        this.image = image;
        this.no_pengantar_rt=no_pengantar_rt;
    }

      public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getKeterangan_ditolak() {
        return keterangan_ditolak;
    }

    public void setKeterangan_ditolak(String keterangan_ditolak) {
        this.keterangan_ditolak = keterangan_ditolak;
    }

    // Getters and Setters (tidak berubah)
    public int getId_surat() {
        return id_surat;
    }

    public void setId_surat(int id_surat) {
        this.id_surat = id_surat;
    }

    public String getNama_surat() {
        return nama_surat;
    }

    public void setNama_surat(String nama_surat) {
        this.nama_surat = nama_surat;
    }

    public String getTanggal_surat() {
        return tanggal_surat;
    }

    public void setTanggal_surat(String tanggal_surat) {
        this.tanggal_surat = tanggal_surat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getTanggal_pengajuan() {
        return tanggal_pengajuan;
    }

    public void setTanggal_pengajuan(String tanggal_pengajuan) {
        this.tanggal_pengajuan = tanggal_pengajuan;
    }

    public String[] getLampiran() {
        return lampiran;
    }

    public void setLampiran(String[] lampiran) {
        this.lampiran = lampiran;
    }
}
