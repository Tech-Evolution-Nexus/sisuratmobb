package com.nixie.sisuratmob.Models;

public class ListKk {
    private String NIK;
    private String nama;


    // Constructor
    public ListKk(String nomorKK, String namaKepalaKeluarga, String alamat, String fotoKepalaKeluarga) {
        this.NIK = nomorKK;
        this.nama = namaKepalaKeluarga;

    }

    // Getter and Setter
    public String getNomorKK() {
        return NIK;
    }

    public void setNomorKK(String nomorKK) {
        this.NIK = NIK;
    }


    public String getAlamat() {
        return nama;
    }

    public void setAlamat(String alamat) {
        this.nama = nama;
    }
}



