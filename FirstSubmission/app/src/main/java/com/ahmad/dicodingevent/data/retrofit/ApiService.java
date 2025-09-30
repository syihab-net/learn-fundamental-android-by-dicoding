package com.ahmad.dicodingevent.data.retrofit;

import com.ahmad.dicodingevent.data.response.DetailEventResponse;
import com.ahmad.dicodingevent.data.response.ListEventResponse;

import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {
    @GET("events")
    Call<ListEventResponse> getEvents(@Query("active") String active);

    @GET("events/{id}")
    Call<DetailEventResponse> getEvent(@Path("id") Integer id);
}
