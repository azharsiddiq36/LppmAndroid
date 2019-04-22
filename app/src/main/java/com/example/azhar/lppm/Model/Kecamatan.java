package com.example.azhar.lppm.Model;

import com.google.gson.annotations.SerializedName;

public class Kecamatan {
    @SerializedName("id")
    private String id;
    @SerializedName("id_kabupaten")
    private String idKabupaten;
    @SerializedName("nama")
    private String nama;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdKabupaten() {
        return idKabupaten;
    }

    public void setIdKabupaten(String idKabupaten) {
        this.idKabupaten = idKabupaten;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
