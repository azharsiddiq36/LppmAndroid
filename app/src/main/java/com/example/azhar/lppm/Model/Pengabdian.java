package com.example.azhar.lppm.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Pengabdian {
    @SerializedName("SipId")
    private Integer sipId;
    @SerializedName("SipKetNam")
    private String sipKetNam;
    @SerializedName("SipAngNam")
    private List<String> sipAngNam = null;
    @SerializedName("SipJud")
    private String sipJud;
    @SerializedName("SipTglKeg")
    private String sipTglKeg;
    @SerializedName("SipProv")
    private String sipProv;
    @SerializedName("SipKot")
    private String sipKot;
    @SerializedName("SipKec")
    private String sipKec;
    @SerializedName("SipKel")
    private String sipKel;
    @SerializedName("SipInsTuj")
    private String sipInsTuj;
    @SerializedName("SipTglSratKel")
    private String sipTglSratKel;
    @SerializedName("SipTglAcc")
    private String sipTglAcc;

    public Integer getSipId() {
        return sipId;
    }

    public void setSipId(Integer sipId) {
        this.sipId = sipId;
    }

    public String getSipKetNam() {
        return sipKetNam;
    }

    public void setSipKetNam(String sipKetNam) {
        this.sipKetNam = sipKetNam;
    }

    public List<String> getSipAngNam() {
        return sipAngNam;
    }

    public void setSipAngNam(List<String> sipAngNam) {
        this.sipAngNam = sipAngNam;
    }

    public String getSipJud() {
        return sipJud;
    }

    public void setSipJud(String sipJud) {
        this.sipJud = sipJud;
    }

    public String getSipTglKeg() {
        return sipTglKeg;
    }

    public void setSipTglKeg(String sipTglKeg) {
        this.sipTglKeg = sipTglKeg;
    }

    public String getSipProv() {
        return sipProv;
    }

    public void setSipProv(String sipProv) {
        this.sipProv = sipProv;
    }

    public String getSipKot() {
        return sipKot;
    }

    public void setSipKot(String sipKot) {
        this.sipKot = sipKot;
    }

    public String getSipKec() {
        return sipKec;
    }

    public void setSipKec(String sipKec) {
        this.sipKec = sipKec;
    }

    public String getSipKel() {
        return sipKel;
    }

    public void setSipKel(String sipKel) {
        this.sipKel = sipKel;
    }

    public String getSipInsTuj() {
        return sipInsTuj;
    }

    public void setSipInsTuj(String sipInsTuj) {
        this.sipInsTuj = sipInsTuj;
    }

    public String getSipTglSratKel() {
        return sipTglSratKel;
    }

    public void setSipTglSratKel(String sipTglSratKel) {
        this.sipTglSratKel = sipTglSratKel;
    }

    public String getSipTglAcc() {
        return sipTglAcc;
    }

    public void setSipTglAcc(String sipTglAcc) {
        this.sipTglAcc = sipTglAcc;
    }
}

