package com.example.kostku;

import android.annotation.SuppressLint;
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
import android.widget.TextView;

import com.example.kostku.model.Room;
import com.example.kostku.model.Transaction;
import com.example.kostku.model.UserSession;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BerandaAdminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BerandaAdminFragment extends Fragment {

    private DatabaseReference mDatabase;
    private ArrayList<Transaction> transactions = new ArrayList<>();
    private ArrayList<Transaction> expiredKost = new ArrayList<>();
    private ArrayList<Transaction> newCustomer = new ArrayList<>();
    private ArrayList<Room> rooms = new ArrayList<>();
    private ManagementKostAdapter managementKostAdapter;
    private ExpiredKostAdapter expiredKostAdapter;

    TextView jumlahKamar, tv_pengahasilan_bulan, tv_penghasilan_total;
    private int persediaan = 0;
    private long penghasilanTotal = 0;
    private long penghasilanBulan = 0;
    private DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    private int thisMonth, thisYear;
    private String currentKost;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BerandaAdminFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BerandaAdminFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BerandaAdminFragment newInstance(String param1, String param2) {
        BerandaAdminFragment fragment = new BerandaAdminFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Date nowDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);
        thisMonth = cal.get(Calendar.MONTH) + 1;
        thisYear = cal.get(Calendar.YEAR);

        currentKost = UserSession.getInstance().getIdKost();

        fetchDataFromFirebase();
        fetchDataTransactionFromFirebase();

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        jumlahKamar = view.findViewById(R.id.persediaanInp);
        jumlahKamar.setText(String.valueOf(persediaan));
        tv_pengahasilan_bulan = view.findViewById(R.id.penghasilan_bulan_inp);
        tv_pengahasilan_bulan.setText(String.valueOf(penghasilanBulan));
        tv_penghasilan_total = view.findViewById(R.id.penghasilan_total_inp);
        tv_penghasilan_total.setText(String.valueOf(penghasilanTotal));

        RecyclerView recyclerView = view.findViewById(R.id.rv_expired_kost);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        expiredKostAdapter = new ExpiredKostAdapter(expiredKost);
        recyclerView.setAdapter(expiredKostAdapter);

        RecyclerView recyclerView2 = view.findViewById(R.id.rv_newCustomer_kost);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
        managementKostAdapter = new ManagementKostAdapter(newCustomer);
        recyclerView2.setAdapter(managementKostAdapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_beranda_admin, container, false);
    }

    public void expiredKost(){
        for(Transaction transaction : transactions){
            int checkoutMonth = Integer.parseInt(transaction.getCheckout_date().substring(3,5));
            if((transaction.getKost_id().equals(currentKost)) && checkoutMonth == thisMonth){
                expiredKost.add(transaction);
                Log.d("expired", "expiredKost: " + expiredKost);
            }
        }
    }

    public void newCustomer(){
        for(Transaction transaction : transactions){
            int checkinMonth = Integer.parseInt(transaction.getCheckin_date().substring(3,5));
            if((transaction.getKost_id().equals(currentKost)) && checkinMonth == thisMonth){
                newCustomer.add(transaction);
                Log.d("new", "newCustomer: " + newCustomer);
            }
        }
    }


    public void countPenghasilan(){
        penghasilanTotal = 0;
        penghasilanBulan = 0;
//        String currentKost = UserSession.getInstance().getIdKost();
//        Date nowDate = new Date();
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(nowDate);
//        int thisMonth = cal.get(Calendar.MONTH) + 1;
//        int thisYear = cal.get(Calendar.YEAR);

        for(Transaction transaction : transactions) {
            int transactionMonth = Integer.parseInt(transaction.getTransaction_date().substring(3,5));
            int checkinYear = Integer.parseInt(transaction.getCheckin_date().substring(6,10));
            if(transaction.getKost_id().equals(currentKost)){
                penghasilanTotal = penghasilanTotal + Long.parseLong(transaction.getTotal_price());
            }
            if((transaction.getKost_id().equals(currentKost)) && (transactionMonth == thisMonth) && (checkinYear == thisYear)){
                penghasilanBulan = penghasilanBulan + Long.parseLong(transaction.getTotal_price());
                Log.d("penghasilan", "countPenghasilan bulan: " + penghasilanBulan + " " + transactionMonth + thisMonth);
            }
        }
        tv_penghasilan_total.setText(decimalFormat.format(penghasilanTotal));
        tv_pengahasilan_bulan.setText(decimalFormat.format(penghasilanBulan));
    }


    public void countPersediaanKamar() {
        persediaan = 0;
//        String currentKost = UserSession.getInstance().getIdKost();
        for (Room room : rooms) {
//            Log.d("d", "countPersediaanKamar: " + room.getKost_id() + " " + currentKost);
            if (room.getKost_id().equals(currentKost) && !room.getIsbooked()) {
                persediaan++;
//                Log.d("d", "countPersediaanKamar: hasil " + persediaan);
            }
        }

        jumlahKamar.setText(String.valueOf(persediaan));
    }

    private boolean fetchDataFromFirebase() {
        mDatabase = FirebaseDatabase.getInstance("https://kostku-89690-default-rtdb.firebaseio.com/").getReference().child("room");

        ValueEventListener postListener = new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NotNull DataSnapshot snapshot) {
                for (DataSnapshot roomSnapshot : snapshot.getChildren()) {
                    Room room = null;
                    room = new Room(roomSnapshot);
                    rooms.add(room);
                    Log.d("fdatabase", "onDataChange: " + room.getName());
                    Log.d("fdatabase", "onDataChange: " + room.getFloor());
                    Log.d("fdatabase", "onDataChange: " + room.getKost_id());
                }
                countPersediaanKamar();
            }


            @Override
            public void onCancelled(@NotNull DatabaseError error) {
                Log.d("fdatabase", "onDataChange: " + error.getMessage());
            }
        };
        mDatabase.addValueEventListener(postListener);
        return true;
    }

    private boolean fetchDataTransactionFromFirebase() {
        mDatabase = FirebaseDatabase.getInstance("https://kostku-89690-default-rtdb.firebaseio.com/").getReference().child("transaction");

        ValueEventListener postListener = new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot transactionSnapshot : snapshot.getChildren()){
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
                countPenghasilan();
                expiredKost();
                newCustomer();
                expiredKostAdapter.notifyDataSetChanged();
                managementKostAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("fdatabase", "onDataChange: " + error.getMessage());
            }
        };
        mDatabase.addValueEventListener(postListener);
        return true;
    }
}