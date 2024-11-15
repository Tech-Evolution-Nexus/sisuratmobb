package com.nixie.sisuratmob.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.nixie.sisuratmob.Models.RegistrasiModel;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Esurat_badean";
    private static final int VERSION = 2 ;
    private static final String TABLE_USER = "user";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String creatable = "CREATE TABLE " + TABLE_USER + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nik TEXT, " +
                "nama_lengkap TEXT, " +
                "tgl_lahir TEXT, " +
                "jenis_kelamin TEXT, " +
                "tempat_lahir TEXT, " +
                "agama TEXT, " +
                "pendidikan TEXT, " +
                "pekerjaan TEXT, " +
                "golongan_darah TEXT, " +
                "status_perkawinan TEXT, " +
                "status_keluarga TEXT, " +
                "kewarganegaraan TEXT, " +
                "nama_ayah TEXT, " +
                "nama_ibu TEXT, " +
                "email TEXT, " +
                "password TEXT, " +
                "no_hp TEXT" +
                ")";
        db.execSQL(creatable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db); // Buat tabel baru
    }



    public void addUser(RegistrasiModel registrasiModel){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("nik", registrasiModel.getNik());
        values.put("nama_lengkap", registrasiModel.getNama_lengkap());
        values.put("jenis_kelamin", registrasiModel.getJenis_kelamin());
        values.put("tempat_lahir", registrasiModel.getTempat_lahir());
        values.put("tgl_lahir", registrasiModel.getTgl_lahir());
        values.put("agama", registrasiModel.getAgama());
        values.put("pendidikan", registrasiModel.getPendidikan());
        values.put("pekerjaan", registrasiModel.getPekerjaan());
        values.put("status_perkawinan", registrasiModel.getStatus_perkawinan());
        values.put("status_keluarga", registrasiModel.getStatus_keluarga());
        values.put("kewarganegaraan", registrasiModel.getKewarganegaraan());
        values.put("nama_ayah", registrasiModel.getNama_ayah());
        values.put("nama_ibu", registrasiModel.getNama_ibu());
        values.put("password", registrasiModel.getPassword());
        db.insert(TABLE_USER,null,values);
        db.close();

    }
    public boolean checkUser(String nik, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE nik=? AND password=?", new String[]{nik, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean checkNIKExists(String nik) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USER + " WHERE nik=?", new String[]{nik});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    //untuk mengetahui apakah passwrd yang diubah sesuai denganNik yang ada didtabase
    public boolean updatePasswordByNIK(String nik, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", newPassword);

        // Memperbarui password berdasarkan NIK
        int rows = db.update(TABLE_USER, values, "nik = ?", new String[]{nik});
        return rows > 0; // Mengembalikan true jika ada baris yang diperbarui
    }



}
