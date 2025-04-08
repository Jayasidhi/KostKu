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

import java.text.ParseException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BerandaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BerandaFragment extends Fragment {
    private DatabaseReference mDatabase;
    private ArrayList<Room> rooms = new ArrayList<>();
    private ArrayList<Transaction> transactions = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView jumlahKamar;
    private int persediaan = 0;

    public BerandaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BerandaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BerandaFragment newInstance(String param1, String param2) {
        BerandaFragment fragment = new BerandaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchDataFromFirebase();
        fetchDataTransactionFromFirebase();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

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
                for (DataSnapshot transactionSnapshot : snapshot.getChildren()){
                    Transaction transaction = null;
                    transaction = new Transaction(transactionSnapshot);
                    transactions.add(transaction);
                    Log.d("Tdatabase", "onDataChange: Transaction ID: " + transaction.getId());
                    Log.d("Tdatabase", "onDataChange: " + transaction.getName());
                    Log.d("Tdatabase", "onDataChange: " + transaction.getPhoneNumber());
                    Log.d("Tdatabase", "onDataChange: " + transaction.getCheckin_date());
                    Log.d("Tdatabase", "onDataChange: " + transaction.getCheckout_date());
                    Log.d("Tdatabase", "onDataChange: " + transaction.getRoomFloor());
                    Log.d("Tdatabase", "onDataChange: " + transaction.getRoomNumber());
                    Log.d("Tdatabase", "onDataChange: " + transaction.getRoomOption());
                    Log.d("Tdatabase", "onDataChange: " + transaction.getBasePrice());
                    Log.d("Tdatabase", "onDataChange: " + transaction.getTotalPrice());
                    Log.d("Tdatabase", "onDataChange: " + transaction.getKostId());
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