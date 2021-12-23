package com.regitaariesti.regita_modul_individu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    //Penentuan nama dan versi database
    public DBHelper (Context context) {super(context, "db.datatransaksi", null, 1);}

    //Perintah membuat tabel dan kolom-kolom atribut
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tb_datatransaksi(id INTEGER PRIMARY KEY AUTOINCREMENT, kategori TEXT, uang INTEGER, deskripsi TEXT, tanggal TEXT, prioritas INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tb_datatransaksi");
    }

    //Memasukkan data ke dalam SQLite database
    public boolean masukkanTransaksi (SetterGetterData sgd){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("kategori", sgd.getKategori());
        cv.put("uang", sgd.getUang());
        cv.put("deskripsi", sgd.getDeskripsi());
        cv.put("tanggal", sgd.getTanggal());
        cv.put("prioritas", sgd.getPrioritas());

        return db.insert("tb_datatransaksi", null, cv) > 0;
    }

    //Mendapatkan seluruh data
    public Cursor dapatkanSemuaTransaksi () {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + "tb_datatransaksi", null);
    }

    //Mendapatkan transaksi berdasarkan id
    public Cursor dapatkanTransaksi (int id) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + "tb_datatransaksi" + " WHERE id = " +id, null);
    }

    //Mendapatkan data teratas
    public Cursor dapatkanDataTeratas () {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + "tb_datatransaksi" + " ORDER BY id DESC LIMIT 1", null);
    }

    //Memperbaharui transaksi
    public boolean perbaharuiTransaksi(SetterGetterData sgd, int id) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("kategori", sgd.getKategori());
        cv.put("uang", sgd.getUang());
        cv.put("deskripsi", sgd.getDeskripsi());
        cv.put("tanggal", sgd.getTanggal());
        cv.put("prioritas", sgd.getPrioritas());

        return db.update("tb_datatransaksi", cv, "id" + "=" + id, null) >0;
    }

    //Metode menghapus sebuah transaksi
    public void hapusTransaksi(int id){
        SQLiteDatabase db = getReadableDatabase();
        db.delete("tb_datatransaksi", "id" + "=" + id, null);
    }
}