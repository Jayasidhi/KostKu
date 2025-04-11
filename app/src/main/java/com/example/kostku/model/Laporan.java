package com.example.kostku.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.google.firebase.database.DataSnapshot;

public class Laporan {

    private String idLaporan, kategori, isi_keluhan, tanggal_lapor, status_laporan, kost_id, room_name, username;

    public Laporan(DataSnapshot laporanSnapshot) {
        this.idLaporan = laporanSnapshot.getKey().toString();
        this.kategori = laporanSnapshot.child("kategori").getValue().toString();
        this.isi_keluhan = laporanSnapshot.child("isi_keluhan").getValue().toString();
        this.status_laporan = laporanSnapshot.child("status_laporan").getValue().toString();
        this.tanggal_lapor = laporanSnapshot.child("tanggal_lapor").getValue().toString();
        this.kost_id = laporanSnapshot.child("kost_id").getValue().toString();
        this.room_name = laporanSnapshot.child("room_name").getValue().toString();
        this.username = laporanSnapshot.child("username").getValue().toString();
    }

    public Laporan(String kategori, String isi_keluhan, String kost_id, String room_name, String username) {
        this.kategori = kategori;
        this.isi_keluhan = isi_keluhan;
        this.status_laporan = "0";
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        this.tanggal_lapor = df.format(date);
        this.kost_id = kost_id;
        this.room_name = room_name;
        this.username = username;
    }


    public String getId() {
        return idLaporan;
    }

    public void setId(String idLaporan) {
        this.idLaporan = idLaporan;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getIsi_keluhan() {
        return isi_keluhan;
    }

    public void setIsi_keluhan(String isi_keluhan) {
        this.isi_keluhan = isi_keluhan;
    }

    public String getStatus_laporan() {
        return status_laporan;
    }

    public void setStatus_laporan(String status_laporan) {
        this.status_laporan = status_laporan;
    }

    public String getTanggal_lapor() {
        return tanggal_lapor;
    }

    public void setTanggal_lapor(String tanggal_lapor) {
        this.tanggal_lapor = tanggal_lapor;
    }

    public String getIdLaporan() {
        return idLaporan;
    }

    public void setIdLaporan(String idLaporan) {
        this.idLaporan = idLaporan;
    }

    public String getKost_id() {
        return kost_id;
    }

    public void setKost_id(String kost_id) {
        this.kost_id = kost_id;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
