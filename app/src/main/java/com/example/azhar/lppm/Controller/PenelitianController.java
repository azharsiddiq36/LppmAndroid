package com.example.azhar.lppm.Controller;

public class PenelitianController {
    public static String SitEml = "email";
    public static String SitKetNam ="ketua";
    public static String SitAngNam = "anggota";
    public static String SitJud = "judul";
    public static String SitTglKeg = "tgl";
    public static String SitLok = "lokasi";
    public static String SitInsTuj = "instansi";
    public static String SitNomKtp = "noktp";
    public static String SitFotKtp = "fotoktp";
    public static String SitNom = "nowa";
    public static String nipNik = "nipNik";
    public PenelitianController(String sitEml,
                                String sitKetNam,
                                String sitAngNam,
                                String sitJud,
                                String sitTglKeg,
                                String sitLok,
                                String sitInsTuj,
                                String sitNomKtp,
                                String sitFotKtp,
                                String sitNom,
                                String nipNik){
        this.SitEml = sitEml;
        this.SitAngNam = sitAngNam;
        this.SitFotKtp = sitFotKtp;
        this.SitInsTuj = sitInsTuj;
        this.SitJud = sitJud;
        this.SitKetNam = sitKetNam;
        this.SitLok = sitLok;
        this.SitTglKeg = sitTglKeg;
        this.SitNom = sitNom;
        this.SitNomKtp = sitNomKtp;
        this.nipNik = nipNik;
    }
    public void input(){

    }
}
