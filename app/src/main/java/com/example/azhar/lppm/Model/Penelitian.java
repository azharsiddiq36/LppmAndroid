package com.example.azhar.lppm.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Penelitian {
    @SerializedName("SitId")
    private Integer sitId;
    @SerializedName("SitEml")
    private String sitEml;
    @SerializedName("SitKetNam")
    private String sitKetNam;
    @SerializedName("SitAngNam")
    private List<String> sitAngNam = null;
    @SerializedName("SitJud")
    private String sitJud;
    @SerializedName("SitTglKeg")
    private String sitTglKeg;
    @SerializedName("SitProv")
    private String sitProv;
    @SerializedName("SitKot")
    private String sitKot;
    @SerializedName("SitKec")
    private String sitKec;
    @SerializedName("SitKel")
    private String sitKel;
    @SerializedName("SitInsTuj")
    private String sitInsTuj;
    @SerializedName("SitNomKtp")
    private String sitNomKtp;
    @SerializedName("SitFotKtp")
    private String sitFotKtp;
    @SerializedName("SitNom")
    private String sitNom;
    @SerializedName("SitTglSratKel")
    private String sitTglSratKel;
    @SerializedName("SitTglAcc")
    private String sitTglAcc;

    public Integer getSitId() {
        return sitId;
    }

    public void setSitId(Integer sitId) {
        this.sitId = sitId;
    }

    public String getSitEml() {
        return sitEml;
    }

    public void setSitEml(String sitEml) {
        this.sitEml = sitEml;
    }

    public String getSitKetNam() {
        return sitKetNam;
    }

    public void setSitKetNam(String sitKetNam) {
        this.sitKetNam = sitKetNam;
    }

    public List<String> getSitAngNam() {
        return sitAngNam;
    }

    public void setSitAngNam(ArrayList<String> sitAngNam) {
        this.sitAngNam = sitAngNam;
    }

    public String getSitJud() {
        return sitJud;
    }

    public void setSitJud(String sitJud) {
        this.sitJud = sitJud;
    }

    public String getSitTglKeg() {
        return sitTglKeg;
    }

    public void setSitTglKeg(String sitTglKeg) {
        this.sitTglKeg = sitTglKeg;
    }

    public void setSitAngNam(List<String> sitAngNam) {
        this.sitAngNam = sitAngNam;
    }

    public String getSitProv() {
        return sitProv;
    }

    public void setSitProv(String sitProv) {
        this.sitProv = sitProv;
    }

    public String getSitKot() {
        return sitKot;
    }

    public void setSitKot(String sitKot) {
        this.sitKot = sitKot;
    }

    public String getSitKec() {
        return sitKec;
    }

    public void setSitKec(String sitKec) {
        this.sitKec = sitKec;
    }

    public String getSitKel() {
        return sitKel;
    }

    public void setSitKel(String sitKel) {
        this.sitKel = sitKel;
    }

    public String getSitInsTuj() {
        return sitInsTuj;
    }

    public void setSitInsTuj(String sitInsTuj) {
        this.sitInsTuj = sitInsTuj;
    }

    public String getSitNomKtp() {
        return sitNomKtp;
    }

    public void setSitNomKtp(String sitNomKtp) {
        this.sitNomKtp = sitNomKtp;
    }

    public String getSitFotKtp() {
        return sitFotKtp;
    }

    public void setSitFotKtp(String sitFotKtp) {
        this.sitFotKtp = sitFotKtp;
    }

    public String getSitNom() {
        return sitNom;
    }

    public void setSitNom(String sitNom) {
        this.sitNom = sitNom;
    }

    public String getSitTglSratKel() {
        return sitTglSratKel;
    }

    public void setSitTglSratKel(String sitTglSratKel) {
        this.sitTglSratKel = sitTglSratKel;
    }

    public String getSitTglAcc() {
        return sitTglAcc;
    }

    public void setSitTglAcc(String sitTglAcc) {
        this.sitTglAcc = sitTglAcc;
    }
}