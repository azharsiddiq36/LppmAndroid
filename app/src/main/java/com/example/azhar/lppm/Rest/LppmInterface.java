package com.example.azhar.lppm.Rest;

import android.util.Base64;

import com.example.azhar.lppm.Model.DataPegawai;
import com.example.azhar.lppm.Model.DataUser;
import com.example.azhar.lppm.Model.ModelDesa;
import com.example.azhar.lppm.Model.ModelFgd;
import com.example.azhar.lppm.Model.ModelKabupaten;
import com.example.azhar.lppm.Model.ModelKecamatan;
import com.example.azhar.lppm.Model.ModelPengabdian;
import com.example.azhar.lppm.Model.ModelPerjalanan;
import com.example.azhar.lppm.Model.ModelProvinsi;
import com.example.azhar.lppm.Model.ModelRespon;
import com.example.azhar.lppm.Model.ModelTahun;
import com.example.azhar.lppm.Model.Pengguna;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface LppmInterface {
    //auth
    @GET("api/auth/pengguna")
    Call<DataUser> listpengguna();
    @GET("api/auth/getallpegawai")
    Call<DataPegawai> listPegawai();
    @FormUrlEncoded
    @POST("api/auth/forget")
    Call<ResponseBody>lupaPassword(@Field("nip") String nip);
    @FormUrlEncoded
    @POST("api/auth/loginproses")
    Call<ResponseBody> loginRequest(@Field("nipNik") String nip,
                                    @Field("password") String password);
    @FormUrlEncoded
    @POST("api/auth/ubahpassword")
    Call<ResponseBody> ubahPassword(@Field("nipNik") String nip,
                                    @Field("password") String password);


    //penelelitian
    @GET("api/disposisi-penelitian/{id}/{akses}")
    Call<ModelRespon> getDisposisi(@Path("id") String id, @Path("akses") String akses);

    @GET("api/list-penelitian/disposisi/{id}/{key2}")
    Call<ModelRespon> setDisposisi(@Path("id")String id,@Path("key2")String Key2);

        @GET("api/data-penelitian/{id}/{tahun}")
    Call<ModelRespon> getDataPenelitian(@Path("id") String id,@Path("tahun") String tahun);

    @FormUrlEncoded
    @POST("api/form-penelitian")
    Call<ResponseBody> insertPenelitian(@Field("SitEml") String SitEml,
                                        @Field("SitKetNam") String SitKetNam,
                                        @Field("SitAngNam") String SitAngNam,
                                        @Field("SitJud") String SitJud,
                                        @Field("SitTglKeg") String SitTglKeg,
                                        @Field("SitProv") String SitProv,
                                        @Field("SitKot") String SitKot,
                                        @Field("SitKec") String SitKec,
                                        @Field("SitKel") String SitKel,
                                        @Field("SitInsTuj") String SitInsTuj,
                                        @Field("SitNomKtp") String SitNomKtp,
                                        @Field("SitFotKtp") String SitFotKtp,
                                        @Field("SitNom") String SitNom,
                                        @Field("nipNik") String nipNik);
    @FormUrlEncoded
    @POST("api/edit-penelitian/{id}/{key2}/{tahun}")
    Call<ResponseBody> editPenelitian(@Path("id") String id,@Path("key2") String idsurat,
                                      @Path("tahun")String tahun,
                                      @Field("SitEml") String SitEml,
                                      @Field("SitKetNam") String SitKetNam,
                                      @Field("SitAngNam") String SitAngNam,
                                      @Field("SitJud") String SitJud,
                                      @Field("SitTglKeg") String SitTglKeg,
                                      @Field("SitProv") String SitProv,
                                      @Field("SitKot") String SitKot,
                                      @Field("SitKec") String SitKec,
                                      @Field("SitKel") String SitKel,
                                      @Field("SitInsTuj") String SitInsTuj,
                                      @Field("SitNomKtp") String SitNomKtp,
                                      @Field("SitFotKtp") String SitFotKtp,
                                      @Field("SitNom") String SitNom);
    //Pengabdian

    @FormUrlEncoded
    @POST("api/form-izinpengabdian")
    Call<ResponseBody> insertPengabdian(@Field("nipNik") String nipNik,
                                        @Field("SipKetNam") String SipKetNam,
                                        @Field("SipAngNam") String SipAngNam,
                                        @Field("SipJud") String SipJud,
                                        @Field("SipTglKeg") String SipTglKeg,
                                        @Field("SipProv") String SipProv,
                                        @Field("SipKot") String SipKot,
                                        @Field("SipKec") String SipKec,
                                        @Field("SipKel") String SipKel,
                                        @Field("SipInsTuj") String SipInsTuj);
    @GET("api/data-pengabdian/{id}/{tahun}")
    Call<ModelPengabdian> getDataPengabdian(@Path("id") String id,@Path("tahun") String tahun);

    @GET("api/list-pengabdian/disposisi/{id}/{key2}")
    Call<ModelRespon> setDisposisiPengabdian(@Path("id")String id,@Path("key2")String Key2);

    @FormUrlEncoded
    @POST("api/edit-pengabdian/{id}/{key2}/{tahun}")
    Call<ResponseBody> editPengabdian(@Path("id") String id,@Path("key2") String idsurat,
                                        @Path("tahun")String tahun,
                                      @Field("SipKetNam") String SipKetNam,
                                      @Field("SipAngNam") String SipAngNam,
                                      @Field("SipJud") String SipJud,
                                      @Field("SipTglKeg") String SipTglKeg,
                                      @Field("SipProv") String SipProv,
                                      @Field("SipKot") String SipKot,
                                      @Field("SipKec") String SipKec,
                                      @Field("SipKel") String SipKel,
                                      @Field("SipInsTuj") String SipInsTuj);

    //FGD
    @FormUrlEncoded
    @POST("api/form-fgd")
    Call<ResponseBody> insertFGD(@Field("nipNik") String nipNik,
                                 @Field("FgdNarNam") String FgdNarNam,
                                 @Field("FgdModNam") String FgdModNam,
                                 @Field("FgdJud") String FgdJud,
                                 @Field("FgdJen") String FgdJen,
                                 @Field("FgdProv") String FgdProv,
                                 @Field("FgdKot") String FgdKot,
                                 @Field("FgdKec") String FgdKec,
                                 @Field("FgdKel") String FgdKel,
                                 @Field("FgdPesNam") String FgdPesNam,
                                 @Field("FgdTglPel") String FgdTglPel);

    @FormUrlEncoded
    @POST("api/edit-fgd/{id}/{key2}/{tahun}")
    Call<ResponseBody> editFGD(@Path("id") String id,@Path("key2") String idsurat,
                                 @Path("tahun")String tahun,
                                 @Field("FgdNarNam") String FgdNarNam,
                                 @Field("FgdModNam") String FgdModNam,
                                 @Field("FgdJud") String FgdJud,
                                 @Field("FgdJen") String FgdJen,
                               @Field("FgdProv") String FgdProv,
                               @Field("FgdKot") String FgdKot,
                               @Field("FgdKec") String FgdKec,
                               @Field("FgdKel") String FgdKel,
                                 @Field("FgdPesNam") String FgdPesNam,
                                 @Field("FgdTglPel") String FgdTglPel);
    @GET("api/data-fgd/{id}/{tahun}")
    Call<ModelFgd> getDataFgd(@Path("id") String id,@Path("tahun") String tahun);
    @GET("api/list-fgd/disposisi/{id}/{key2}")
    Call<ModelRespon> setDisposisiFgd(@Path("id")String id,@Path("key2")String Key2);

    //perjalanan
    @GET("api/list-perjalanan/disposisi/{id}/{key2}")
    Call<ModelRespon> setDisposisiPerjalanan(@Path("id")String id,@Path("key2")String Key2);
    @GET("api/data-perjalanan/{id}/{tahun}")
    Call<ModelPerjalanan> getDataPerjalanan(@Path("id") String id,@Path("tahun") String tahun);
    @FormUrlEncoded
    @POST("api/form-tugasperjalanan")
    Call<ResponseBody> insertPerjalanan(@Field("StpKetNam") String StpKetNam,
                                        @Field("StpKetPang") String StpKetPang,
                                        @Field("StpKetJab") String StpKetJab,
                                        @Field("StpAngNam") String StpAngNam,
                                        @Field("StpAngJab") String StpAngJab,
                                        @Field("StpAngPang") String StpAngPang,
                                        @Field("StpJud") String StpJud,
                                        @Field("StpTglKeg") String StpTglKeg,
                                        @Field("StpProv") String StpProv,
                                        @Field("StpKot") String StpKot,
                                        @Field("StpKec") String StpKec,
                                        @Field("StpKel") String StpKel,
                                        @Field("StpKlu") String StpKlu,
                                        @Field("StpTglKlu") String StpTglKlu,
                                        @Field("StpJen") String StpJen,
                                        @Field("StpNom") String StpNom,
                                        @Field("StpNipNik") String StpNipNik);
    @FormUrlEncoded
    @POST("api/edit-perjalanan/{id}/{key2}/{tahun}")
    Call<ResponseBody> editPerjalanan(@Path("id") String id,@Path("key2") String idsurat,
                                      @Path("tahun")String tahun,
                                      @Field("StpKetNam") String StpKetNam,
                                      @Field("StpKetPang") String StpKetPang,
                                      @Field("StpKetJab") String StpKetJab,
                                      @Field("StpAngNam") String StpAngNam,
                                      @Field("StpAngJab") String StpAngJab,
                                      @Field("StpAngPang") String StpAngPang,
                                      @Field("StpJud") String StpJud,
                                      @Field("StpTglKeg") String StpTglKeg,
                                      @Field("StpProv") String StpProv,
                                      @Field("StpKot") String StpKot,
                                      @Field("StpKec") String StpKec,
                                      @Field("StpKel") String StpKel,
                                      @Field("StpKlu") String StpKlu,
                                      @Field("StpTglKlu") String StpTglKlu,
                                      @Field("StpJen") String StpJen,
                                      @Field("StpNom") String StpNom);
    @GET("api/list-tahun/{nip}")
    Call<ModelTahun> getTahun(@Path("nip")String nip);

    @Multipart
    @POST("api/form-penelitian/upload")
    Call<ResponseBody> uploadImage(@Part MultipartBody.Part photo,
                             @Part("nip") RequestBody text);
}
