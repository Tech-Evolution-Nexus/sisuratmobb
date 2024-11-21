package com.nixie.sisuratmob.Models;

public class UserLoginModel {
    private String nik;
    private String password;
    private String fcm_token;


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

    public String getFcm_token() {
        return fcm_token;
    }

    public void setFcm_token(String fcm_token) {
        this.fcm_token = fcm_token;
    }

    public UserLoginModel(String nik, String password, String fcm_token) {
        this.nik = nik;
        this.password = password;
        this.fcm_token = fcm_token;
    }
}
