package com.example.azhar.lppm.Model;

import com.google.gson.annotations.SerializedName;

public class Desa {
    @SerializedName("id")
    private String id;
    @SerializedName("id_kecamatan")
    private String idKecamatan;
    @SerializedName("nama")
    private String nama;

    public String getIdKecamatan() {
        return idKecamatan;
    }

    public void setIdKecamatan(String idKecamatan) {
        this.idKecamatan = idKecamatan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
