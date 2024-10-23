package com.nixie.sisuratmob.Models;

public class UserModel {
    private int id ;
    private String nik ;
    private String nohp;
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public  UserModel(String nik , String nohp , String password){
        this.id = id;
        this.nik = nik;
        this.nohp = nohp;
        this.password = password;


    }

}
