package com.example.azhar.lppm.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelRespon {
    @SerializedName("pesan")
    private String pesan;
    @SerializedName("data")
    private List<Penelitian> data;
    @SerializedName("idpengajuan")
    private List<String> idpengajuan;
    @SerializedName("pengguna")
    private List<String> pengguna;

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public List<Penelitian> getData() {
        return data;
    }

    public void setData(List<Penelitian> data) {
        this.data = data;
    }

    public List<String> getIdpengajuan() {
        return idpengajuan;
    }

    public void setIdpengajuan(List<String> idpengajuan) {
        this.idpengajuan = idpengajuan;
    }

    public List<String> getPengguna() {
        return pengguna;
    }

    public void setPengguna(List<String> pengguna) {
        this.pengguna = pengguna;
    }


}

