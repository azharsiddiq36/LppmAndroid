package com.example.azhar.lppm.Model;

import com.google.gson.annotations.SerializedName;

public class Kabupaten {
    @SerializedName("id")
    private String id;
    @SerializedName("id_prov")
    private String idProv;
    @SerializedName("nama")
    private String nama;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdProv() {
        return idProv;
    }

    public void setIdProv(String idProv) {
        this.idProv = idProv;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
