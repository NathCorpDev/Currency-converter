package com.example.currencyconverter;


import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface Api {



    @GET("latest")
    Call<JsonObject> getValue(@Query("base") String base);

    @Streaming
    @GET
    Call<ResponseBody> downloadFile(@Url String url);
}
