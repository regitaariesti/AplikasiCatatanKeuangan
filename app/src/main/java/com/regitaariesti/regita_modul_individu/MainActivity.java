package com.regitaariesti.regita_modul_individu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    String nama_kategori = "Pengeluaran";
    SeekBar sbPrioritas;
    TextView tvLabelprioritas;
    EditText etUang, etTanggal, etDeskripsi;
    RadioGroup rgKategori;
    LinearLayout prioritas;
    Button btnSubmit;
    CheckBox cbVerifikasi;
    Calendar calendar = Calendar.getInstance();
    int point=1, kategori=0;
    AlertDialog.Builder builder;
    //deklarasi SQLiteQuery
    //String SQLiteQuery;
    //SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prioritas = findViewById(R.id.prioritas);
        sbPrioritas = findViewById(R.id.sbPrioritas);
        tvLabelprioritas = findViewById(R.id.tvLabelprioritas);
        etUang = findViewById(R.id.etUang);
        rgKategori = findViewById(R.id.rgKategori);
        etTanggal = findViewById(R.id.etTanggal);
        etDeskripsi = findViewById(R.id.etDeskripsi);
        cbVerifikasi = findViewById(R.id.cbVerifikasi);
        btnSubmit = findViewById(R.id.btnSubmit);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        etTanggal.setOnClickListener(v -> Show_Calendar());
        if(getIntent().getExtras()!=null) {
            if (getIntent().getExtras().getString("message") != null) {
                Toast.makeText(this, getIntent().getExtras().getString("message"), Toast.LENGTH_LONG).show();
            }
        }
        sbPrioritas.setMax(9);
        sbPrioritas.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                point=i+1;
                tvLabelprioritas.setText(String.valueOf(point));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        rgKategori.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i){
                case R.id.rbPengeluaran:
                    etUang.setHint("Masukan jumlah uang pengeluaran");
                    prioritas.setVisibility(View.VISIBLE);
                    kategori=0;
                    nama_kategori = "Pengeluaran";
                    break;
                case R.id.rbPemasukan:
                    etUang.setHint("Masukan jumlah uang pemasukan");
                    prioritas.setVisibility(View.GONE);
                    kategori=1;
                    nama_kategori = "Pemasukan";
                    break;
            }
        });

        btnSubmit.setOnClickListener(view -> {
            if(etUang.getText().toString().isEmpty()||
                    etDeskripsi.getText().toString().isEmpty()||
                    etTanggal.getText().toString().isEmpty()){
                Toast.makeText(this, "Isi Semua Field", Toast.LENGTH_LONG).show();
            }else if(!cbVerifikasi.isChecked()){
                Toast.makeText(this, "Input verifikasi terlebih dahulu", Toast.LENGTH_LONG).show();
            }else{/*
            //alert dialog
            String message = "";
            message+="Kategori : "+(kategori==0?"Pengeluaran":"Pemasukan");
            message+="\nJumlah Uang : Rp."+etUang.getText().toString();
            message+="\nDeskripsi : "+etDeskripsi.getText().toString();
            message+="\nTanggal : "+etTanggal.getText().toString();
            if(kategori==0){
                message+="\nPrioritas : "+point;
            }
            builder.setTitle("Data Catatan Keuangan")
                    .setMessage(message)
                    .setPositiveButton("OK", (dialog, which) -> new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int id) {dialog.cancel();}
                    })
                    .setIcon(R.drawable.ic_baseline_description_24); //declare ikon
            builder.show();
            // halaman kedua
                Intent i = new Intent(this, MainActivity2.class);
                i.putExtra("kategori", kategori);
                i.putExtra("uang", etUang.getText().toString());
                i.putExtra("deskripsi", etDeskripsi.getText().toString());
                i.putExtra("tanggal", etTanggal.getText().toString());
                if(kategori==0) {
                    i.putExtra("point", point);
                }
                finish();
                startActivity(i);


            sqLiteDatabase = openOrCreateDatabase("db.transaksi", Context.MODE_PRIVATE, null);
            sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS tb_transaksi (id INTEGER PRIMARY KEY AUTOINCREMENT, kategori TEXT, uang INTEGER, deskripsi TEXT, tanggal TEXT, prioritas INTEGER)");

            Intent i = new Intent(this, MainActivity2.class);

            String messagekategori = "";
            if(kategori==0){
                messagekategori = "Pengeluaran";
            }
            if (kategori==1){
                messagekategori = "Pemasukan";
            }
            String messageuang = etUang.getText().toString();
            String messagedeskripsi = etDeskripsi.getText().toString();
            String messagetanggal = etTanggal.getText().toString();
            String messagepoint = "";
            if (kategori==0){
                messagepoint = String.valueOf(point);
            }
            if (kategori==1){
                messagepoint = "0";
            }

            i.putExtra("kategori", kategori);
            i.putExtra("uang", etUang.getText().toString());
            i.putExtra("deskripsi", etDeskripsi.getText().toString());
            i.putExtra("tanggal", etTanggal.getText().toString());
            if (kategori==0){
                i.putExtra("point", point);
            }

            /*SQLiteQuery = "INSERT INTO tb_transaksi (kategori, uang, deskripsi, tanggal, prioritas) VALUES ('" + messagekategori + "', '" + messageuang + "', '" + messagedeskripsi + "', '" + messagetanggal + "', '" + messagepoint + "');";
            sqLiteDatabase.execSQL(SQLiteQuery);*/

                DBHelper dbHelper = new DBHelper(getApplicationContext());
                SetterGetterData sgd = new SetterGetterData();
                sgd.setKategori(nama_kategori);
                sgd.setUang(Integer.parseInt(etUang.getText().toString()));
                sgd.setDeskripsi(etDeskripsi.getText().toString());
                sgd.setTanggal(etTanggal.getText().toString());
                if(kategori==0){
                    sgd.setPrioritas(point);
                }
                else{
                    sgd.setPrioritas(0);
                }
                boolean input;
                input = dbHelper.masukkanTransaksi(sgd);
                if(input){
                    Toast.makeText(getApplicationContext(), "Transaksi berhasil disimpan", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Kesalahan terjadi!", Toast.LENGTH_SHORT).show();
                }
                dbHelper.close();

                Intent intent = new Intent(MainActivity.this, ViewData.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, kategori==0?"Data Pengeluaran":"Data Pemasukan", Toast.LENGTH_LONG).show();
    }
    private void Show_Calendar(){
        etTanggal.setShowSoftInputOnFocus(false);
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view1, year, month, dayOfMonth) -> {
                    month = month + 1;
                    String date;
                    String m,d;
                    if (month < 10) {
                        m="0"+month;
                    } else {
                        m= String.valueOf(month);
                    }
                    if (dayOfMonth < 10) {
                        d="0"+dayOfMonth;
                    } else {
                        d= String.valueOf(dayOfMonth);
                    }
                    date = d + "-" + m + "-" + year;
                    etTanggal.setText(date);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        if(!datePickerDialog.isShowing()) {
            datePickerDialog.show();
        }
    }

    public void view(View view){
        Intent intent = new Intent(MainActivity.this, ViewData.class);
        startActivity(intent);
    }
}