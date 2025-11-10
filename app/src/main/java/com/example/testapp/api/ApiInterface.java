package com.example.testapp.api;

import com.example.testapp.model.ApiResponse;
import com.example.testapp.model.ImageModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    // This API expects a form POST parameter named "start"
    @FormUrlEncoded
    @POST("android_service/")
    Call<ApiResponse> getImages(@Field("start") int start);
}
