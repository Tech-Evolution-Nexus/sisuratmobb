package com.nixie.sisuratmob.Models;

import android.util.Log;

import java.util.List;

public class ResponModel {
    private DataWrapper data;

    public ResponModel(DataWrapper data) {
        this.data = data;
    }

    public DataWrapper getData() {
        return data;
    }

    public void setData(DataWrapper data) {
        this.data = data;
    }

    public static class DataWrapper {
        private String msg;
        private List<BiodataModel> biodata;
        private List<LampiranSuratModel> datalampiran;
        private List<RiwayatSurat> datariwayat;
        private List<ListKkModel> datakk;
        private List<Surat> datasurat;
        private List<Berita> databerita;
        private List<PengajuanSuratModel> datapengajuanpending;
        private List<PengajuanSuratModel> datapengajuanselesai;
        private List<PengajuanSuratModel> datapengajuan;

        public DataWrapper(String msg, List<BiodataModel> biodata, List<LampiranSuratModel> datalampiran, List<RiwayatSurat> datariwayat, List<ListKkModel> datakk, List<Surat> datasurat, List<Berita> databerita,List<PengajuanSuratModel> datapengajuanpending,List<PengajuanSuratModel> datapengajuanselesai) {
            this.msg = msg;
            this.biodata = biodata;
            this.datalampiran = datalampiran;
            this.datariwayat = datariwayat;
            this.datakk = datakk;
            this.datasurat = datasurat;
            this.databerita = databerita;
            this.datapengajuanpending = datapengajuanpending;
            this.datapengajuanselesai = datapengajuanselesai;
        }

        public List<Berita> getDataberita() {
            return databerita;
        }

        public void setDataberita(List<Berita> databerita) {
            this.databerita = databerita;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public List<BiodataModel> getBiodata() {
            return biodata;
        }

        public void setBiodata(List<BiodataModel> biodata) {
            this.biodata = biodata;
        }

        public List<LampiranSuratModel> getDatalampiran() {
            return datalampiran;
        }

        public void setDatalampiran(List<LampiranSuratModel> datalampiran) {
            this.datalampiran = datalampiran;
        }

        public List<RiwayatSurat> getDatariwayat() {
            return datariwayat;
        }

        public void setDatariwayat(List<RiwayatSurat> datariwayat) {
            this.datariwayat = datariwayat;
        }

        public List<ListKkModel> getDatakk() {
            return datakk;
        }

        public void setDatakk(List<ListKkModel> datakk) {
            this.datakk = datakk;
        }

        public List<Surat> getDatasurat() {
            return datasurat;
        }
        public List<PengajuanSuratModel> getDataListPengajuanPending() {
            return datapengajuanpending;
        }public List<PengajuanSuratModel> getDataListPengajuanSelesai() {
            return datapengajuanselesai;
        }

        public void setDatasurat(List<Surat> datasurat) {
            this.datasurat = datasurat;
        }
    }
}
