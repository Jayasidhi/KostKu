package com.example.kostku.model;

import com.google.firebase.database.DataSnapshot;

public class Room {
    private String id, name, kost_id, floor;

    public Room(DataSnapshot roomSnapshot) {
        this.id = roomSnapshot.getKey().toString();
        this.name = roomSnapshot.child("name").getValue().toString();
        this.kost_id = roomSnapshot.child("kost_id").getValue().toString();
        this.floor = roomSnapshot.child("floor").getValue().toString();
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
