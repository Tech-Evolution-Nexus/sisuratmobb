package com.nixie.sisuratmob.Models;

public class Surat {

    private String id;
    private String image;
    private String nama_surat;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Surat(String id, String image, String nama_surat) {
        this.id = id;
        this.image = image;
        this.nama_surat = nama_surat;
    }
}
