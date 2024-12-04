package com.nixie.sisuratmob.Helpers;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Helpers {
    public static final String BASE_URL = "http://192.168.100.205/SISURAT/";

    public static String formatTanggal(String tanggalAsli) {
        try {
            // Format tanggal asli (dari database)
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
            // Format yang diinginkan
            SimpleDateFormat desiredFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("id", "ID"));
            // Parse dan format ulang
            return desiredFormat.format(originalFormat.parse(tanggalAsli));
        } catch (Exception e) {
            e.printStackTrace();
            return tanggalAsli; // Jika gagal, gunakan tanggal asli
        }
    }
    public static String formatTanggalnoday(String tanggalAsli) {
        try {
            // Format tanggal asli (dari database)
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
            // Format yang diinginkan
            SimpleDateFormat desiredFormat = new SimpleDateFormat("dd MMMM yyyy", new Locale("id", "ID"));
            // Parse dan format ulang
            return desiredFormat.format(originalFormat.parse(tanggalAsli));
        } catch (Exception e) {
            e.printStackTrace();
            return tanggalAsli; // Jika gagal, gunakan tanggal asli
        }
    }
    public static String formatTanggalalltime(String tanggalAsli) {
        try {
            // Format tanggal asli (dari database)
            SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
            // Format yang diinginkan
            SimpleDateFormat desiredFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy hh:mm", new Locale("id", "ID"));
            // Parse dan format ulang
            return desiredFormat.format(originalFormat.parse(tanggalAsli));
        } catch (Exception e) {
            e.printStackTrace();
            return tanggalAsli; // Jika gagal, gunakan tanggal asli
        }
    }
}   
