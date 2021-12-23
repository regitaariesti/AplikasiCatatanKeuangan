package com.regitaariesti.regita_modul_individu;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class EditData extends AppCompatActivity {
    String nama_kategori = "Pengeluaran";
    SeekBar sbPrioritas;
    TextView tvLabelprioritas;
    EditText etUang, etTanggal, etDeskripsi;
    RadioGroup rgKategori;
    LinearLayout prioritas;
    CheckBox cbVerifikasi;
    Calendar calendar = Calendar.getInstance();
    RadioButton pemasukan, pengeluaran;
    int point=1, kategori=0;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);
        prioritas = findViewById(R.id.prioritas);
        sbPrioritas = findViewById(R.id.sbPrioritas);
        tvLabelprioritas = findViewById(R.id.tvLabelprioritas);
        etUang = findViewById(R.id.etUang);
        rgKategori = findViewById(R.id.rgKategori);
        etTanggal = findViewById(R.id.etTanggal);
        etDeskripsi = findViewById(R.id.etDeskripsi);
        cbVerifikasi = findViewById(R.id.cbVerifikasi);
        pemasukan = findViewById(R.id.rbPemasukan);
        pengeluaran = findViewById(R.id.rbPengeluaran);

        id = getIntent().getExtras().getInt("id");
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        Cursor cursor = dbHelper.dapatkanTransaksi(id);
        cursor.moveToFirst();

        String radiokategori = cursor.getString(1);
        if(radiokategori.equals("Pemasukan")){
            pemasukan.setChecked(true);
            prioritas.setVisibility(View.GONE);
        }

        etUang.setText(String.valueOf(cursor.getInt(2)));
        etDeskripsi.setText(cursor.getString(3));
        etTanggal.setText(cursor.getString(4));
        tvLabelprioritas.setText(String.valueOf(cursor.getInt(5)));

        dbHelper.close();

        etTanggal.setOnClickListener(v -> Show_Calendar());
        if(getIntent().getExtras()!=null) {
            if (getIntent().getExtras().getString("message") != null) {
                Toast.makeText(this, getIntent().getExtras().getString("message"), Toast.LENGTH_LONG).show();
            }
        }
        sbPrioritas.setMax(9);
        sbPrioritas.setProgress(Integer.parseInt(tvLabelprioritas.getText().toString()));
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
                    prioritas.setVisibility(View.VISIBLE);
                    kategori=0;
                    nama_kategori = "Pengeluaran";
                    break;
                case R.id.rbPemasukan:
                    prioritas.setVisibility(View.GONE);
                    kategori=1;
                    nama_kategori = "Pemasukan";
                    break;
            }
        });
    }

    private void Show_Calendar() {
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

    public void edit(View view) {
        id = getIntent().getExtras().getInt("id");
        if(etUang.getText().toString().isEmpty()||
                etDeskripsi.getText().toString().isEmpty()||
                etTanggal.getText().toString().isEmpty()){
            Toast.makeText(this, "Isi Semua Field", Toast.LENGTH_LONG).show();
        }else if(!cbVerifikasi.isChecked()){
            Toast.makeText(this, "Input verifikasi terlebih dahulu", Toast.LENGTH_LONG).show();
        }else {
            DBHelper dbHelper = new DBHelper(getApplicationContext());
            SetterGetterData sgd = new SetterGetterData();
            sgd.setKategori(nama_kategori);
            sgd.setUang(Integer.parseInt(etUang.getText().toString()));
            sgd.setDeskripsi(etDeskripsi.getText().toString());
            sgd.setTanggal(etTanggal.getText().toString());
            if (kategori == 0) {
                sgd.setPrioritas(point);
            } else {
                sgd.setPrioritas(0);
            }
            boolean edit = dbHelper.perbaharuiTransaksi(sgd, id);
            if (edit) {
                Toast.makeText(getApplicationContext(), "Transaksi berhasil diperbaharui", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Kesalahan terjadi!", Toast.LENGTH_SHORT).show();
            }
            dbHelper.close();
            Intent intent = new Intent(EditData.this, ViewData.class);
            startActivity(intent);
        }
    }
}