package com.example.kostku;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class PesanKamarActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radio1, radio2, radio3;
    TextView tanggalMasuk;
    TextView lantai;
    TextView kamar;
    TextView totalPriceTxt;
    EditText countMonth;
    long basePrice = 3000000;
    private DecimalFormat decimalFormat = new DecimalFormat("###,###,###");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan_kamar);

        TextView header = findViewById(R.id.textHeader);
        header.setText("Pesan Kamar");

        String choosenDate = getIntent().getStringExtra("tanggal_masuk");
        String choosenFloor = getIntent().getStringExtra("lantai");
        String choosenRoom = getIntent().getStringExtra("kamar");

        tanggalMasuk = findViewById(R.id.tanggalMasukInp);
        tanggalMasuk.setText(choosenDate);

        lantai = findViewById(R.id.lantaiInp);
        lantai.setText(choosenFloor);

        kamar = findViewById(R.id.nokamarInp);
        kamar.setText(choosenRoom);

        countMonth = findViewById(R.id.jumlahBulanEdt);
        totalPriceTxt = findViewById(R.id.totalHargaNum);

        countMonth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (countMonth.getText().toString().isEmpty() || (countMonth.getText().toString().startsWith("0") && countMonth.getText().toString().length() >= 1)) {
                    countMonth.setText("1");
                }
                totalPriceTxt.setText("Rp " + decimalFormat.format(countTotalPrice(Integer.parseInt(countMonth.getText().toString()), basePrice)));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        radioGroup = findViewById(R.id.tataLetakRadioGrp);
        radio1 = findViewById(R.id.option1);
        radio2 = findViewById(R.id.option2);
        radio3 = findViewById(R.id.option3);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.option1) {
                Toast.makeText(this, "Option 1 Selected", Toast.LENGTH_SHORT).show();
            } else if (checkedId == R.id.option2) {
                Toast.makeText(this, "Option 2 Selected", Toast.LENGTH_SHORT).show();
            } else if (checkedId == R.id.option3) {
                Toast.makeText(this, "Option 3 Selected", Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayout pesanKamar = findViewById(R.id.pembayaran_layout);
        pesanKamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(PesanKamarActivity.this, "Button Clicked!", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder builder = new AlertDialog.Builder(PesanKamarActivity.this);

                builder.setTitle("Pesanan Anda Berhasil !");
                builder.setMessage("username : " + "username" + "\n" + "password : " + "password");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "Thank You !", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PesanKamarActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

                builder.show();

            }
        });

    }

    public long countTotalPrice (int countMonth, long basePrice){
        return countMonth * basePrice;
    }


}