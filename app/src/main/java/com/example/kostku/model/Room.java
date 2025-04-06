package com.example.kostku.model;

import android.annotation.SuppressLint;

import com.google.firebase.database.DataSnapshot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Room {
    private String id, name, kost_id, floor;
    private Boolean isbooked;
    private Date checkout_date;

    public Room(DataSnapshot roomSnapshot) throws ParseException {
        this.id = roomSnapshot.getKey().toString();
        this.name = roomSnapshot.child("name").getValue().toString();
        this.kost_id = roomSnapshot.child("kost_id").getValue().toString();
        this.floor = roomSnapshot.child("floor").getValue().toString();
        this.isbooked = Boolean.valueOf(roomSnapshot.child("isbooked").getValue().toString());

        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        if(roomSnapshot.child("checkout_date").getValue() == null){
            this.checkout_date = new Date();
        } else {
            this.checkout_date = (Date) dateFormat.parse(roomSnapshot.child("checkout_date").getValue().toString());
        }
    }

    public Boolean getIsbooked() {
        return isbooked;
    }

    public void setIsbooked(Boolean isbooked) {
        this.isbooked = isbooked;
    }

    public Date getCheckout_date() {
        return checkout_date;
    }

    public void setCheckout_date(Date checkout_date) {
        this.checkout_date = checkout_date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKost_id() {
        return kost_id;
    }

    public void setKost_id(String kost_id) {
        this.kost_id = kost_id;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }
}
