package com.example.kostku.model;

import com.google.firebase.database.DataSnapshot;

public class User {

    private String id, username, password;
    private int role;

    public User(DataSnapshot userSnapshot) {
        this.id = userSnapshot.getKey().toString();
        this.username = userSnapshot.child("username").getValue().toString();
        this.password = userSnapshot.child("password").getValue().toString();
        this.role = Integer.parseInt(userSnapshot.child("role").getValue().toString());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
