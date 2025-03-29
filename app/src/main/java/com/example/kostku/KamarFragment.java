package com.example.kostku;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

//import com.panoramagl.PLImage;
//import com.panoramagl.PLManager;
//import com.panoramagl.PLSphericalPanorama;
//import com.panoramagl.utils.PLUtils;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link KamarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KamarFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public KamarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment KamarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static KamarFragment newInstance(String param1, String param2) {
        KamarFragment fragment = new KamarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kamar, container, false);
    }

    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    private Button pesanKamarButton;

//    private FrameLayout flview;
//    private PLManager plManager;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//         Initialize DatePicker from layout
//        DatePicker datePicker = getView().findViewById(R.id.datePicker);

//         Get today's date using Calendar instance
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.DAY_OF_MONTH, +1);
//        long limit = calendar.getTimeInMillis();
//
//        datePicker.setMinDate(limit);

//         Initialize DatePicker with the current date
//        datePicker.init(
//                calendar.get(Calendar.YEAR),
//                calendar.get(Calendar.MONTH),
//                calendar.get(Calendar.DAY_OF_MONTH),
//                new DatePicker.OnDateChangedListener() {
//                    @Override
//                    public void onDateChanged(DatePicker view, int year, int month, int day) {
//                        // Display selected date in Toast message
//                        String msg = "You Selected: " + day + "/" + (month + 1) + "/" + year;
//                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
//                    }
//                }
//        );

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
        spinnerLantai.setAdapter(adapterLantai);

        Spinner spinnerKamar = (Spinner) getView().findViewById(R.id.spinnerRoom);
        ArrayAdapter<CharSequence> adapterKamar = ArrayAdapter.createFromResource(getActivity(), R.array.spinnerRoom, android.R.layout.simple_spinner_dropdown_item);
        adapterLantai.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKamar.setAdapter(adapterKamar);

//        flview = getView().findViewById(R.id.panoramaView);
//        initialisePlManager();
//
//        PLSphericalPanorama panorama = new PLSphericalPanorama();
//        panorama.getCamera().lookAt(30.0f, 90.0f);
//        panorama.setImage(new PLImage(PLUtils.getBitmap(getActivity(), R.drawable.image_pano_test)));
//        plManager.setPanorama(panorama);

        WebView webView = getView().findViewById(R.id.panoramaWeb);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setDomStorageEnabled(true);

        webView.setWebViewClient(new WebViewClient());
//
//        // Load local HTML file with Panorama JS
        webView.loadUrl("File:///android_asset/panorama.html");
//        webView.evaluateJavascript("loadPanorama('file:///android_asset/panorama_image.jpg');", null);




        pesanKamarButton = getView().findViewById(R.id.pesan_kamar_btn);
        pesanKamarButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view1){
                Intent intent = new Intent(getActivity(), PesanKamarActivity.class);
                startActivity(intent);
            }
        });


    }

//    private void initialisePlManager() {
//        plManager = new PLManager(getActivity());
//        plManager.setContentView(flview);
//        plManager.onCreate();
//        plManager.setScrollingEnabled(true);
//        plManager.setAccelerometerEnabled(false);
//        plManager.setZoomEnabled(true);
//        plManager.setInertiaEnabled(true);
//        plManager.setAcceleratedTouchScrollingEnabled(false);
//    }
//    public boolean onTouchEvent(MotionEvent event) {
//        return plManager.onTouchEvent(event);
//    }
//
//    public void onResume() {
//        super.onResume();
//        if (plManager != null) {
//            plManager.onResume();
//        }
//    }
//
//    public void onDestroy() {
//        super.onDestroy();
//        plManager.onDestroy();
//    }

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