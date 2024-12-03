package com.nixie.sisuratmob.Models;

public class AktivasiModel {
    private String nik ;
    private String no_hp;
    private String password;
    private  String email;
    public AktivasiModel(String nik,String email, String no_hp, String password) {

        this.nik = nik;
        this.email = email;
        this.no_hp = no_hp;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
