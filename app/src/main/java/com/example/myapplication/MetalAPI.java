package com.example.myapplication;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MetalAPI {
    @GET("latest?")
    Call<JsonObject> getPrices(@Query("access_key") String apiKey, @Query("base") String base, @Query("symbols") String symbols);
}
