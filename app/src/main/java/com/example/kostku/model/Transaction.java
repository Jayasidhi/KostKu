package com.example.kostku.model;

import android.annotation.SuppressLint;

import com.google.firebase.database.DataSnapshot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {

    String id, name, phoneNumber, roomFloor, roomNumber,  roomOption, basePrice, totalPrice, kostId;
    String checkin_date;
    String checkout_date;

    public Transaction(DataSnapshot transactionSnapshot) {
        this.id = transactionSnapshot.getKey().toString();
        this.name = transactionSnapshot.child("name").getValue().toString();
        this.phoneNumber = transactionSnapshot.child("phone_number").getValue().toString();

        this.checkin_date = transactionSnapshot.child("checkin_date").getValue().toString();
        this.checkout_date = transactionSnapshot.child("checkout_date").getValue().toString();

        this.roomFloor = transactionSnapshot.child("room_floor").getValue().toString();
        this.roomNumber = transactionSnapshot.child("room_number").getValue().toString();
        this.roomOption = transactionSnapshot.child("room_option").getValue().toString();
        this.basePrice = transactionSnapshot.child("base_price").getValue().toString();
        this.totalPrice = transactionSnapshot.child("total_price").getValue().toString();
        this.kostId = transactionSnapshot.child("kost_id").getValue().toString();
    }

    public Transaction(String name, String phoneNumber, String roomFloor, String roomNumber, String roomOption, String basePrice, String totalPrice, String kostId, String checkin_date, String checkout_date) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.roomFloor = roomFloor;
        this.roomNumber = roomNumber;
        this.roomOption = roomOption;
        this.basePrice = basePrice;
        this.totalPrice = totalPrice;
        this.kostId = kostId;
        this.checkin_date = checkin_date; //1-1-2020 --> 01-01-2020
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCheckin_date() {
        return checkin_date;
    }

    public void setCheckin_date(String checkin_date) {
        this.checkin_date = checkin_date;
    }

    public String getCheckout_date() {
        return checkout_date;
    }

    public void setCheckout_date(String checkout_date) {
        this.checkout_date = checkout_date;
    }

    public String getRoomFloor() {
        return roomFloor;
    }

    public void setRoomFloor(String roomFloor) {
        this.roomFloor = roomFloor;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getRoomOption() {
        return roomOption;
    }

    public void setRoomOption(String roomOption) {
        this.roomOption = roomOption;
    }

    public String getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(String basePrice) {
        this.basePrice = basePrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getKostId() {
        return kostId;
    }

    public void setKostId(String kostId) {
        this.kostId = kostId;
    }
}
