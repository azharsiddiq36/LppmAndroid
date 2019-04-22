package com.example.azhar.lppm.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelTahun {
    @SerializedName("pesan")
    private String pesan;
    @SerializedName("data")
    private List<Tahun> data;

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public List<Tahun> getData() {
        return data;
    }

    public void setData(List<Tahun> data) {
        this.data = data;
    }
}
