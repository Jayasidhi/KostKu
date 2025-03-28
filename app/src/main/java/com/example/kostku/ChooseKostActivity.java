package com.example.kostku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.kostku.model.Kost;

import java.util.ArrayList;
import java.util.List;

public class ChooseKostActivity extends AppCompatActivity implements ChooseKostAdapter.ChooseKostAdapterListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_kost);

        RecyclerView recyclerView = findViewById(R.id.rvKost);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List <Kost> kostList = new ArrayList<>();

        kostList.add(new Kost("1", "Kost 70A", "Jalan kontold fedfmgvm ascasghdfastyhdfystfvksadflsadf sadjhjasgdfvhasdfcyftvasdyfvsyd djvasdyugystadfgvbsdajkvsyhdfvyksdhafyhsdvfyhsadfvygthhhhvvvvvvvvvvvvvvvvvvvvvvvvsdfgsdfgsdfgsdfgsfdgsdfgsdfgvvvvvvvvvvvvvvvvvvvvvvvv", ""));
        kostList.add(new Kost("2", "Kost 70B", "Jalan kontold fedfmgvm", ""));
        kostList.add(new Kost("3", "Kost 70C", "Jalan kontold fedfmgvm", ""));

        ChooseKostAdapter chooseKostAdapter = new ChooseKostAdapter(kostList, this);

        recyclerView.setAdapter(chooseKostAdapter);

    }

    @Override
    public void chooseKostAdapterListener(int position) {
        Toast.makeText(this, "test", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, HomeActivity.class);
        this.startActivity(intent);
    }
}