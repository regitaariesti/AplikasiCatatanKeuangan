package com.regitaariesti.regita_modul_individu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewData extends AppCompatActivity {
    private DBHelper MyDatabase;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList IdList;
    private ArrayList KategoriList;
    private ArrayList JumlahList;
    private ArrayList TanggalList;
    private ArrayList DeskripsiList;
    private ArrayList SkalaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
//        getSupportActionBar().setTitle("Daftar Transaksi");
        IdList = new ArrayList<>();
        KategoriList = new ArrayList<>();
        JumlahList = new ArrayList<>();
        TanggalList = new ArrayList<>();
        DeskripsiList = new ArrayList<>();
        SkalaList = new ArrayList<>();
        MyDatabase = new DBHelper(getBaseContext());
        recyclerView = findViewById(R.id.recycler);
        getData();
        //Menggunakan Layout Manager, Dan Membuat List Secara Vertical
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerViewAdapter(IdList, KategoriList, JumlahList, TanggalList, DeskripsiList, SkalaList);
        //Memasang Adapter pada RecyclerView
        recyclerView.setAdapter(adapter);
    }

    //Berisi Statement-Statement Untuk Mengambi Data dari Database
    @SuppressLint("Recycle")
    protected void getData(){
        //Mengambil Repository dengan Mode Membaca
        final DBHelper dh = new DBHelper(getApplicationContext());
        Cursor cursor = dh.dapatkanSemuaTransaksi();
        cursor.moveToFirst();//Memulai Cursor pada Posisi Awal

        //Melooping Sesuai Dengan Jumlan Data (Count) pada cursor
        for(int count=0; count < cursor.getCount(); count++){

            cursor.moveToPosition(count);//Berpindah Posisi dari no index 0 hingga no index terakhir
            IdList.add(cursor.getInt(0));//Menambil Data Dari Kolom 0 (Id)
            KategoriList.add(cursor.getString(1));//Menambil Data Dari Kolom 1 (Kategori)
            JumlahList.add(cursor.getInt(2));//Menambil Data Dari Kolom 2 (Jumlah Uang)
            TanggalList.add(cursor.getString(4));//Menambil Data Dari Kolom 4 (Tanggal)
            DeskripsiList.add(cursor.getString(3));//Menambil Data Dari Kolom 3 (Deskripsi)
            SkalaList.add(cursor.getInt(5));//Menambil Data Dari Kolom 5 (Skala Prioritas)
        }
    }

    public void tambah(View view) {
        Intent intent = new Intent(ViewData.this, MainActivity.class);
        startActivity(intent);
    }
}
