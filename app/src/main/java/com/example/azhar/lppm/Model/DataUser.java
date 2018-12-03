package com.example.azhar.lppm.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataUser {
    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public List<Pengguna> getUser() {
        return user;
    }

    public void setUser(List<Pengguna> user) {
        this.user = user;
    }

    @SerializedName("pesan")
    private String pesan;
    @SerializedName("pengguna")
    private List<Pengguna> user;

    @SerializedName("peneliti")
    private List<Integer> peneliti;
    @SerializedName("pengabdi")
    private List<Integer> pengabdi;
    @SerializedName("perjalanan")
    private List<Integer> perjalanan;
    @SerializedName("fgd")
    private List<Integer> fgd;

    public List<Integer> getPeneliti() {
        return peneliti;
    }

    public void setPeneliti(List<Integer> peneliti) {
        this.peneliti = peneliti;
    }

    public List<Integer> getPengabdi() {
        return pengabdi;
    }

    public void setPengabdi(List<Integer> pengabdi) {
        this.pengabdi = pengabdi;
    }

    public List<Integer> getPerjalanan() {
        return perjalanan;
    }

    public void setPerjalanan(List<Integer> perjalanan) {
        this.perjalanan = perjalanan;
    }

    public List<Integer> getFgd() {
        return fgd;
    }

    public void setFgd(List<Integer> fgd) {
        this.fgd = fgd;
    }
}
