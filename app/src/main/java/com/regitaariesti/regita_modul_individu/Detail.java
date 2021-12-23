package com.regitaariesti.regita_modul_individu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Detail extends AppCompatActivity {
    TextView tvMessage;
    boolean first=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tvMessage = findViewById(R.id.tvMessage);
        Bundle extras = getIntent().getExtras();
        String message="";
        message+="Kategori : "+extras.getString("kategori");
        message+="\nJumlah Uang : Rp."+extras.getInt("jumlah");
        message+="\nDeskripsi : "+extras.getString("deskripsi");
        message+="\nTanggal : "+extras.getString("tanggal");
        if(extras.getString("kategori").equals("Pengeluaran")){
            message+="\nPrioritas : "+extras.getInt("skala");
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

    public void editData(View view) {
        Intent intent = new Intent(Detail.this, EditData.class);
        intent.putExtra("id", getIntent().getExtras().getInt("id"));
        startActivity(intent);
    }

    public void deleteData(View view) {
        String radiokategori = getIntent().getExtras().getString("kategori");
        String message = "Yakin menghapus data " +radiokategori+ "?";
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Hapus Data");
        alertDialogBuilder
                .setMessage(message)
                .setIcon(R.drawable.ic_baseline_info_24)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        DBHelper dbHelper = new DBHelper(getApplicationContext());
                        dbHelper.hapusTransaksi(getIntent().getExtras().getInt("id"));
                        Toast.makeText(getApplicationContext(), "Transaksi berhasil dihapus", Toast.LENGTH_SHORT).show();
                        dbHelper.close();

                        Intent intent = new Intent(Detail.this, ViewData.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog =alertDialogBuilder.create();
        alertDialog.show();
    }

    public void backButton(View view) {
        onBackPressed();
    }
}