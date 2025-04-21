package com.example.kostku;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kostku.model.Laporan;
import com.example.kostku.model.Transaction;
import com.example.kostku.model.UserSession;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BuatLaporanActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private ArrayList<Transaction> transactions = new ArrayList<>();
    private Button buatLaporanButton;
    private String name, phone_number, room_floor, room_number, kategori, kost_id;

    TextView header, tv_name, tv_phone_number, tv_room_floor, tv_room_number, tv_keluhan_error;
    EditText detail_keluhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_laporan);

        findLayout();
        fetchDataFromFirebase();

        header.setText("Buat Laporan");

        Spinner spinnerKategori = findViewById(R.id.spinnerCategory);
        ArrayAdapter<CharSequence> adapterKategori = ArrayAdapter.createFromResource(this, R.array.spinnerKategoriLapor, android.R.layout.simple_spinner_dropdown_item);
        adapterKategori.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKategori.setAdapter(adapterKategori);

        spinnerKategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                kategori = adapterView.getItemAtPosition(i).toString();
                Log.d("kategori", "onItemSelected: " + kategori);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        buatLaporanButton = findViewById(R.id.lapor_btn);
        buatLaporanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view1) {

                //validasi
                if (detailKeluhanValidasi(detail_keluhan, tv_keluhan_error)) {
                    //masukin data
                    Laporan laporan = new Laporan(kategori, detail_keluhan.getText().toString(), kost_id, room_number, UserSession.getInstance().getUsername());
                    mDatabase = FirebaseDatabase.getInstance("https://kostku-89690-default-rtdb.firebaseio.com/").getReference().child("laporan");
                    DatabaseReference newPostRef = mDatabase.push();
                    newPostRef.setValue(laporan);

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
                } else {
                    tv_keluhan_error.setVisibility(View.VISIBLE);
//                    tv_keluhan_error.setText("Detail Keluhan Tidak Boleh Kosong!");
                }

            }
        });

    }

    private boolean detailKeluhanValidasi(EditText keluhan, TextView error) {
        if (detail_keluhan.getText().toString().isEmpty()) {
            error.setVisibility(View.VISIBLE);
            error.setText("Detail Keluhan Tidak Boleh Kosong!");
            return false;
        } else if (detail_keluhan.getText().toString().length() > 50) {
            error.setVisibility(View.VISIBLE);
            error.setText("Mohon Mengisi dengan 1-50 Huruf");
            return false;
        }
        error.setVisibility(View.GONE);
        return true;
    }


    private void getDataUser() {
        for (Transaction transaction : transactions) {
            if (transaction.getPhone_number().equals(UserSession.getInstance().getUsername())) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                Date checkoutDate = null;
                try {
                    checkoutDate = sdf.parse(transaction.getCheckout_date());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (new Date().before(checkoutDate)) {
                    name = transaction.getName();
                    phone_number = transaction.getPhone_number();
                    room_floor = transaction.getRoom_floor();
                    room_number = transaction.getRoom_number();
                    kost_id = transaction.getKost_id();
                    tv_name.setText(name);
                    tv_phone_number.setText(phone_number);
                    tv_room_floor.setText(room_floor);
                    tv_room_number.setText(room_number);
                    break;
                }
            }
        }
    }

    public void findLayout() {
        header = findViewById(R.id.textHeader);
        tv_name = findViewById(R.id.namaInp);
        tv_phone_number = findViewById(R.id.notelpInp);
        tv_room_floor = findViewById(R.id.lantaiInp);
        tv_room_number = findViewById(R.id.nokamarInp);
        detail_keluhan = findViewById(R.id.keluhanEdt);
        tv_keluhan_error = findViewById(R.id.keluhanErrorLabel);
    }


    private boolean fetchDataFromFirebase() {
        mDatabase = FirebaseDatabase.getInstance("https://kostku-89690-default-rtdb.firebaseio.com/").getReference().child("transaction");

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot snapshot) {
                for (DataSnapshot transactionSnapshot : snapshot.getChildren()) {
                    Transaction transaction = null;
                    transaction = new Transaction(transactionSnapshot);
                    transactions.add(transaction);
                }
                getDataUser();
            }


            @Override
            public void onCancelled(@NotNull DatabaseError error) {
                Log.d("fdatabase", "onDataChange: " + error.getMessage());
            }
        };
        mDatabase.addValueEventListener(postListener);
        return true;
    }

}