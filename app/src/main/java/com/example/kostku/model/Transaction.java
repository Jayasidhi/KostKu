package com.example.kostku.model;

import com.google.firebase.database.DataSnapshot;

public class Transaction {

    String id, name, phone_number, room_floor, room_number, room_option, base_price, total_price, kost_id;
    String checkin_date;
    String checkout_date;

    public Transaction(DataSnapshot transactionSnapshot) {
        this.id = transactionSnapshot.getKey().toString();
        this.name = transactionSnapshot.child("name").getValue().toString();
        this.phone_number = transactionSnapshot.child("phone_number").getValue().toString();

        this.checkin_date = transactionSnapshot.child("checkin_date").getValue().toString();
        this.checkout_date = transactionSnapshot.child("checkout_date").getValue().toString();

        this.room_floor = transactionSnapshot.child("room_floor").getValue().toString();
        this.room_number = transactionSnapshot.child("room_number").getValue().toString();
        this.room_option = transactionSnapshot.child("room_option").getValue().toString();
        this.base_price = transactionSnapshot.child("base_price").getValue().toString();
        this.total_price = transactionSnapshot.child("total_price").getValue().toString();
        this.kost_id = transactionSnapshot.child("kost_id").getValue().toString();
    }

    public Transaction(String name, String phone_number, String room_floor, String room_number, String room_option, String base_price, String total_price, String kost_id, String checkin_date, String checkout_date) {
        this.name = name;
        this.phone_number = phone_number;
        this.room_floor = room_floor;
        this.room_number = room_number;
        this.room_option = room_option;
        this.base_price = base_price;
        this.total_price = total_price;
        this.kost_id = kost_id;
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

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
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

    public String getRoom_floor() {
        return room_floor;
    }

    public void setRoom_floor(String room_floor) {
        this.room_floor = room_floor;
    }

    public String getRoom_number() {
        return room_number;
    }

    public void setRoom_number(String room_number) {
        this.room_number = room_number;
    }

    public String getRoom_option() {
        return room_option;
    }

    public void setRoom_option(String room_option) {
        this.room_option = room_option;
    }

    public String getBase_price() {
        return base_price;
    }

    public void setBase_price(String base_price) {
        this.base_price = base_price;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getKost_id() {
        return kost_id;
    }

    public void setKost_id(String kost_id) {
        this.kost_id = kost_id;
    }
}
