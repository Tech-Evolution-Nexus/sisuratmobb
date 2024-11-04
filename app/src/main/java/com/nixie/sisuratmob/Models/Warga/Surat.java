package com.nixie.sisuratmob.Models.Warga;

public class Surat {

    private int gambarSurat;
    private String namaSurat;
    public Surat(String suratKeterangan, int kermaaian) {
    }


    public void Surat(int gambarSurat, String namaSurat){
        this.gambarSurat = gambarSurat;
        this.namaSurat = namaSurat;
    }

    public int getGambarSurat() {
        return gambarSurat;
    }

    public String getNamaSurat() {
        return namaSurat;
    }

}
