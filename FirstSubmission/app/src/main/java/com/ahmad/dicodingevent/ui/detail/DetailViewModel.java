package com.ahmad.dicodingevent.ui.detail;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ahmad.dicodingevent.data.response.DetailEventResponse;
import com.ahmad.dicodingevent.data.response.Event;
import com.ahmad.dicodingevent.data.retrofit.ApiConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailViewModel extends ViewModel {
    private final MutableLiveData<Event> _event = new MutableLiveData<>();

    public LiveData<Event> getEvent() {
        return _event;
    }

    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();

    public LiveData<Boolean> isLoading() {
        return _isLoading;
    }

    public static final String TAG = "DetailViewModel";

    public void loadDetailEvent(Integer eventId) {
        _isLoading.setValue(true);
        Call<DetailEventResponse> client = ApiConfig.getApiService().getEvent(eventId);

        client.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<DetailEventResponse> call, @NonNull Response<DetailEventResponse> response) {
                _isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    _event.setValue(response.body().getEvent());
                } else {
                    _event.setValue(null);
                    assert response.body() != null;
                    Log.e(TAG, "onFailure: " + response.body().getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<DetailEventResponse> call, @NonNull Throwable t) {
                _isLoading.setValue(false);
                _event.setValue(null);
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}
