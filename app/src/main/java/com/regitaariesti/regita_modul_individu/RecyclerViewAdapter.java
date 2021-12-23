package com.regitaariesti.regita_modul_individu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private ArrayList idList;
    private ArrayList kategoriList;
    private ArrayList jumlahList;
    private ArrayList tanggalList;
    private ArrayList deskripsiList;
    private ArrayList skalaList;
    private Context context;

    //Membuat Konstruktor pada Class RecyclerViewAdapter
    RecyclerViewAdapter(ArrayList idList, ArrayList kategoriList, ArrayList jumlahList, ArrayList tanggalList, ArrayList deskripsiList, ArrayList skalaList){
        this.idList = idList;
        this.kategoriList = kategoriList;
        this.jumlahList = jumlahList;
        this.tanggalList = tanggalList;
        this.deskripsiList = deskripsiList;
        this.skalaList = skalaList;
    }

    //ViewHolder Digunakan Untuk Menyimpan Referensi Dari View-View
    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView Kategori, Jumlah, Tanggal, Deskripsi;
        private CardView Card;

        ViewHolder(View itemView) {
            super(itemView);
            //Mendapatkan Context dari itemView yang terhubung dengan Activity ViewData
            context = itemView.getContext();
            //Menginisialisasi View-View untuk kita gunakan pada RecyclerView
            Card = itemView.findViewById(R.id.card);
            Kategori = itemView.findViewById(R.id.kategori);
            Jumlah = itemView.findViewById(R.id.jumlah);
            Tanggal = itemView.findViewById(R.id.tanggal);
            Deskripsi = itemView.findViewById(R.id.deskripsi);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Membuat View untuk Menyiapkan dan Memasang Layout yang Akan digunakan pada RecyclerView
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_design, parent, false);
        return new ViewHolder(V);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        //Memanggil Nilai/Value Pada View-View Yang Telah Dibuat pada Posisi Tertentufinal String Kategori = (String) kategoriList.get(position);//Mengambil data (Kategori) sesuai dengan posisi yang telah ditentukan
        final Integer Id = (Integer) idList.get(position);//Mengambil data (Id) sesuai dengan posisi yang telah ditentukan
        final String Kategori = (String) kategoriList.get(position);//Mengambil data (Kategori) sesuai dengan posisi yang telah ditentukan
        final Integer Jumlah = (Integer) jumlahList.get(position);//Mengambil data (Jumlah) sesuai dengan posisi yang telah ditentukan
        final String Tanggal = (String) tanggalList.get(position);//Mengambil data (Tanggal) sesuai dengan posisi yang telah ditentukan
        final String Deskripsi = (String) deskripsiList.get(position);//Mengambil data (Deskripsi) sesuai dengan posisi yang telah ditentukan
        final Integer Skala = (Integer) skalaList.get(position);//Mengambil data (Skala Prioritas) sesuai dengan posisi yang telah ditentukan
        holder.Kategori.setText(Kategori);
        holder.Jumlah.setText("Rp."+Jumlah);
        holder.Tanggal.setText(Tanggal);
        holder.Deskripsi.setText(Deskripsi);

        //Mengirim data ke activity edit dan hapus
        holder.Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent intent = new Intent(view.getContext(), Detail.class);
                intent.putExtra("id", Id);
                intent.putExtra("kategori", Kategori);
                intent.putExtra("jumlah", Jumlah);
                intent.putExtra("tanggal", Tanggal);
                intent.putExtra("deskripsi", Deskripsi);
                intent.putExtra("skala", Skala);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        //Menghitung Ukuran/Jumlah Data Yang Akan Ditampilkan Pada RecyclerView
        return idList.size();
    }

}