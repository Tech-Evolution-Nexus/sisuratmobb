package com.nixie.sisuratmob.Models;

public class BeritaRw {

    private String judul ;
    private String subjudul;
    private String deskripsi;
    private int gambarUrl;

    public BeritaRw(String judul ,String subjudul,String deskripsi,int gambarUrl){
        this.judul = judul;
        this.subjudul = subjudul;
        this.deskripsi = deskripsi;
        this.gambarUrl = gambarUrl;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getSubjudul() {
        return subjudul;
    }

    public void setSubjudul(String subjudul) {
        this.subjudul = subjudul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public int getGambarUrl() {
        return gambarUrl;
    }

    public void setGambarUrl(int gambarUrl) {
        this.gambarUrl = gambarUrl;
    }
}
