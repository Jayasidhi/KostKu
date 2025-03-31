package com.example.kostku;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class BuatLaporanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_laporan);


        Spinner spinnerKategori = findViewById(R.id.spinnerRoom);
        ArrayAdapter<CharSequence> adapterKategori = ArrayAdapter.createFromResource(this, R.array.spinnerKategoriLapor, android.R.layout.simple_spinner_dropdown_item);
        adapterKategori.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKategori.setAdapter(adapterKategori);

        TextView header = findViewById(R.id.textHeader);
        header.setText("Buat Laporan");

    }
}