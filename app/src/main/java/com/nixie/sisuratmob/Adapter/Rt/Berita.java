package com.nixie.sisuratmob.Adapter.Rt;

public class Berita {

    private String judul;
    private String subjudul; // Opsional, jika perlu
    private String deskripsi; // Opsional, jika perlu
    private int gambarUrl;

    public Berita(String judul, String subjudul, String deskripsi, int gambarUrl) {
        this.judul = judul;
        this.subjudul = subjudul;
        this.deskripsi = deskripsi;
        this.gambarUrl = gambarUrl;
    }

    public String getJudul() {
        return judul;
    }

    public String getSubjudul() {
        return subjudul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public int getGambarUrl() {
        return gambarUrl;
    }
}

