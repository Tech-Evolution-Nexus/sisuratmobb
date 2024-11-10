package com.nixie.sisuratmob.Models;

public class ListKkModel {

    private String nik;
    private String nama_lengkap;

    public ListKkModel(String nik, String nama_lengkap) {
        this.nik = nik;
        this.nama_lengkap = nama_lengkap;
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
}



