package com.example.kostku.model;

import com.google.firebase.database.DataSnapshot;

public class Kost {
    private String id, name, address;
    private String photo;

    public Kost(DataSnapshot kostSnapshot) {
        this.id = kostSnapshot.getKey().toString();
        this.name = kostSnapshot.child("name").getValue().toString();
        this.address = kostSnapshot.child("address").getValue().toString();
//        this.photo = photo;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
