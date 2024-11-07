package com.nixie.sisuratmob.Models;

public class ListKkModel {
    private String nik;
    private String nama;


    // Constructor
    public ListKkModel(String nomorKK, String namaKepalaKeluarga, String alamat, String fotoKepalaKeluarga) {
        this.nik = nomorKK;
        this.nama = namaKepalaKeluarga;

    }

    // Getter and Setter
    public String getNomorKK() {
        return nik;
    }

    public void setNomorKK(String nomorKK) {
        this.nik = nik;
    }


    public String getnama() {
        return nama;
    }

    public void setnama(String nama) {
        this.nama = nama;
    }
}



