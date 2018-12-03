package com.example.azhar.lppm.Model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Fgd {
    @SerializedName("FgdId")
    private String fgdId;
    @SerializedName("FgdNarNam")
    private String fgdNarNam;
    @SerializedName("FgdModNam")
    private String fgdModNam;
    @SerializedName("FgdJud")
    private String fgdJud;
    @SerializedName("FgdJen")
    private String fgdJen;
    @SerializedName("FgdLok")
    private String fgdLok;
    @SerializedName("FgdPesNam")
    private List<String> fgdPesNam = null;
    @SerializedName("FgdTglPel")
    private String fgdTglPel;

    public String getFgdId() {
        return fgdId;
    }

    public void setFgdId(String fgdId) {
        this.fgdId = fgdId;
    }

    public String getFgdNarNam() {
        return fgdNarNam;
    }

    public void setFgdNarNam(String fgdNarNam) {
        this.fgdNarNam = fgdNarNam;
    }

    public String getFgdModNam() {
        return fgdModNam;
    }

    public void setFgdModNam(String fgdModNam) {
        this.fgdModNam = fgdModNam;
    }

    public String getFgdJud() {
        return fgdJud;
    }

    public void setFgdJud(String fgdJud) {
        this.fgdJud = fgdJud;
    }

    public String getFgdJen() {
        return fgdJen;
    }

    public void setFgdJen(String fgdJen) {
        this.fgdJen = fgdJen;
    }

    public String getFgdLok() {
        return fgdLok;
    }

    public void setFgdLok(String fgdLok) {
        this.fgdLok = fgdLok;
    }

    public List<String> getFgdPesNam() {
        return fgdPesNam;
    }

    public void setFgdPesNam(List<String> fgdPesNam) {
        this.fgdPesNam = fgdPesNam;
    }

    public String getFgdTglPel() {
        return fgdTglPel;
    }

    public void setFgdTglPel(String fgdTglPel) {
        this.fgdTglPel = fgdTglPel;
    }

    public String getFgdTglSurat() {
        return fgdTglSurat;
    }

    public void setFgdTglSurat(String fgdTglSurat) {
        this.fgdTglSurat = fgdTglSurat;
    }

    public String getFgdTglAcc() {
        return fgdTglAcc;
    }

    public void setFgdTglAcc(String fgdTglAcc) {
        this.fgdTglAcc = fgdTglAcc;
    }

    @SerializedName("FgdTglSurat")
    private String fgdTglSurat;
    @SerializedName("FgdTglAcc")
    private String fgdTglAcc;
}
