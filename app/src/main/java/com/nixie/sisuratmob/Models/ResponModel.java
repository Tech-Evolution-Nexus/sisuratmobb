package com.nixie.sisuratmob.Models;

import java.util.List;

public class ResponModel {
    private List<BiodataModel> biodata;
    private List<LampiranSuratModel> data;
    private List<RiwayatSurat> datariwayat;
    public ResponModel(List<BiodataModel> biodata, List<LampiranSuratModel> data,List<RiwayatSurat> datariwayat) {
        this.biodata = biodata;
        this.data = data;
        this.datariwayat = datariwayat;
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
}
