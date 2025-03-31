package com.example.kostku.model;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class Laporan {

    private User user;

    private String idLaporan, kategori, isiKeluhan, statusLaporan;

    private String tanggalLapor;

    public Laporan(String idLaporan, String kategori, String isiKeluhan, String statusLaporan, String tanggalLapor) {
//        this.user = user;
        this.idLaporan = idLaporan;
        this.kategori = kategori;
        this.isiKeluhan = isiKeluhan;
        this.statusLaporan = statusLaporan;
        this.tanggalLapor = tanggalLapor;
    }

//    public Laporan(User user, String idLaporan, String kategori, String isiKeluhan, String statusLaporan, Date tanggalLapor) {
//        this.user = user;
//        this.idLaporan = idLaporan;
//        this.kategori = kategori;
//        this.isiKeluhan = isiKeluhan;
//        this.statusLaporan = statusLaporan;
//        this.tanggalLapor = tanggalLapor;
//    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public String getIsiKeluhan() {
        return isiKeluhan;
    }

    public void setIsiKeluhan(String isiKeluhan) {
        this.isiKeluhan = isiKeluhan;
    }

    public String getStatusLaporan() {
        return statusLaporan;
    }

    public void setStatusLaporan(String statusLaporan) {
        this.statusLaporan = statusLaporan;
    }

    public String getTanggalLapor() {
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(new Date(tanggalLapor));
    }

    public void setTanggalLapor(String tanggalLapor) {
        this.tanggalLapor = tanggalLapor;
    }


}
