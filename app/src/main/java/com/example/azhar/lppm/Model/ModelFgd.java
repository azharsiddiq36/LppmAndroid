package com.example.azhar.lppm.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelFgd {
    @SerializedName("data")
    private List<Fgd> data;

    public List<Fgd> getData() {
        return data;
    }

    public void setData(List<Fgd> data) {
        this.data = data;
    }
}
