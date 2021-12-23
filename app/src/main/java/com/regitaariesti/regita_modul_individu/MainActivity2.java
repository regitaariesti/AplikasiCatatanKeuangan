package com.regitaariesti.regita_modul_individu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {
    TextView tvMessage;
    boolean first=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tvMessage = findViewById(R.id.tvMessage);
        Bundle extras = getIntent().getExtras();
        String message="";
        message+="Kategori : "+(extras.getInt("kategori")==0?"Pengeluaran":"Pemasukan");
        message+="\nJumlah Uang : Rp."+extras.getString("uang");
        message+="\nDeskripsi : "+extras.getString("deskripsi");
        message+="\nTanggal : "+extras.getString("tanggal");
        if(extras.getInt("kategori")==0){
            message+="\nPrioritas : "+extras.getInt("point");
        }
        tvMessage.setText(message);
        Log.i("TAG", "BARU");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("message", "Input lagi");
        startActivity(i);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "Program masih aktif", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(first){
            Toast.makeText(this, "Halo...", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Halo again....", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        first=false;
    }

    public void hapusData(View view) {
    }

    public void editData(View view) {
    }
}
