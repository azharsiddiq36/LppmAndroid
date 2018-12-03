package com.example.azhar.lppm.Rest;

public class CombineApi {
    public static final String BASE_URL = "http://192.168.43.201/lppm/office/";
    public static LppmInterface getApiService(){
        return ApiClient.getApiClient(BASE_URL).create(LppmInterface.class);
    }
}
