package com.example.kostku.model;

public class Kost {
    private String id, name, address;
    private String photo;

    public Kost(String id, String name, String address, String photo) {
        this.id = id;
        this.name = name;
        this.address = address;
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
