package com.nixie.sisuratmob.Models;

public class JenisSuratModel {

    private String namasurat;
    private String image;

    // Constructor
    public JenisSuratModel(String namasurat, String image) {
        this.namasurat = namasurat;
        this.image = image;
    }

    // Getter and Setter for namasurat
    public String getNamasurat() {
        return namasurat;
    }

    public void setNamasurat(String namasurat) {
        this.namasurat = namasurat;
    }

    // Getter and Setter for image
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}