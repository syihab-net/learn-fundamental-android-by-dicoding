package com.ahmad.dicodingevent.data.remote.retrofit;

import com.ahmad.dicodingevent.data.remote.response.EventListResponse;

import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {
    @GET("events")
    Call<EventListResponse> getEvents(@Query("active") String active);
}
