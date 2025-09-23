package com.ahmad.restaurantreview.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ahmad.restaurantreview.R;
import com.ahmad.restaurantreview.data.response.CustomerReviewsItem;
import com.ahmad.restaurantreview.data.response.PostReviewResponse;
import com.ahmad.restaurantreview.data.response.Restaurant;
import com.ahmad.restaurantreview.data.response.RestaurantResponse;
import com.ahmad.restaurantreview.data.retrofit.ApiConfig;
import com.ahmad.restaurantreview.databinding.ActivityMainBinding;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private static final String TAG = "MainActivity";
    private static final String RESTAURANT_ID = "s1knt6za9kkfw1e867";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        MainViewModel mainViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory()).get(MainViewModel.class);
        mainViewModel.getRestaurant().observe(this, this::setRestaurantData);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvReview.setLayoutManager(layoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        binding.rvReview.addItemDecoration(itemDecoration);

        mainViewModel.getListReview().observe(this, this::setReviewData);
        mainViewModel.isLoading().observe(this, this::showLoading);

        binding.btnSend.setOnClickListener(view -> {
            if (binding.edReview.getText() != null) {
                mainViewModel.postReview(binding.edReview.getText().toString());
            }

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        });
    }



    private void setRestaurantData(Restaurant restaurant) {
        binding.tvTitle.setText(restaurant.getName());
        binding.tvDescription.setText(restaurant.getDescription());
        Glide.with(MainActivity.this).
                load("https://restaurant-api.dicoding.dev/images/large/" + restaurant.getPictureId())
                .into(binding.ivPicture);
    }
    private void setReviewData(java.util.List<CustomerReviewsItem> consumerReviews) {
        ArrayList<String> listReview = new ArrayList<>();
        for (CustomerReviewsItem review : consumerReviews) {
            listReview.add(review.getReview() + "\n- " + review.getName());
        }
        ReviewAdapter adapter = new ReviewAdapter(listReview);
        binding.rvReview.setAdapter(adapter);
        binding.edReview.setText("");
    }

    private void showLoading (Boolean isLoading) {
        if (isLoading) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.GONE);
        }
    }
}