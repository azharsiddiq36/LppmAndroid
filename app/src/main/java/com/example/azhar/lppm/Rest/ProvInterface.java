package com.example.azhar.lppm.Rest;

import com.example.azhar.lppm.Model.ModelDesa;
import com.example.azhar.lppm.Model.ModelKabupaten;
import com.example.azhar.lppm.Model.ModelKecamatan;
import com.example.azhar.lppm.Model.ModelProvinsi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProvInterface {
    @GET("provinsi")
    Call<ModelProvinsi> getProvinsi();
    @GET("provinsi/{idprovinsi}/kabupaten")
    Call<ModelKabupaten> getKabupaten(@Path("idprovinsi") String idProvinsi);
    @GET("provinsi/kabupaten/{idkabupaten}/kecamatan")
    Call<ModelKecamatan> getKecamatan(@Path("idkabupaten") String idKabupaten);
    @GET("provinsi/kabupaten/kecamatan/{idkecamatan}/desa")
    Call<ModelDesa> getDesa(@Path("idkecamatan")String idKecamatan);
}
