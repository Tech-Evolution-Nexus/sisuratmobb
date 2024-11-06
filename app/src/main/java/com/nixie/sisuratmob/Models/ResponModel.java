package com.nixie.sisuratmob.Models;

import java.util.List;

public class ResponModel {
    private List<BiodataModel> biodata;
    private List<LampiranSuratModel> data;

    public ResponModel(List<BiodataModel> biodata, List<LampiranSuratModel> data) {
        this.biodata = biodata;
        this.data = data;
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
}
