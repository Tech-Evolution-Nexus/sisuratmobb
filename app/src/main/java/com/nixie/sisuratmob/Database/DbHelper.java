package com.nixie.sisuratmob.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.nixie.sisuratmob.Models.UserModel;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Esurat_badean";
    private static final int VERSION = 1 ;
    private static final String TABLE_USER = "user";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String creatable = "CREATE TABLE "+ TABLE_USER + "(id INTEGER PRIMARY KEY AUTOINCREMENT, nik TEXT,nohp TEXT ,password TEXT )";
        db.execSQL(creatable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
    }


    public void addUser(UserModel userModel){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("nik",userModel.getNik());
        values.put("nohp",userModel.getNohp());
        values.put("password",userModel.getPassword());
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
}
