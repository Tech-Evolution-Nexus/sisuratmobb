package com.nixie.sisuratmob.Models;

public class Berita {
    private String judul;
    private String subjudul;
    private String deskripsi;
    private int gambarResId; // ID gambar dari drawable

    public Berita(String judul, String subjudul, String deskripsi, int gambarResId) {
        this.judul = judul;
        this.subjudul = subjudul;
        this.deskripsi = deskripsi;
        this.gambarResId = gambarResId;
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

    public int getGambarResId() {
        return gambarResId;
    }
}



//package com.nixie.sisuratmob.Models;
//
//public class Berita {
//    private String judul;
//    private String subjudul;
//    private String deskripsi;
//    private String gambarUrl;
//
//    public Berita(String judul, String subjudul, String deskripsi, String gambarUrl) {
//        this.judul = judul;
//        this.subjudul = subjudul;
//        this.deskripsi = deskripsi;
//        this.gambarUrl = gambarUrl;
//    }
//
//    public String getJudul() {
//        return judul;
//    }
//
//    public String getSubjudul() {
//        return subjudul;
//    }
//
//    public String getDeskripsi() {
//        return deskripsi;
//    }
//
//    public String getGambarUrl() {
//        return gambarUrl;
//    }
//}
