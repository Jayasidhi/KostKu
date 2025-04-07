package com.example.kostku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.Objects;

public class PesanKamarActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radio1, radio2, radio3;
    TextView tanggalMasuk, lantai, kamar;
    TextView totalPriceTxt, totalPriceTxt1, tv_checkout_date;
    String checkout_date;
    TextView tv_error_nama, tv_error_notelp, tv_error_bulan, tv_error_radio;
    EditText countMonth, namaEdt, notelpEdt;
    boolean isValidNama, isValidNotelp, isValidBulan, isValidRadio = false;
    long basePrice = 3000000;
    private DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    private ScrollView scrollView;


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


        namaEdt = findViewById(R.id.namaEdt);
        notelpEdt = findViewById(R.id.notelpEdt);
        tv_error_nama = findViewById(R.id.namaErrorLabel);
        tv_error_notelp = findViewById(R.id.notelpErrorLabel);
        tv_error_bulan = findViewById(R.id.bulanErrorLabel);
        tv_error_radio = findViewById(R.id.tataLetakErrorLabel);
        scrollView = findViewById(R.id.scrollView);
        tv_checkout_date = findViewById(R.id.tanggalKeluarLabel);

        editTextCheck();

        countMonth = findViewById(R.id.jumlahBulanEdt);
        totalPriceTxt = findViewById(R.id.totalHargaNum);
        totalPriceTxt1 = findViewById(R.id.totalHargaLabel);

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
                isValidBulan = true;
                tv_error_bulan.setVisibility(View.GONE);
                totalPriceTxt.setText("Rp " + decimalFormat.format(countTotalPrice(Integer.parseInt(countMonth.getText().toString()), basePrice)));
                totalPriceTxt1.setText(decimalFormat.format(countTotalPrice(Integer.parseInt(countMonth.getText().toString()), basePrice)));
                tv_checkout_date.setText(countCheckoutDate(Integer.parseInt(countMonth.getText().toString()), choosenDate));
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(countMonth.getText().toString().isEmpty()){
                    tv_error_bulan.setText("Harap Masukkan Jumlah Bulan yang Diinginkan!");
                    tv_error_bulan.setVisibility(View.VISIBLE);
                    isValidBulan = false;
                } else {
                    isValidBulan = true;
                    tv_error_bulan.setVisibility(View.GONE);
                }

            }
        });

        radioGroup = findViewById(R.id.tataLetakRadioGrp);
        radio1 = findViewById(R.id.option1);
        radio2 = findViewById(R.id.option2);
        radio3 = findViewById(R.id.option3);

