package com.example.azhar.lppm.Model;

import com.google.gson.annotations.SerializedName;

public class Tahun {
    @SerializedName("tahun")
    private String tahun;

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }
}
