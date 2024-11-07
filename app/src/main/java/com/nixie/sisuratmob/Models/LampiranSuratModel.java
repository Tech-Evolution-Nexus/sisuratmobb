package com.nixie.sisuratmob.Models;

public class LampiranSuratModel {
    private int idSurat;
    private int id;
    private String nama_lampiran	;

    public LampiranSuratModel(int idSurat, int id, String nama_lampiran	) {
        this.idSurat = idSurat;
        this.id = id;
        this.nama_lampiran	 = nama_lampiran	;
    }

    public int getIdSurat() {
        return idSurat;
    }

    public void setIdSurat(int idSurat) {
        this.idSurat = idSurat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaLampiran() {
        return nama_lampiran	;
    }

    public void setNamaLampiran(String namaLampiran) {
        this.nama_lampiran	 = namaLampiran;
    }
}
