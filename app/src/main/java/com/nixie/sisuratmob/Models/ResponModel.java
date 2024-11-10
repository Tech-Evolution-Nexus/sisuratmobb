package com.nixie.sisuratmob.Models;

import java.util.List;

public class ResponModel {
    private List<BiodataModel> biodata;
    private List<LampiranSuratModel> data;
    private List<RiwayatSurat> datariwayat;
    private List<ListKkModel> datakk;
    private List<Surat> datasurat;



    public ResponModel(List<BiodataModel> biodata, List<LampiranSuratModel> data, List<RiwayatSurat> datariwayat, List<ListKkModel> datakk, List<Surat> datasurat) {
        this.biodata = biodata;
        this.data = data;
        this.datariwayat = datariwayat;
        this.datakk = datakk;
        this.datasurat = datasurat;
    }

    public List<ListKkModel> getDatakk() {
        return datakk;
    }

    public void setDatakk(List<ListKkModel> datakk) {
        this.datakk = datakk;
    }

    public List<BiodataModel> getBiodata() {
        return biodata;
    }

    public void setBiodata(List<BiodataModel> biodata) {
        this.biodata = biodata;
    }

    public List<LampiranSuratModel> getData() {
        return data;
    }

    public void setData(List<LampiranSuratModel> data) {
        this.data = data;
    }

    public List<RiwayatSurat> getDatariwayat() {
        return datariwayat;
    }

    public void setDatariwayat(List<RiwayatSurat> datariwayat) {
        this.datariwayat = datariwayat;
    }
    public List<Surat> getDatasurat() {
        return datasurat;
    }

    public void setDatasurat(List<Surat> datasurat) {
        this.datasurat = datasurat;
    }
}