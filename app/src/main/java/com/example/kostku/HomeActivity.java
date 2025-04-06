package com.example.kostku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import com.example.kostku.databinding.ActivityHomeBinding;
import com.example.kostku.model.Kost;
import com.example.kostku.model.Room;
import com.example.kostku.model.UserSession;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    private UserSession userSession = UserSession.getInstance();
    private ArrayList<Room> rooms = new ArrayList<>();
    private DatabaseReference mDatabase;
    private int persediaanKamar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchDataFromFirebase();

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        countPersediaanKamar();
        Log.d("d", "onCreate: jumlah hitung " + persediaanKamar);
        Bundle bundle = new Bundle();
        bundle.putInt("jumlah", persediaanKamar);

        BerandaFragment beranda = new BerandaFragment();
        beranda.setArguments(bundle);
        replaceFragment(beranda);
        binding.bottomNavigationView.setSelectedItemId(R.id.beranda);

//        if(iscount){
//            Log.d("d", "onCreate: jumlah " + persediaanKamar);
//            Bundle bundle = new Bundle();
//            bundle.putInt("jumlah", persediaanKamar);
//
//            BerandaFragment beranda = new BerandaFragment();
//            beranda.setArguments(bundle);
//            replaceFragment(beranda);
//            binding.bottomNavigationView.setSelectedItemId(R.id.beranda);
//        }else{
////            replaceFragment(new BerandaFragment());
////            binding.bottomNavigationView.setSelectedItemId(R.id.beranda);
//            Bundle bundle = new Bundle();
//            bundle.putInt("jumlah", persediaanKamar);
//
//            BerandaFragment beranda = new BerandaFragment();
//            beranda.setArguments(bundle);
//            replaceFragment(beranda);
//            binding.bottomNavigationView.setSelectedItemId(R.id.beranda);
//        }

        Log.d("fdatabase", "onDataChangeKost: Session username " + userSession.getUsername());
        Log.d("fdatabase", "onDataChangeKost: Session role " + userSession.getRole());
        Log.d("fdatabase", "onDataChangeKost: Session idkost " + userSession.getIdKost());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.beranda:
                    countPersediaanKamar();
                    Log.d("d", "onCreate: jumlah " + persediaanKamar);
                    Bundle bundles = new Bundle();
                    bundles.putInt("jumlah", persediaanKamar);

                    BerandaFragment berandas = new BerandaFragment();
                    berandas.setArguments(bundles);
                    replaceFragment(berandas);
                    break;
                case R.id.kamar:
                    replaceFragment(new KamarFragment());
                    break;
                case R.id.lapor:
                    replaceFragment(new LaporFragment());
                    break;
            }

            return true;
        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    private boolean fetchDataFromFirebase() {
        mDatabase = FirebaseDatabase.getInstance("https://kostku-89690-default-rtdb.firebaseio.com/").getReference().child("room");

        ValueEventListener postListener = new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NotNull DataSnapshot snapshot) {
                for (DataSnapshot roomSnapshot : snapshot.getChildren()) {
                    Room room = new Room(roomSnapshot);
                    rooms.add(room);
                    Log.d("fdatabase", "onDataChange: " + room.getName());
                    Log.d("fdatabase", "onDataChange: " + room.getFloor());
                    Log.d("fdatabase", "onDataChange: " + room.getKost_id());
                }
//                chooseKostAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NotNull DatabaseError error) {
                Log.d("fdatabase", "onDataChange: " + error.getMessage());
            }
        };
        mDatabase.addValueEventListener(postListener);
        return true;
    }

    public void countPersediaanKamar() {
        persediaanKamar = 0;
        for (Room room : rooms) {
            Log.d("d", "countPersediaanKamar: " + room.getKost_id() + " " + userSession.getIdKost());
            if (room.getKost_id().equals(userSession.getIdKost())) {
                persediaanKamar++;
                Log.d("d", "countPersediaanKamar: hasil " + persediaanKamar);
            }
        }

    }

}