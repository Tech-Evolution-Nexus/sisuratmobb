package com.nixie.sisuratmob.Models;

public class ListKkModel {
    private String nik;
    private String nama_lengkap;

    public ListKkModel(String nik, String nama_lengkap	) {
        this.nik = nik;
        this.nama_lengkap = nama_lengkap;
    }

    // Getter and Setter
    public String getNomorKK() {
        return nik;
    }

    public void setNomorKK(String nomorKK) {
        this.nik = nik;
    }


    public String getnama() {
        return nama_lengkap;
    }

    public void setnama(String nama) {
        this.nama_lengkap = nama_lengkap;
    }
}



