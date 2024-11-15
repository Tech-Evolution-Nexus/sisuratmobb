package com.nixie.sisuratmob.Models;

import java.util.List;

public class FormModel {
    private String nik;
    private String idSurat;
    private String keterangan;
    private List<LampiranSuratModel> lampiranList;

    public FormModel(String nik, String keterangan,List<LampiranSuratModel> lampiranList) {
        this.nik = nik;
        this.keterangan = keterangan;
        this.lampiranList=lampiranList;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getIdSurat() {
        return idSurat;
    }

    public void setIdSurat(String idSurat) {
        this.idSurat = idSurat;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
    public List<LampiranSuratModel> getLampiranList() { return lampiranList; }
    public void setLampiranList(List<LampiranSuratModel> lampiranList) { this.lampiranList = lampiranList; }
}
