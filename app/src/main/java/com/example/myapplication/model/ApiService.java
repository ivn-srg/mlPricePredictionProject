package com.example.myapplication.model;

import com.example.myapplication.model.DollarRate;
import com.example.myapplication.model.MolybdenumRate;
import com.example.myapplication.model.CobaltRate;
import com.example.myapplication.model.NickelRate;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("dollar")
    Call<DollarRate> getDollarRate();

    @GET("molybdenum")
    Call<MolybdenumRate> getMolybdenumRate();

    @GET("cobalt")
    Call<CobaltRate> getCobaltRate();

    @GET("nickel")
    Call<NickelRate> getNickelRate();
}
