package com.example.kostku;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kostku.model.Laporan;
import com.example.kostku.model.UserSession;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class HistoryLaporanActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private ArrayList<Laporan> laporans = new ArrayList<>();
    private LaporanAdapter laporanAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_laporan);
        fetchDataFromFirebase();

        RecyclerView recyclerView = findViewById(R.id.rvHistoryLaporanKost);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        laporanList.add(new Laporan("kamar", "AC Rusak", "Diterima", String.valueOf(new Date())));
//        laporanList.add(new Laporan("kamar", "AC Tidak Dingin", "Diproses", String.valueOf(new Date())));
//        laporanList.add(new Laporan("kamar", "Lemari Patah", "Diterima", String.valueOf(new Date())));
//        laporanList.add(new Laporan("kamar", "AC Rusak", "Diterima", String.valueOf(new Date())));
//        laporanList.add(new Laporan("kamar", "AC Tidak Dingin", "Selesai", String.valueOf(new Date())));
//        laporanList.add(new Laporan("kamar", "Lemari Patah", "Selesai", String.valueOf(new Date())));
//        laporanList.add(new Laporan("kamar", "AC Rusak", "Selesai", String.valueOf(new Date())));
//        laporanList.add(new Laporan("kamar", "AC Tidak Dingin", "Selesai", String.valueOf(new Date())));
//        laporanList.add(new Laporan("kamar", "Lemari Patah", "Selesai", String.valueOf(new Date())));

        laporanAdapter = new LaporanAdapter(laporans);
        recyclerView.setAdapter(laporanAdapter);

        TextView header = findViewById(R.id.textHeader);
        header.setText("Riwayat Laporan");
    }

    private void fetchDataFromFirebase() {
        mDatabase = FirebaseDatabase.getInstance("https://kostku-89690-default-rtdb.firebaseio.com/").getReference().child("laporan");

        ValueEventListener postListener = new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NotNull DataSnapshot snapshot) {
                laporans.clear();
                for (DataSnapshot laporanSnapshot : snapshot.getChildren()) {
                    Laporan laporan = new Laporan(laporanSnapshot);
                    if (UserSession.getInstance().getRole() == 0 && UserSession.getInstance().getIdKost().equals(laporan.getKost_id())) {
                        laporans.add(laporan);
                    } else if (UserSession.getInstance().getRole() == 1 && laporan.getUsername().equals(UserSession.getInstance().getUsername())) {
                        laporans.add(laporan);
                    }
                    Log.d("laporan", "onDataChange: user: " + laporan.getUsername());
                    Log.d("laporan", "onDataChange: user session: " + UserSession.getInstance().getUsername() + laporan.getUsername().equals(UserSession.getInstance().getUsername()));
                    Log.d("laporan", "onDataChange: user: " + laporan.getIsi_keluhan());
                }
                laporanAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NotNull DatabaseError error) {
                Log.d("fdatabase", "onDataChange: " + error.getMessage());
            }
        };
        mDatabase.addValueEventListener(postListener);
    }
}