package com.fabiose.childhealth.requests;

import com.fabiose.childhealth.domains.Child;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.ArrayList;

public interface IChildRestRequest {

    @GET("api/child/findAll")
    Call<ArrayList<Child>> findAll();

    @GET("api/child/findById/")
    Call<?> findById(@Query("id") Integer id);

    @POST("api/child/save")
    Call<ResponseBody> save(@Body Child child);

    @POST("api/child/delete")
    Call<ResponseBody> delete(@Body Child child);

    @PUT("api/child/update")
    Call<ResponseBody> update(@Body Child child);

}
