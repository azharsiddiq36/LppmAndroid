package com.example.azhar.lppm.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelPengabdian {
    @SerializedName("data")
    private List<Pengabdian> data;

    public List<Pengabdian> getData() {
        return data;
    }

    public void setData(List<Pengabdian> data) {
        this.data = data;
    }
}
