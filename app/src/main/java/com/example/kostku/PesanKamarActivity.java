package com.example.kostku;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class PesanKamarActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radio1, radio2, radio3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan_kamar);

        TextView header = findViewById(R.id.textHeader);
        header.setText("Pesan Kamar");

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
}