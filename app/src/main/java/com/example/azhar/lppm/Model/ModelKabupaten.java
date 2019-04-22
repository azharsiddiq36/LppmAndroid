package com.example.azhar.lppm.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelKabupaten {
    @SerializedName("error")
    private Boolean error;
    @SerializedName("message")
    private String message;
    @SerializedName("daftar_kecamatan")
    private List<Kabupaten> daftarKecamatan;

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

    public List<Kabupaten> getDaftarKecamatan() {
        return daftarKecamatan;
    }

    public void setDaftarKecamatan(List<Kabupaten> daftarKecamatan) {
        this.daftarKecamatan = daftarKecamatan;
    }
}
