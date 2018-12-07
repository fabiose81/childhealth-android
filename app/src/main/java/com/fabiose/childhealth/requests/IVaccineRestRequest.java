package com.fabiose.childhealth.requests;

import com.fabiose.childhealth.domains.Vaccine;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.ArrayList;

public interface IVaccineRestRequest {

    @GET("api/vaccine/findAll")
    Call<ArrayList<Vaccine>> findAll();

    @GET("api/vaccine/findById/")
    Call<ResponseBody> findById(@Query("id") Integer id);

    @POST("api/vaccine/save")
    Call<ResponseBody> save(@Body Vaccine vaccine);

    @POST("api/vaccine/delete")
    Call<ResponseBody> delete(@Body Vaccine vaccine);

    @PUT("api/vaccine/update")
    Call<ResponseBody> update(@Body Vaccine vaccine);

}
