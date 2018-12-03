package com.example.azhar.lppm.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelPerjalanan {

    @SerializedName("data")
    private List<Perjalanan> data;

    public List<Perjalanan> getData() {
        return data;
    }

    public void setData(List<Perjalanan> data) {
        this.data = data;
    }
}
