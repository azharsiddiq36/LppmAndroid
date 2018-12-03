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
    @SerializedName("SipLok")
    private String sipLok;
    @SerializedName("SipInsTuj")
    private String sipInsTuj;
    @SerializedName("SipKabKot")
    private String sipKabKot;
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

    public String getSipLok() {
        return sipLok;
    }

    public void setSipLok(String sipLok) {
        this.sipLok = sipLok;
    }

    public String getSipInsTuj() {
        return sipInsTuj;
    }

    public void setSipInsTuj(String sipInsTuj) {
        this.sipInsTuj = sipInsTuj;
    }

    public String getSipKabKot() {
        return sipKabKot;
    }

    public void setSipKabKot(String sipKabKot) {
        this.sipKabKot = sipKabKot;
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

