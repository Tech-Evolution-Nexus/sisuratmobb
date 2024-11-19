package com.nixie.sisuratmob.Models;

public class UserLoginModel {
    private String nik;
    private String password;
    public UserLoginModel(String nik, String password ) {
        this.nik = nik;
        this.password = password;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
