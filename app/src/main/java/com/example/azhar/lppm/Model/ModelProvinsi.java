package com.example.azhar.lppm.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ModelProvinsi {
    @SerializedName("error")
    private Boolean error;
    @SerializedName("message")
    private String message;
    @SerializedName("semuaprovinsi")
    private List<Semuaprovinsi> semuaprovinsi;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Semuaprovinsi> getSemuaprovinsi() {
        return semuaprovinsi;
    }

    public void setSemuaprovinsi(List<Semuaprovinsi> semuaprovinsi) {
        this.semuaprovinsi = semuaprovinsi;
    }
}
