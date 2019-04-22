package com.example.azhar.lppm.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataPegawai {
    @SerializedName("Pesan")
    private String pesan;
    @SerializedName("Pegawai")
    private List<Pegawai> pegawai;

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public List<Pegawai> getPegawai() {
        return pegawai;
    }

    public void setPegawai(List<Pegawai> pegawai) {
        this.pegawai = pegawai;
    }
}
