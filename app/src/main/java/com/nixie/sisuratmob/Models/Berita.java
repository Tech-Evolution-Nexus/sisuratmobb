package com.nixie.sisuratmob.Models;

public class Berita {
    private int id;
    private String judul;
    private String sub_judul; // Opsional, jika perlu
    private String deskripsi; // Opsional, jika perlu
    private String gambar;
    private String created_at;

    public Berita(int id, String judul, String sub_judul, String deskripsi, String gambar, String created_at) {
        this.id = id;
        this.judul = judul;
        this.sub_judul = sub_judul;
        this.deskripsi = deskripsi;
        this.gambar = gambar;
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getSubjudul() {
        return sub_judul;
    }

    public void setSubjudul(String sub_judul) {
        this.sub_judul = sub_judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
