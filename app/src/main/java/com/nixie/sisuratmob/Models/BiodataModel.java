package com.nixie.sisuratmob.Models;

public class BiodataModel {
    private int id;
    private String nama_lengkap	;
    private String no_kk;
    private String kk_tgl;

    public String getNama_lengkap() {
        return nama_lengkap;
    }

    public void setNama_lengkap(String nama_lengkap) {
        this.nama_lengkap = nama_lengkap;
    }

    public String getNo_kk() {
        return no_kk;
    }

    public void setNo_kk(String no_kk) {
        this.no_kk = no_kk;
    }

    public String getKk_tgl() {
        return kk_tgl;
    }

    public void setKk_tgl(String kk_tgl) {
        this.kk_tgl = kk_tgl;
    }

    public int getKode_pos() {
        return kode_pos;
    }

    public void setKode_pos(int kode_pos) {
        this.kode_pos = kode_pos;
    }

    private String nik;
    private String alamat;
    private int rt;
    private int rw;
    private int kode_pos;
    private String kelurahan;
    private String kecamatan;
    private String kabupaten;
    private String provinsi;

    public BiodataModel(int id, String nama_lengkap	, String no_kk, String kk_tgl, String nik, int rt, int rw, int kode_pos, String kelurahan, String kecamatan) {
        this.id = id;
        this.nama_lengkap = nama_lengkap	;
        this.no_kk = no_kk;
        this.kk_tgl = kk_tgl;
        this.nik = nik;
        this.alamat = alamat;
        this.rt = rt;
        this.rw = rw;
        this.kode_pos = kode_pos;
        this.kelurahan = kelurahan;
        this.kecamatan = kecamatan;
        this.kabupaten = kabupaten;
        this.provinsi = provinsi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaLengkap() {
        return nama_lengkap	;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.nama_lengkap	 = namaLengkap;
    }

    public String getNoKk() {
        return no_kk;
    }

    public void setNoKk(String no_kk) {
        this.no_kk = no_kk;
    }

    public String getKkTgl() {
        return kk_tgl;
    }

    public void setKkTgl(String kkTgl) {
        this.kk_tgl = kkTgl;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public int getRt() {
        return rt;
    }

    public void setRt(int rt) {
        this.rt = rt;
    }

    public int getRw() {
        return rw;
    }

    public void setRw(int rw) {
        this.rw = rw;
    }

    public int getKodePos() {
        return kode_pos;
    }

    public void setKodePos(int kodePos) {
        this.kode_pos = kodePos;
    }

    public String getKelurahan() {
        return kelurahan;
    }

    public void setKelurahan(String kelurahan) {
        this.kelurahan = kelurahan;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getKabupaten() {
        return kabupaten;
    }

    public void setKabupaten(String kabupaten) {
        this.kabupaten = kabupaten;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }
}
