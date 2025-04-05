package com.example.kostku.model;

public class UserSession {

    private static UserSession userSession;
    private String username;
    private int role;

    private String idKost;

    public static UserSession getInstance() {
        if (userSession == null) {
            userSession = new UserSession();
        }
        return userSession;
    }

    public UserSession() {
    }

    public UserSession(String username, int role, String idKost) {
        this.username = username;
        this.role = role;
        this.idKost = idKost;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getIdKost() {
        return idKost;
    }

    public void setIdKost(String idKost) {
        this.idKost = idKost;
    }
}
