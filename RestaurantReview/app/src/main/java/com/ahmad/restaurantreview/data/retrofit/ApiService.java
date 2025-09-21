package com.ahmad.restaurantreview.data.retrofit;

import com.ahmad.restaurantreview.data.response.PostReviewResponse;
import com.ahmad.restaurantreview.data.response.RestaurantResponse;

import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {
    @GET("detail/{id}")
    Call<RestaurantResponse> getRestaurant(@Path("id") String id);

    @FormUrlEncoded
    @Headers({"Authorization: token 123"})
    @POST("review")
    Call<PostReviewResponse> postReview(
            @Field("id") String id,
            @Field("name") String name,
            @Field("review") String review
    );
}
