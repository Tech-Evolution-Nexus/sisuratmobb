package com.nixie.sisuratmob.Models;

public class RiwayatSurat {
    private int id_surat;
    private String nomor_surat,status,nik,created_at;

    public RiwayatSurat(int id_surat, String nomor_surat, String status, String nik, String created_at) {
        this.id_surat = id_surat;
        this.nomor_surat = nomor_surat;
        this.status = status;
        this.nik = nik;
        this.created_at = created_at;
    }

    public int getId_surat() {
        return id_surat;
    }

    public void setId_surat(int id_surat) {
        this.id_surat = id_surat;
    }

    public String getNomor_surat() {
        return nomor_surat;
    }

    public void setNomor_surat(String nomor_surat) {
        this.nomor_surat = nomor_surat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
    @Override
    public String toString() {
        return "RiwayatSurat{" +
                "id_surat=" + id_surat +
                ", nomor_surat='" + nomor_surat + '\'' +
                ", status='" + status + '\'' +
                ", nik='" + nik + '\'' +
                ", created_at='" + created_at + '\'' +
                '}';
    }
}
