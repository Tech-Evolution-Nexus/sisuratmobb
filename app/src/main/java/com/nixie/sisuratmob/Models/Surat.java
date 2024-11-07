package com.nixie.sisuratmob.Models;

public class Surat {

    private String image;
    private String nama_surat;

    public void Surat(String image, String nama_surat){
        this.image = image;
        this.nama_surat = nama_surat;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNama_surat() {
        return nama_surat;
    }

    public void setNama_surat(String nama_surat) {
        this.nama_surat = nama_surat;
    }
}
