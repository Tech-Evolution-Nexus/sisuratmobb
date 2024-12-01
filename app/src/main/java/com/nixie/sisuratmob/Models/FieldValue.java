package com.nixie.sisuratmob.Models;

public class FieldValue {
    private String id,nama_field,value;

    public String getNama_field() {
        return nama_field;
    }

    public void setNama_field(String nama_field) {
        this.nama_field = nama_field;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public FieldValue(String id, String nama_field, String value) {
        this.id = id;
        this.nama_field = nama_field;
        this.value = value;
    }
}
