package com.nixie.sisuratmob.Models;

public class DetailHistoriModel {
    private  int id;
    private String nama_lampiran,url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama_lampiran() {
        return nama_lampiran;
    }

    public void setNama_lampiran(String nama_lampiran) {
        this.nama_lampiran = nama_lampiran;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public DetailHistoriModel(int id, String nama_lampiran, String url) {
        this.id = id;
        this.nama_lampiran = nama_lampiran;
        this.url = url;
    }
}