//        if(radioGroup.getCheckedRadioButtonId() == -1){
//            tv_error_radio.setText("Silahkan Pilih Opsi Tata Ltak!");
//            tv_error_radio.setVisibility(View.VISIBLE);
//            isValidRadio = false;
//        } else {
//            tv_error_radio.setVisibility(View.GONE);
//            isValidRadio = true;
//        }


        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.option1) {
                Toast.makeText(this, "Option 1 Selected", Toast.LENGTH_SHORT).show();
                isValidRadio = true;
            } else if (checkedId == R.id.option2) {
                Toast.makeText(this, "Option 2 Selected", Toast.LENGTH_SHORT).show();
                isValidRadio = true;
            } else if (checkedId == R.id.option3) {
                Toast.makeText(this, "Option 3 Selected", Toast.LENGTH_SHORT).show();
                isValidRadio = true;
            } else {
                tv_error_radio.setText("Silahkan Pilih Opsi Tata Ltak!");
                tv_error_radio.setVisibility(View.VISIBLE);
                isValidRadio = false;
            }
        });

        LinearLayout pesanKamar = findViewById(R.id.pembayaran_layout);
        pesanKamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isValidNama && isValidNotelp && isValidBulan && isValidRadio){
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
                } else {
                    scrollView.fullScroll(View.FOCUS_UP);
                    if(!isValidNama){
                        tv_error_nama.setText("Nama Tidak Boleh Kosong!");
                        tv_error_nama.setVisibility(View.VISIBLE);
                    }
                    if(!isValidNotelp){
                        if(notelpEdt.getText().toString().isEmpty()){
                            tv_error_notelp.setText("Nomor Telepon Tidak Boleh Kosong!");
                            tv_error_notelp.setVisibility(View.VISIBLE);
                        } else {
                            tv_error_notelp.setText("Harap Masukkan Nomor Telepon yang valid!");
                            tv_error_notelp.setVisibility(View.VISIBLE);
                        }
                    }
                    if(!isValidBulan){
                        tv_error_bulan.setText("Harap Masukkan Jumlah Bulan yang Diinginkan!");
                        tv_error_bulan.setVisibility(View.VISIBLE);
                    }
                    if(!isValidRadio){
                        tv_error_radio.setText("Harap Memilih Salah Satu Opsi!");
                        tv_error_radio.setVisibility(View.VISIBLE);
                    }
                    Toast.makeText(getApplicationContext(), "Error !", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void editTextCheck() {
        namaEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (nameValidation(namaEdt.getText().toString(), tv_error_nama)) {
                    tv_error_nama.setVisibility(View.VISIBLE);
                    isValidNama = false;
                } else {
                    tv_error_nama.setVisibility(View.GONE);
                    isValidNama = true;
                }
            }
        });

        notelpEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (phoneValidation(notelpEdt.getText().toString(), tv_error_notelp)) {
                    tv_error_notelp.setVisibility(View.VISIBLE);
                    isValidNotelp = false;
                } else {
                    tv_error_notelp.setVisibility(View.GONE);
                    isValidNotelp = true;
                }
            }
        });

    }


    public long countTotalPrice(int countMonth, long basePrice) {
        return countMonth * basePrice;
    }

    public String countCheckoutDate(int countMonth, String choosenDate) {
        //get String Month
        int length = choosenDate.length();
        String day;
        int month = 0;
        int year = Integer.parseInt(choosenDate.substring(length-4));

        if(length == 10){
            month = getMonthFormatInteger(choosenDate.substring(2,5));
            day = choosenDate.substring(0,1);
        } else {
            month = getMonthFormatInteger(choosenDate.substring(3,6));
            day = choosenDate.substring(0,2);
        }

        if((month + countMonth) >= 12){
            year = year + ((month + countMonth)/12);
        }

        checkout_date = day + "-" + (month+countMonth)%12 + "-" + year;
        Log.d("testing", "countCheckoutDate: " + checkout_date);

        return day + " " + getMonthFormatString((month+countMonth)%12) + " " + year;
    }

    private boolean nameValidation(String data, TextView tv_error) {
        if (data == null || data.length() < 1 || data.isEmpty()) {
            tv_error.setText("Nama Tidak Boleh Kosong!");
            return true;
        }
        return false;
    }

    private boolean phoneValidation(String data, TextView tv_error) {
        if (data.isEmpty()) {
            tv_error.setText("Nomor Telepon Tidak Boleh Kosong!");
            return true;
        } else if (!data.startsWith("08")) {
            tv_error.setText("Silahkan mulai dengan \"08\"!");
            return true;
        } else if (data.length() < 12) {
            tv_error.setText("Harap Masukkan Nomor Telepon yang valid!");
            return true;
        }
        return false;
    }

    private String getMonthFormatString(int month) {
        if (month == 1)
            return "JAN";
        if (month == 2)
            return "FEB";
        if (month == 3)
            return "MAR";
        if (month == 4)
            return "APR";
        if (month == 5)
            return "MAY";
        if (month == 6)
            return "JUN";
        if (month == 7)
            return "JUL";
        if (month == 8)
            return "AUG";
        if (month == 9)
            return "SEP";
        if (month == 10)
            return "OCT";
        if (month == 11)
            return "NOV";
        if (month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    private int getMonthFormatInteger(String month) {
        if (Objects.equals(month, "JAN"))
            return 1;
        if (Objects.equals(month, "FEB"))
            return 2;
        if (Objects.equals(month, "MAR"))
            return 3;
        if (Objects.equals(month, "APR"))
            return 4;
        if (Objects.equals(month, "MAY"))
            return 5;
        if (Objects.equals(month, "JUN"))
            return 6;
        if (Objects.equals(month, "JUL"))
            return 7;
        if (Objects.equals(month, "AUG"))
            return 8;
        if (Objects.equals(month, "SEP"))
            return 9;
        if (Objects.equals(month, "OCT"))
            return 10;
        if (Objects.equals(month, "NOV"))
            return 11;
        if (Objects.equals(month, "DEC"))
            return 12;

        //default should never happen
        return 1;
    }
}