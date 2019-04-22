package com.example.azhar.lppm.Rest;

public class CombineApi {

    public static final String BASE_URL = "http://karir-suska.org/lppm/lppm/office/";
    //public static final String BASE_URL = "http://192.168.43.201/lppm/office/";
    //public static final String BASE_URL = "http://10.0.0.2/lppm/office/";
    public static final String BASE_URL_PROV = "https://dev.farizdotid.com/api/daerahindonesia/";
    public static LppmInterface getApiService(){
        return ApiClient.getApiClient(BASE_URL).create(LppmInterface.class);
    }
    public static ProvInterface getApiProv(){
        return ApiClient.getApiProv(BASE_URL_PROV).create(ProvInterface.class);
    }
}
