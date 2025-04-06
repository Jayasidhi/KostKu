package com.example.kostku;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kostku.model.Room;
import com.example.kostku.model.UserSession;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

public class KamarFragment extends Fragment {

    private DatabaseReference mDatabase;
    private ArrayList<Room> rooms = new ArrayList<>();
    private ArrayList<String> roomFloor = new ArrayList<>();
    private ArrayAdapter<String> adapterKamar;
    String choosenFloor = null;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private Button pesanKamarButton;

    public KamarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchDataFromFirebase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kamar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initDatePicker();
        dateButton = getView().findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
                datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
            }
        });

        Spinner spinnerLantai = (Spinner) getView().findViewById(R.id.spinnerFloor);
        ArrayAdapter<CharSequence> adapterLantai = ArrayAdapter.createFromResource(getActivity(), R.array.spinnerFloor, android.R.layout.simple_spinner_dropdown_item);
        adapterLantai.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLantai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                choosenFloor = spinnerLantai.getSelectedItem().toString();
                getTempRooms();
                Log.d("floor", "onViewCreated: floor selected : " + choosenFloor);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerLantai.setAdapter(adapterLantai);


        Spinner spinnerKamar = getView().findViewById(R.id.spinnerRoom);
        adapterKamar = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, roomFloor);
        adapterKamar.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Log.d("floor", "onViewCreated: room selected test: " + spinnerKamar);
        spinnerKamar.setAdapter(adapterKamar);
        spinnerKamar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String spinnerValue = adapterView.getItemAtPosition(i).toString();

                Toast.makeText(getActivity(), "Selected item" + spinnerValue, Toast.LENGTH_SHORT).show();
                Log.d("floor", "onViewCreated: room selected : " + spinnerKamar.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.d("floor", "onViewCreated: room no selected : ");
            }
        });
        adapterKamar.notifyDataSetChanged();

        WebView webView = getView().findViewById(R.id.panoramaWeb);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setDomStorageEnabled(true);

        webView.setWebViewClient(new WebViewClient());
        // Load local HTML file with Panorama JS
        webView.loadUrl("File:///android_asset/panorama.html");

        pesanKamarButton = getView().findViewById(R.id.pesan_kamar_btn);
        pesanKamarButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view1) {
                Intent intent = new Intent(getActivity(), PesanKamarActivity.class);
                startActivity(intent);
            }
        });

    }

    private boolean fetchDataFromFirebase() {
        mDatabase = FirebaseDatabase.getInstance("https://kostku-89690-default-rtdb.firebaseio.com/").getReference().child("room");

        ValueEventListener postListener = new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NotNull DataSnapshot snapshot) {
                for (DataSnapshot roomSnapshot : snapshot.getChildren()) {
                    Room room = null;
                    try {
                        room = new Room(roomSnapshot);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    rooms.add(room);
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


    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
                Toast.makeText(getActivity(), date, Toast.LENGTH_LONG).show();

                // dibawah ini untuk validasi output kamar apa aja yang avail
                getTempRooms();
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(getActivity(), R.style.SpinnerDatePickerDialogTheme, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
        datePickerDialog.setTitle("Tanggal");
    }

    private void getTempRooms() {
        roomFloor.clear();
        for (Room room : rooms) {
            if (UserSession.getInstance().getIdKost().equals(room.getKost_id()) && room.getFloor().equals(choosenFloor.substring(7)) && !room.getIsbooked()) {
                roomFloor.add(room.getName());
            }
        }

        // Kasih tahu adapter bahwa data telah berubah
        if (adapterKamar != null) {
            adapterKamar.notifyDataSetChanged();
        }
    }

    private String makeDateString(int day, int month, int year) {
        return day + " " + getMonthFormat(month) + " " + year;

    }

    private String getMonthFormat(int month) {
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
}