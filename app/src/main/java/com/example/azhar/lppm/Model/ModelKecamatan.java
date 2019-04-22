package com.example.azhar.lppm.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelKecamatan {
    @SerializedName("error")
    private Boolean error;
    @SerializedName("message")
    private String message;
    @SerializedName("daftar_kecamatan")
    private List<Kecamatan> daftarKecamatan;

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

    public List<Kecamatan> getDaftarKecamatan() {
        return daftarKecamatan;
    }

    public void setDaftarKecamatan(List<Kecamatan> daftarKecamatan) {
        this.daftarKecamatan = daftarKecamatan;
    }
}
