package com.example.azhar.lppm.Model;

import com.google.gson.annotations.SerializedName;

public class Pengguna {
    @SerializedName("id_pengguna")
    private String id_pengguna;
    @SerializedName("nip")
    private String nip;
    @SerializedName("nama")
    private String nama;
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("hak_akses")
    private String hak_akses;
    @SerializedName("foto")
    private String foto;
    @SerializedName("konfirmasi_email")
    private String konfirmasi_email;

    public String getFoto() {
        return foto;
    }
    public void setFoto(String foto) {
        this.foto = foto;
    }
    public String getId_pengguna() {
        return id_pengguna;
    }
    public void setId_pengguna(String id_pengguna) {
        this.id_pengguna = id_pengguna;
    }
    public String getNip() {
        return nip;
    }
    public void setNip(String nip) {
        this.nip = nip;
    }
    public String getNama() {
        return nama;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getHak_akses() {
        return hak_akses;
    }
    public void setHak_akses(String hak_akses) {
        this.hak_akses = hak_akses;
    }
    public String getKonfirmasi_email() {
        return konfirmasi_email;
    }
    public void setKonfirmasi_email(String konfirmasi_email) {
        this.konfirmasi_email = konfirmasi_email;
    }
    @SerializedName("isi_surat")
    private String isiSurat;
    @SerializedName("tahun")
    private String tahun;

    public String getIsiSurat() {
        return isiSurat;
    }

    public void setIsiSurat(String isiSurat) {
        this.isiSurat = isiSurat;
    }

    public String getTahun() {
        return tahun;
    }

    public void setTahun(String tahun) {
        this.tahun = tahun;
    }
}

