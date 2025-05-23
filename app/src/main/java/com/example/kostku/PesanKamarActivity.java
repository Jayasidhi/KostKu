package com.example.kostku;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kostku.model.Room;
import com.example.kostku.model.Transaction;
import com.example.kostku.model.User;
import com.example.kostku.model.UserSession;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PesanKamarActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radio1, radio2, radio3;
    TextView tanggalMasuk, lantai, kamar, totalPriceTxt, totalPriceTxt1, tv_checkout_date, tv_error_nama, tv_error_notelp, tv_error_bulan, tv_error_radio, header;
    EditText countMonth, namaEdt, notelpEdt;
    LinearLayout pesanKamar;
    ScrollView scrollView;
    private String checkout_date, checkin_date, roomOption, roomId, today;
    private String name, phone_number, room_floor, room_number;
    private DatabaseReference mDatabase, updateDatabase;
    boolean isValidNama, isValidNotelp, isValidBulan, isValidRadio = false;
    private long totalPrice, basePrice;
    private DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    private UserSession userSession = UserSession.getInstance();
    private ArrayList<Transaction> transactions = new ArrayList<>();
    private ArrayList<Room> rooms = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchDataTransactionFromFirebase();
        setContentView(R.layout.activity_pesan_kamar);
        findLayout();
        getTodaysDate();

        basePrice = 3000000;

        header.setText("Pesan Kamar");
        checkin_date = getIntent().getStringExtra("tanggal_masuk");
        tanggalMasuk.setText(dateStringDisplayFormat(checkin_date));

        if (UserSession.getInstance().getRole() == 1) {
            getDataUser();
            fetchDataRoomFromFirebase();
        } else {
            room_floor = getIntent().getStringExtra("lantai");
            lantai.setText(room_floor);
            room_number = getIntent().getStringExtra("kamar");
            kamar.setText(room_number);
            roomId = getIntent().getStringExtra("kamar_id");
        }


        editTextCheck();

        pesanKamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isValidNama && isValidNotelp && isValidBulan && isValidRadio) {
                    DatabaseReference newPostRef;

                    String username = notelpEdt.getText().toString();
                    String name[] = namaEdt.getText().toString().split(" ");
                    String password = name[0] + username.substring(notelpEdt.length() - 3);

                    if (UserSession.getInstance().getRole() != 1) {
                        // generate new user
                        User newUser = new User(username, password, namaEdt.getText().toString());
                        mDatabase = FirebaseDatabase.getInstance("https://kostku-89690-default-rtdb.firebaseio.com/").getReference().child("user");
                        newPostRef = mDatabase.push();
                        newPostRef.setValue(newUser);
                    }

                    //update kost available
                    updateDatabase = FirebaseDatabase.getInstance("https://kostku-89690-default-rtdb.firebaseio.com/").getReference().child("room").child(roomId);
                    updateDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Map<String, Object> kostUpdate = new HashMap<>();
                            kostUpdate.put("isbooked", true);
                            kostUpdate.put("checkout_date", checkout_date);
                            updateDatabase.updateChildren(kostUpdate);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    // add new transaction
                    Transaction transaction = new Transaction(namaEdt.getText().toString(), notelpEdt.getText().toString(), room_floor, room_number,
                            roomOption, String.valueOf(basePrice), String.valueOf(totalPrice), userSession.getIdKost(), checkin_date, checkout_date, today);
                    mDatabase = FirebaseDatabase.getInstance("https://kostku-89690-default-rtdb.firebaseio.com/").getReference().child("transaction");
                    newPostRef = mDatabase.push();
                    newPostRef.setValue(transaction);


                    AlertDialog.Builder builder = new AlertDialog.Builder(PesanKamarActivity.this);

                    builder.setTitle("Pesanan Anda Berhasil !");
                    if (UserSession.getInstance().getRole() != 1) {
                        builder.setMessage("username : " + username + "\n" + "password : " + password);
                    }

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(getApplicationContext(), "Thank You !", Toast.LENGTH_SHORT).show();
                            if (UserSession.getInstance().getRole() == 1) {
                                Intent intent = new Intent(PesanKamarActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Intent intent = new Intent(PesanKamarActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }

                        }
                    });

                    builder.show();
                } else {
                    scrollView.fullScroll(View.FOCUS_UP);
                    if (!isValidNama) {
                        tv_error_nama.setText("Nama Tidak Boleh Kosong!");
                        tv_error_nama.setVisibility(View.VISIBLE);
                    }
                    if (!isValidNotelp) {
                        if (notelpEdt.getText().toString().isEmpty()) {
                            tv_error_notelp.setText("Nomor Telepon Tidak Boleh Kosong!");
                            tv_error_notelp.setVisibility(View.VISIBLE);
                        } else {
                            tv_error_notelp.setText("Harap Masukkan Nomor Telepon yang valid!");
                            tv_error_notelp.setVisibility(View.VISIBLE);
                        }
                    }
                    if (!isValidBulan) {
                        tv_error_bulan.setText("Harap Masukkan Jumlah Bulan yang Diinginkan!");
                        tv_error_bulan.setVisibility(View.VISIBLE);
                    }
                    if (!isValidRadio) {
                        tv_error_radio.setText("Harap Memilih Salah Satu Opsi!");
                        tv_error_radio.setVisibility(View.VISIBLE);
                    }
                    Toast.makeText(getApplicationContext(), "Error !", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void findLayout() {
        pesanKamar = findViewById(R.id.pembayaran_layout);
        header = findViewById(R.id.textHeader);
        tanggalMasuk = findViewById(R.id.tanggalMasukInp);
        lantai = findViewById(R.id.lantaiInp);
        kamar = findViewById(R.id.nokamarInp);


        namaEdt = findViewById(R.id.namaEdt);
        notelpEdt = findViewById(R.id.notelpEdt);
        tv_error_nama = findViewById(R.id.namaErrorLabel);
        tv_error_notelp = findViewById(R.id.notelpErrorLabel);
        tv_error_bulan = findViewById(R.id.bulanErrorLabel);
        tv_error_radio = findViewById(R.id.tataLetakErrorLabel);
        scrollView = findViewById(R.id.scrollView);
        tv_checkout_date = findViewById(R.id.tanggalKeluarLabel);

        countMonth = findViewById(R.id.jumlahBulanEdt);
        totalPriceTxt = findViewById(R.id.totalHargaNum);
        totalPriceTxt1 = findViewById(R.id.totalHargaLabel);

        radioGroup = findViewById(R.id.tataLetakRadioGrp);
        radio1 = findViewById(R.id.option1);
        radio2 = findViewById(R.id.option2);
        radio3 = findViewById(R.id.option3);
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
                totalPrice = countTotalPrice(Integer.parseInt(countMonth.getText().toString()), basePrice);
                totalPriceTxt.setText("Rp " + decimalFormat.format(totalPrice));
                totalPriceTxt1.setText(decimalFormat.format(totalPrice));
                tv_checkout_date.setText(countCheckoutDate(Integer.parseInt(countMonth.getText().toString()), checkin_date));
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (countMonth.getText().toString().isEmpty()) {
                    tv_error_bulan.setText("Harap Masukkan Jumlah Bulan yang Diinginkan!");
                    tv_error_bulan.setVisibility(View.VISIBLE);
                    isValidBulan = false;
                } else {
                    isValidBulan = true;
                    tv_error_bulan.setVisibility(View.GONE);
                }

            }
        });

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.option1) {
//                Toast.makeText(this, "Option 1 Selected", Toast.LENGTH_SHORT).show();
                isValidRadio = true;
                roomOption = "1";
            } else if (checkedId == R.id.option2) {
//                Toast.makeText(this, "Option 2 Selected", Toast.LENGTH_SHORT).show();
                isValidRadio = true;
                roomOption = "2";
            } else if (checkedId == R.id.option3) {
//                Toast.makeText(this, "Option 3 Selected", Toast.LENGTH_SHORT).show();
                isValidRadio = true;
                roomOption = "3";
            } else {
                tv_error_radio.setText("Silahkan Pilih Opsi Tata Letak!");
                tv_error_radio.setVisibility(View.VISIBLE);
                isValidRadio = false;
            }
        });
    }


    public long countTotalPrice(int countMonth, long basePrice) {
        return countMonth * basePrice;
    }

    private void getTodaysDate() {
        Date nowDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        today = makeDateString(day, month, year);
    }

    private static String makeDateString(int day, int month, int year) {
        String date = null;
        date = day < 10 ? "0" + day : day + "";
        date += "-";
        date += month < 10 ? "0" + month : month;
        date += "-";
        date += year;
        return date;
    }

    public String countCheckoutDate(int countMonth, String choosenDate) {
        //get String Month
        int length = choosenDate.length();
        String day = choosenDate.substring(0, 2);
        int month = Integer.parseInt(choosenDate.substring(3, 5));
        int year = Integer.parseInt(choosenDate.substring(length - 4));

        if ((month + countMonth) > 12) {
            year = year + ((month + countMonth) / 12);
            month = month + (countMonth % 12);
        } else {
            month = month + countMonth;
        }


        String monthS = null;
        if (month < 10) {
            monthS = "0" + String.valueOf(month);
        }

        checkout_date = day + "-" + monthS + "-" + year;

        return day + " " + getMonthFormatString(month) + " " + year;
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

    //method ubah 01-01-2020 ke 01 JAN 2020
    private String dateStringDisplayFormat(String date) {
        int month = Integer.parseInt(date.substring(3, 5));
        return date.substring(0, 2) + " " + getMonthFormatString(month) + " " + date.substring(6, 10);
    }

    private boolean fetchDataTransactionFromFirebase() {
        mDatabase = FirebaseDatabase.getInstance("https://kostku-89690-default-rtdb.firebaseio.com/").getReference().child("transaction");

        ValueEventListener postListener = new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot transactionSnapshot : snapshot.getChildren()) {
                    Transaction transaction = null;
                    transaction = new Transaction(transactionSnapshot);
                    transactions.add(transaction);
                    Log.d("Tdatabase", "onDataChange: Transaction ID: " + transaction.getId());
                    Log.d("Tdatabase", "onDataChange: " + transaction.getName());
                    Log.d("Tdatabase", "onDataChange: " + transaction.getPhone_number());
                    Log.d("Tdatabase", "onDataChange: " + transaction.getCheckin_date());
                    Log.d("Tdatabase", "onDataChange: " + transaction.getCheckout_date());
                    Log.d("Tdatabase", "onDataChange: " + transaction.getRoom_floor());
                    Log.d("Tdatabase", "onDataChange: " + transaction.getRoom_number());
                    Log.d("Tdatabase", "onDataChange: " + transaction.getRoom_option());
                    Log.d("Tdatabase", "onDataChange: " + transaction.getBase_price());
                    Log.d("Tdatabase", "onDataChange: " + transaction.getTotal_price());
                    Log.d("Tdatabase", "onDataChange: " + transaction.getKost_id());
                }
                getDataUser();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("fdatabase", "onDataChange: " + error.getMessage());
            }
        };
        mDatabase.addValueEventListener(postListener);
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
//                    kost_id = transaction.getKost_id();
                    Log.d("trans", "getDataUser: " + transaction.getName());
                    namaEdt.setText(name);
                    namaEdt.setFocusable(false);
                    notelpEdt.setText(phone_number);
                    notelpEdt.setFocusable(false);
                    lantai.setText(room_floor);
                    lantai.setEnabled(false);
                    kamar.setText(room_number);
                    kamar.setEnabled(false);

                    roomOption = transaction.getRoom_option();
                    if (Objects.equals(roomOption, "1")) {
                        radioGroup.check(R.id.option1);
//                        radioGroup.setClickable(false);
//                        radioGroup.isEnabled();
                    } else if (Objects.equals(roomOption, "2")) {
                        radioGroup.check(R.id.option2);
//                        radioGroup.setClickable(false);
//                        radioGroup.setEnabled(false);
                    } else if (Objects.equals(roomOption, "3")) {
                        radioGroup.check(R.id.option3);
//                        radioGroup.setClickable(false);
//                        radioGroup.setEnabled(false);
                    }
                    for (int i = 0; i < radioGroup.getChildCount(); i++) {
                        View child = radioGroup.getChildAt(i);
                        child.setEnabled(false);
                    }

                    header.setText("Perpanjang Kamar");

                    break;
                }
            }
        }
    }

    private boolean fetchDataRoomFromFirebase() {
        mDatabase = FirebaseDatabase.getInstance("https://kostku-89690-default-rtdb.firebaseio.com/").getReference().child("room");

        ValueEventListener postListener = new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NotNull DataSnapshot snapshot) {
                for (DataSnapshot roomSnapshot : snapshot.getChildren()) {
                    Room room = null;
                    room = new Room(roomSnapshot);
                    rooms.add(room);
                    if (room.getKost_id().equals(UserSession.getInstance().getIdKost()) && room.getName().equals(room_number)) {
                        roomId = room.getId();
                    }
                    Log.d("fdatabase", "onDataChange: " + room.getName());
                    Log.d("fdatabase", "onDataChange: " + room.getFloor());
                    Log.d("fdatabase", "onDataChange: " + room.getKost_id());
                }
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