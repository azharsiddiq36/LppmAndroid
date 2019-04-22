package com.example.azhar.lppm.Model;

import com.google.gson.annotations.SerializedName;

public class Semuaprovinsi {
    @SerializedName("id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    @SerializedName("nama")
    private String nama;
}

