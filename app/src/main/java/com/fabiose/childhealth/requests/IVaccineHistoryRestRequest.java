package com.fabiose.childhealth.requests;

import com.fabiose.childhealth.domains.VaccineHistory;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.ArrayList;

public interface IVaccineHistoryRestRequest {

    @GET("api/vaccine-history/findAll")
    Call<ArrayList<VaccineHistory>> findAll();

    @GET("api/vaccine-history/findById/")
    Call<ResponseBody> findById(@Query("id") Integer id);

    @GET("api/vaccine-history/findByChild/{id}")
    Call<ResponseBody> findByChild(@Path("id") Integer id);

    @POST("api/vaccine-history/save")
    Call<ResponseBody> save(@Body VaccineHistory vaccineHistory);

    @POST("api/vaccine-history/delete")
    Call<ResponseBody> delete(@Body VaccineHistory vaccineHistory);

    @PUT("api/vaccine-history/update")
    Call<ResponseBody> update(@Body VaccineHistory vaccineHistory);

}
