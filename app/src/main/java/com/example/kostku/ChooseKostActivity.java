package com.example.kostku;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kostku.model.Kost;
import com.example.kostku.model.UserSession;

import java.util.ArrayList;
import java.util.List;

public class ChooseKostActivity extends AppCompatActivity implements ChooseKostAdapter.ChooseKostAdapterListener {

    int[] kostImage = {R.drawable.logonew, R.drawable.option1, R.drawable.logonew};
    private UserSession userSession = UserSession.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_kost);
        Log.d("fdatabase", "onDataChangeKost: " + userSession.getUsername());
        Log.d("fdatabase", "onDataChangeKost: " + userSession.getRole());

        RecyclerView recyclerView = findViewById(R.id.rvKost);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Kost> kostList = new ArrayList<>();

        kostList.add(new Kost("1", "Kost 70A", "Jalan kontold fedfmgvm ascasghdfastyhdfystfvksadflsadf sadjhjasgdfvhasdfcyftvasdyfvsyd djvasdyugystadfgvbsdajkvsyhdfvyksdhafyhsdvfyhsadfvygthhhhvvvvvvvvvvvvvvvvvvvvvvvvsdfgsdfgsdfgsdfgsfdgsdfgsdfgvvvvvvvvvvvvvvvvvvvvvvvv", ""));
        kostList.add(new Kost("2", "Kost 70B", "Jalan kontold fedfmgvm", ""));
        kostList.add(new Kost("3", "Kost 70C", "Jalan kontold fedfmgvm", ""));

        ChooseKostAdapter chooseKostAdapter = new ChooseKostAdapter(kostList, kostImage, this);

        recyclerView.setAdapter(chooseKostAdapter);

    }

    @Override
    public void chooseKostAdapterListener(int position) {
        Toast.makeText(this, "test", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, HomeActivity.class);
        this.startActivity(intent);
    }
}