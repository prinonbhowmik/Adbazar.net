package com.adbazarnet.Api;

public class ApiUtils {
    public static final String BASE_URL = "https://api.adbazar.net/api/v1/";

    public static ApiInterface getUserService(){
        return RetrofitClient.getClient(BASE_URL).create(ApiInterface.class);
    }
}
