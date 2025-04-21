package com.example.kostku;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kostku.model.Room;
import com.example.kostku.model.Transaction;
import com.example.kostku.model.UserSession;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class BerandaFragment extends Fragment {
    private DatabaseReference mDatabase;
    private ArrayList<Room> rooms = new ArrayList<>();
    private ArrayList<Transaction> transactions = new ArrayList<>();
    TextView jumlahKamar;
    private int persediaan = 0;

    public BerandaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchDataFromFirebase();
        fetchDataTransactionFromFirebase();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_beranda, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        jumlahKamar = view.findViewById(R.id.persediaanInp);
        jumlahKamar.setText(String.valueOf(persediaan));

    }

    public void countPersediaanKamar() {
        persediaan = 0;
        String currentKost = UserSession.getInstance().getIdKost();
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
                    Log.d("Tdatabase", "onDataChange: " + transaction.getTransaction_date());
                }
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