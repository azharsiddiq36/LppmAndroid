package com.example.azhar.lppm.Model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Perjalanan {
    @SerializedName("StpId")
    private String stpId;
    @SerializedName("StpKetNam")
    private String stpKetNam;
    @SerializedName("StpNipNik")
    private String stpNipNik;
    @SerializedName("StpGol")
    private String stpGol;
    @SerializedName("StpAngNam")
    private List<String> stpAngNam = null;
    @SerializedName("StpJud")
    private String stpJud;
    @SerializedName("StpKlu")
    private String stpKlu;
    @SerializedName("StpTglKeg")
    private String stpTglKeg;
    @SerializedName("StpLok")
    private String stpLok;
    @SerializedName("StpJen")
    private String stpJen;
    @SerializedName("StpNom")
    private String stpNom;
    @SerializedName("StpTglSratKel")
    private String stpTglSratKel;
    @SerializedName("StpTglAcc")
    private String stpTglAcc;

    public String getStpId() {
        return stpId;
    }

    public void setStpId(String stpId) {
        this.stpId = stpId;
    }

    public String getStpKetNam() {
        return stpKetNam;
    }

    public void setStpKetNam(String stpKetNam) {
        this.stpKetNam = stpKetNam;
    }

    public String getStpNipNik() {
        return stpNipNik;
    }

    public void setStpNipNik(String stpNipNik) {
        this.stpNipNik = stpNipNik;
    }

    public String getStpGol() {
        return stpGol;
    }

    public void setStpGol(String stpGol) {
        this.stpGol = stpGol;
    }

    public List<String> getStpAngNam() {
        return stpAngNam;
    }

    public void setStpAngNam(List<String> stpAngNam) {
        this.stpAngNam = stpAngNam;
    }

    public String getStpJud() {
        return stpJud;
    }

    public void setStpJud(String stpJud) {
        this.stpJud = stpJud;
    }

    public String getStpKlu() {
        return stpKlu;
    }

    public void setStpKlu(String stpKlu) {
        this.stpKlu = stpKlu;
    }

    public String getStpTglKeg() {
        return stpTglKeg;
    }

    public void setStpTglKeg(String stpTglKeg) {
        this.stpTglKeg = stpTglKeg;
    }

    public String getStpLok() {
        return stpLok;
    }

    public void setStpLok(String stpLok) {
        this.stpLok = stpLok;
    }

    public String getStpJen() {
        return stpJen;
    }

    public void setStpJen(String stpJen) {
        this.stpJen = stpJen;
    }

    public String getStpNom() {
        return stpNom;
    }

    public void setStpNom(String stpNom) {
        this.stpNom = stpNom;
    }

    public String getStpTglSratKel() {
        return stpTglSratKel;
    }

    public void setStpTglSratKel(String stpTglSratKel) {
        this.stpTglSratKel = stpTglSratKel;
    }

    public String getStpTglAcc() {
        return stpTglAcc;
    }

    public void setStpTglAcc(String stpTglAcc) {
        this.stpTglAcc = stpTglAcc;
    }
}
