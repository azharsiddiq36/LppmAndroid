package com.example.azhar.lppm.Rest;

import com.example.azhar.lppm.Model.DataUser;
import com.example.azhar.lppm.Model.ModelFgd;
import com.example.azhar.lppm.Model.ModelPengabdian;
import com.example.azhar.lppm.Model.ModelPerjalanan;
import com.example.azhar.lppm.Model.ModelRespon;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LppmInterface {
    //auth
    @GET("api/auth/pengguna")
    Call<DataUser> listpengguna();
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
    @GET("api/data-penelitian/{id}")
    Call<ModelRespon> getDataPenelitian(@Path("id") String id);
    @FormUrlEncoded
    @POST("api/form-penelitian")
    Call<ResponseBody> insertPenelitian(@Field("SitEml") String SitEml,
                                        @Field("SitKetNam") String SitKetNam,
                                        @Field("SitAngNam") String SitAngNam,
                                        @Field("SitJud") String SitJud,
                                        @Field("SitTglKeg") String SitTglKeg,
                                        @Field("SitLok") String SitLok,
                                        @Field("SitInsTuj") String SitInsTuj,
                                        @Field("SitNomKtp") String SitNomKtp,
                                        @Field("SitFotKtp") String SitFotKtp,
                                        @Field("SitNom") String SitNom,
                                        @Field("nipNik") String nipNik);
    @FormUrlEncoded
    @POST("api/edit-penelitian/{id}/{key2}")
    Call<ResponseBody> editPenelitian(@Path("id") String id,@Path("key2") String idsurat,
                                      @Field("SitEml") String SitEml,
                                      @Field("SitKetNam") String SitKetNam,
                                      @Field("SitAngNam") String SitAngNam,
                                      @Field("SitJud") String SitJud,
                                      @Field("SitTglKeg") String SitTglKeg,
                                      @Field("SitLok") String SitLok,
                                      @Field("SitInsTuj") String SitInsTuj,
                                      @Field("SitNomKtp") String SitNomKtp,
                                      @Field("SitFotKtp") String SitFotKtp,
                                      @Field("SitNom") String SitNom,
                                      @Field("nipNik") String nipNik);
    //Pengabdian
    @FormUrlEncoded
    @POST("api/form-izinpengabdian")
    Call<ResponseBody> insertPengabdian(@Field("nipNik") String nipNik,
                                        @Field("SipAngNam") String SipAngNam,
                                        @Field("StpKetNam") String SipKetNam,
                                        @Field("SipJud") String SipJud,
                                        @Field("SipTglKeg") String SipTglKeg,
                                        @Field("SipLok") String SipLok,
                                        @Field("SipInsTuj") String SipInsTuj,
                                        @Field("SipKabKot") String SipKabKot);
    @GET("api/data-pengabdian/{id}")
    Call<ModelPengabdian> getDataPengabdian(@Path("id") String id);
    //FGD
    @FormUrlEncoded
    @POST("api/form-FGD")
    Call<ResponseBody> insertFGD(@Field("nipNik") String nipNik,
                                 @Field("FgdNarNam") String FgdNarNam,
                                 @Field("FgdModNam") String FgdModNam,
                                 @Field("FgdJud") String FgdJud,
                                 @Field("FgdJen") String FgdJen,
                                 @Field("FgdLok") String FgdLok,
                                 @Field("FgdPesNam") String FgdPesNam,
                                 @Field("FgdTglPel") String FgdTglPel);
    @GET("api/data-fgd/{id}")
    Call<ModelFgd> getDataFgd(@Path("id") String id);

    //perjalanan
    @GET("api/data-perjalanan/{id}")
    Call<ModelPerjalanan> getDataPerjalanan(@Path("id") String id);
    @FormUrlEncoded
    @POST("api/form-tugasperjalanan")
    Call<ResponseBody> insertPerjalanan(@Field("StpKetNam") String StpKetNam,
                                        @Field("StpAngNam") String StpAngNam,
                                        @Field("StpJud") String StpJud,
                                        @Field("StpTglKeg") String StpTglKeg,
                                        @Field("StpLok") String StpLok,
                                        @Field("StpGol") String StpGol,
                                        @Field("StpKlu") String StpKlu,
                                        @Field("StpJen") String StpJen,
                                        @Field("StpNom") String StpNom,
                                        @Field("StpNipNik") String StpNipNik);
    @FormUrlEncoded
    @POST("api/edit-perjalanan/{id}/{key2}")
    Call<ResponseBody> editPerjalanan(@Path("id") String id,@Path("key2") String idsurat,
                                      @Field("StpKetNam") String StpKetNam,
                                      @Field("StpAngNam") String StpAngNam,
                                      @Field("StpJud") String StpJud,
                                      @Field("StpTglKeg") String StpTglKeg,
                                      @Field("StpLok") String StpLok,
                                      @Field("StpGol") String StpGol,
                                      @Field("StpKlu") String StpKlu,
                                      @Field("StpJen") String StpJen,
                                      @Field("StpNom") String StpNom,
                                      @Field("StpNipNik") String StpNipNik);
}
