package com.example.kostku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.kostku.model.Laporan;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryLaporanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_laporan);


        RecyclerView recyclerView = findViewById(R.id.rvHistoryLaporanKost);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Laporan> laporanList = new ArrayList<>();
        laporanList.add(new Laporan("1", "kamar", "AC Rusak", "Diterima", String.valueOf(new Date())));
        laporanList.add(new Laporan("2", "kamar", "AC Tidak Dingin", "Diproses", String.valueOf(new Date())));
        laporanList.add(new Laporan("3", "kamar", "Lemari Patah", "Diterima", String.valueOf(new Date())));
        laporanList.add(new Laporan("4", "kamar", "AC Rusak", "Diterima", String.valueOf(new Date())));
        laporanList.add(new Laporan("5", "kamar", "AC Tidak Dingin", "Selesai", String.valueOf(new Date())));
        laporanList.add(new Laporan("6", "kamar", "Lemari Patah", "Selesai", String.valueOf(new Date())));
        laporanList.add(new Laporan("7", "kamar", "AC Rusak", "Selesai", String.valueOf(new Date())));
        laporanList.add(new Laporan("8", "kamar", "AC Tidak Dingin", "Selesai", String.valueOf(new Date())));
        laporanList.add(new Laporan("9", "kamar", "Lemari Patah", "Selesai", String.valueOf(new Date())));

        LaporanAdapter laporanAdapter = new LaporanAdapter(laporanList);
        recyclerView.setAdapter(laporanAdapter);

        TextView header = findViewById(R.id.textHeader);
        header.setText("History Laporan");
    }
}