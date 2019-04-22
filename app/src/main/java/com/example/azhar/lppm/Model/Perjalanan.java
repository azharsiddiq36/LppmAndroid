package com.example.azhar.lppm.Model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Perjalanan {
    @SerializedName("StpId")
    private Integer stpId;
    @SerializedName("StpKetNam")
    private String stpKetNam;
    @SerializedName("StpNipNik")
    private String stpNipNik;
    @SerializedName("StpKetJab")
    private String stpKetJab;
    @SerializedName("StpKetPang")
    private String stpKetPang;
    @SerializedName("StpAngNam")
    private List<String> stpAngNam = null;
    @SerializedName("StpAngJab")
    private List<String> stpAngJab = null;
    @SerializedName("StpAngPang")
    private List<String> stpAngPang = null;
    @SerializedName("StpJud")
    private String stpJud;
    @SerializedName("StpKlu")
    private String stpKlu;
    @SerializedName("StpTglKlu")
    private String stpTglKlu;
    @SerializedName("StpTglKeg")
    private String stpTglKeg;
    @SerializedName("StpProv")
    private String stpProv;
    @SerializedName("StpKot")
    private String stpKot;
    @SerializedName("StpKec")
    private String stpKec;
    @SerializedName("StpKel")
    private String stpKel;
    @SerializedName("StpJen")
    private String stpJen;
    @SerializedName("StpNom")
    private String stpNom;
    @SerializedName("StpTglSratKel")
    private String stpTglSratKel;
    @SerializedName("StpTglAcc")
    private String stpTglAcc;

    public Integer getStpId() {
        return stpId;
    }

    public void setStpId(Integer stpId) {
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

    public String getStpKetJab() {
        return stpKetJab;
    }

    public void setStpKetJab(String stpKetJab) {
        this.stpKetJab = stpKetJab;
    }

    public String getStpKetPang() {
        return stpKetPang;
    }

    public void setStpKetPang(String stpKetPang) {
        this.stpKetPang = stpKetPang;
    }

    public List<String> getStpAngNam() {
        return stpAngNam;
    }

    public void setStpAngNam(List<String> stpAngNam) {
        this.stpAngNam = stpAngNam;
    }

    public List<String> getStpAngJab() {
        return stpAngJab;
    }

    public void setStpAngJab(List<String> stpAngJab) {
        this.stpAngJab = stpAngJab;
    }

    public List<String> getStpAngPang() {
        return stpAngPang;
    }

    public void setStpAngPang(List<String> stpAngPang) {
        this.stpAngPang = stpAngPang;
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

    public String getStpTglKlu() {
        return stpTglKlu;
    }

    public void setStpTglKlu(String stpTglKlu) {
        this.stpTglKlu = stpTglKlu;
    }

    public String getStpTglKeg() {
        return stpTglKeg;
    }

    public void setStpTglKeg(String stpTglKeg) {
        this.stpTglKeg = stpTglKeg;
    }

    public String getStpProv() {
        return stpProv;
    }

    public void setStpProv(String stpProv) {
        this.stpProv = stpProv;
    }

    public String getStpKot() {
        return stpKot;
    }

    public void setStpKot(String stpKot) {
        this.stpKot = stpKot;
    }

    public String getStpKec() {
        return stpKec;
    }

    public void setStpKec(String stpKec) {
        this.stpKec = stpKec;
    }

    public String getStpKel() {
        return stpKel;
    }

    public void setStpKel(String stpKel) {
        this.stpKel = stpKel;
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
