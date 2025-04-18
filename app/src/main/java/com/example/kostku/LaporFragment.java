package com.example.kostku;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.kostku.model.Kost;
import com.example.kostku.model.Laporan;
import com.example.kostku.model.Transaction;
import com.example.kostku.model.UserSession;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LaporFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LaporFragment extends Fragment {

    private DatabaseReference mDatabase;
    private ArrayList<Laporan> laporans = new ArrayList<>();
    private LaporanAdapter laporanAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LaporFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LaporFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LaporFragment newInstance(String param1, String param2) {
        LaporFragment fragment = new LaporFragment();
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
        return inflater.inflate(R.layout.fragment_lapor, container, false);
    }


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fetchDataFromFirebase();

        RelativeLayout buatLaporan = (RelativeLayout) getView().findViewById(R.id.buat_laporan_layout);
        buatLaporan.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (UserSession.getInstance().getRole() == -1){
                    return;
                }
                else {
                    Intent intent = new Intent(getActivity(), BuatLaporanActivity.class);
                    startActivity(intent);
                }
            }

        });

        RelativeLayout historyLaporan = (RelativeLayout) getView().findViewById(R.id.history_laporan_layout);
        historyLaporan.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), HistoryLaporanActivity.class);
                startActivity(intent);
            }

        });


        RecyclerView recyclerView = view.findViewById(R.id.rvLaporanKost);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

//        laporanList.add(new Laporan("kamar", "AC Rusak", "Diterima", String.valueOf(new Date())));
//        laporanList.add(new Laporan("kamar", "AC Tidak Dingin", "Diproses", String.valueOf(new Date())));
//        laporanList.add(new Laporan("kamar", "Lemari Patah", "Diterima", String.valueOf(new Date())));



        laporanAdapter = new LaporanAdapter(laporans);
        recyclerView.setAdapter(laporanAdapter);


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
                    if (UserSession.getInstance().getRole() == 0 && UserSession.getInstance().getIdKost().equals(laporan.getKost_id()) && laporan.getStatus_laporan().equals("0")) {
                        laporans.add(laporan);
                    }
                    else if (UserSession.getInstance().getRole() == 1 && laporan.getUsername().equals(UserSession.getInstance().getUsername()) && laporan.getStatus_laporan().equals("0")) {
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