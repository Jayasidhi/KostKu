package com.example.kostku;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kostku.model.Kost;
import com.example.kostku.model.User;
import com.example.kostku.model.UserSession;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ChooseKostActivity extends AppCompatActivity implements ChooseKostAdapter.ChooseKostAdapterListener {

    int[] kostImage = {R.drawable.kost_1, R.drawable.kost_2, R.drawable.kost_3};
    private UserSession userSession = UserSession.getInstance();

    private ArrayList<Kost> kosts = new ArrayList<>();
    private DatabaseReference mDatabase;
    private ChooseKostAdapter chooseKostAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchDataFromFirebase();
        setContentView(R.layout.activity_choose_kost);

        Log.d("fdatabase", "onDataChangeKost: " + userSession.getUsername());
        Log.d("fdatabase", "onDataChangeKost: " + userSession.getRole());
        Log.d("fdatabase", "onDataChangeKost: " + userSession.getIdKost());

        RecyclerView recyclerView = findViewById(R.id.rvKost);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        chooseKostAdapter = new ChooseKostAdapter(kosts, kostImage, this);

        recyclerView.setAdapter(chooseKostAdapter);

    }

    @Override
    public void chooseKostAdapterListener(int position) {
        userSession.setIdKost(kosts.get(position).getId());
//        Toast.makeText(this, kosts.get(position).getId(), Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, HomeActivity.class);
        this.startActivity(intent);
    }

    private void fetchDataFromFirebase() {
        mDatabase = FirebaseDatabase.getInstance("https://kostku-89690-default-rtdb.firebaseio.com/").getReference().child("kost");

        ValueEventListener postListener = new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NotNull DataSnapshot snapshot) {
                for (DataSnapshot kostSnapshot : snapshot.getChildren()) {
                    Kost kost = new Kost(kostSnapshot);
                    kosts.add(kost);
                    Log.d("fdatabase", "onDataChange: " + kost.getName());
                    Log.d("fdatabase", "onDataChange: " + kost.getAddress());
                }
                chooseKostAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NotNull DatabaseError error) {
                Log.d("fdatabase", "onDataChange: " + error.getMessage());
            }
        };
        mDatabase.addValueEventListener(postListener);
    }

}