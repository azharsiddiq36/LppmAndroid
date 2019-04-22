package com.example.azhar.lppm.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelDesa {
    @SerializedName("error")
    private Boolean error;
    @SerializedName("message")
    private String message;
    @SerializedName("daftar_desa")
    private List<Desa> daftarDesa;

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

    public List<Desa> getDaftarDesa() {
        return daftarDesa;
    }

    public void setDaftarDesa(List<Desa> daftarDesa) {
        this.daftarDesa = daftarDesa;
    }
}
