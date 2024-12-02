package com.nixie.sisuratmob.Models;

public class FieldModel {
    private String id,id_surat,nama_field,tipe,value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_surat() {
        return id_surat;
    }

    public void setId_surat(String id_surat) {
        this.id_surat = id_surat;
    }

    public String getNama_field() {
        return nama_field;
    }

    public void setNama_field(String nama_field) {
        this.nama_field = nama_field;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public FieldModel(String id, String id_surat, String nama_field, String tipe) {
        this.id = id;
        this.id_surat = id_surat;
        this.nama_field = nama_field;
        this.tipe = tipe;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
