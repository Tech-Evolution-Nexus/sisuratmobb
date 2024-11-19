package com.nixie.sisuratmob.Models;

public class RiwayatSurat {
    private int id,id_surat;
    private String nomor_surat;
    private String no_pengantar;
    private String status;
    private String keterangan;
    private String keterangan_ditolak;
    private String file_pdf;
    private String pengantar_rt;
    private String pengantar_rw;
    private String nik;
    private String kode_kelurahan;
    private String nomor_surat_tambahan;
    private String created_at;
    private String updated_at;
    private String nama_surat;
    private String image;

    public RiwayatSurat(int id, int id_surat, String nomor_surat, String no_pengantar, String status, String keterangan, String keterangan_ditolak, String file_pdf, String pengantar_rt, String pengantar_rw, String nik, String kode_kelurahan, String nomor_surat_tambahan, String created_at, String updated_at, String nama_surat, String image) {
        this.id = id;
        this.id_surat = id_surat;
        this.nomor_surat = nomor_surat;
        this.no_pengantar = no_pengantar;
        this.status = status;
        this.keterangan = keterangan;
        this.keterangan_ditolak = keterangan_ditolak;
        this.file_pdf = file_pdf;
        this.pengantar_rt = pengantar_rt;
        this.pengantar_rw = pengantar_rw;
        this.nik = nik;
        this.kode_kelurahan = kode_kelurahan;
        this.nomor_surat_tambahan = nomor_surat_tambahan;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.nama_surat = nama_surat;
        this.image = image;
    }

    public int getId_surat() {
        return id_surat;
    }

    public void setId_surat(int id_surat) {
        this.id_surat = id_surat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomor_surat() {
        return nomor_surat;
    }

    public void setNomor_surat(String nomor_surat) {
        this.nomor_surat = nomor_surat;
    }

    public String getNo_pengantar() {
        return no_pengantar;
    }

    public void setNo_pengantar(String no_pengantar) {
        this.no_pengantar = no_pengantar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getKeterangan_ditolak() {
        return keterangan_ditolak;
    }

    public void setKeterangan_ditolak(String keterangan_ditolak) {
        this.keterangan_ditolak = keterangan_ditolak;
    }

    public String getFile_pdf() {
        return file_pdf;
    }

    public void setFile_pdf(String file_pdf) {
        this.file_pdf = file_pdf;
    }

    public String getPengantar_rt() {
        return pengantar_rt;
    }

    public void setPengantar_rt(String pengantar_rt) {
        this.pengantar_rt = pengantar_rt;
    }

    public String getPengantar_rw() {
        return pengantar_rw;
    }

    public void setPengantar_rw(String pengantar_rw) {
        this.pengantar_rw = pengantar_rw;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getKode_kelurahan() {
        return kode_kelurahan;
    }

    public void setKode_kelurahan(String kode_kelurahan) {
        this.kode_kelurahan = kode_kelurahan;
    }

    public String getNomor_surat_tambahan() {
        return nomor_surat_tambahan;
    }

    public void setNomor_surat_tambahan(String nomor_surat_tambahan) {
        this.nomor_surat_tambahan = nomor_surat_tambahan;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getNama_surat() {
        return nama_surat;
    }

    public void setNama_surat(String nama_surat) {
        this.nama_surat = nama_surat;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
