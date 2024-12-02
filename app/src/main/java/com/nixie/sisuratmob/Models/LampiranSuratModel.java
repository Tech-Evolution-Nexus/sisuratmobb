package com.nixie.sisuratmob.Models;

import android.net.Uri;

public class LampiranSuratModel {
    private int id_surat;
    private int id;
    private String nama_lampiran,image;
    private Uri imageUri;

    public LampiranSuratModel(int id_surat, int id, String nama_lampiran) {
        this.id_surat = id_surat;
        this.id = id;
        this.nama_lampiran = nama_lampiran;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNama_lampiran() {
        return nama_lampiran;
    }

    public void setNama_lampiran(String nama_lampiran) {
        this.nama_lampiran = nama_lampiran;
    }

    public int getIdSurat() {
        return id_surat;
    }

    public void setIdSurat(int id_surat) {
        this.id_surat = id_surat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaLampiran() {
        return nama_lampiran	;
    }

    public void setNamaLampiran(String namaLampiran) {
        this.nama_lampiran	 = namaLampiran;
    }
    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }
}
