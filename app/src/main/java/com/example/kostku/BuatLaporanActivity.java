package com.example.kostku;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class BuatLaporanActivity extends AppCompatActivity {

    private Button buatLaporanButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_laporan);


        Spinner spinnerKategori = findViewById(R.id.spinnerCategory);
        ArrayAdapter<CharSequence> adapterKategori = ArrayAdapter.createFromResource(this, R.array.spinnerKategoriLapor, android.R.layout.simple_spinner_dropdown_item);
        adapterKategori.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKategori.setAdapter(adapterKategori);

        TextView header = findViewById(R.id.textHeader);
        header.setText("Buat Laporan");


        buatLaporanButton = findViewById(R.id.lapor_btn);
        buatLaporanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view1){
//                Toast.makeText(BuatLaporanActivity.this, "Button Clicked!", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(BuatLaporanActivity.this);

                builder.setTitle("Laporan Anda Berhasil Dibuat!");
                builder.setMessage("Mohon menunggu untuk diproses");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "Thank You !", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BuatLaporanActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                });

                builder.show();
            }
        });

    }
}