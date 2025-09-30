package com.ahmad.dicodingevent.ui.finished;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ahmad.dicodingevent.data.response.ListEventResponse;
import com.ahmad.dicodingevent.data.response.Event;
import com.ahmad.dicodingevent.data.retrofit.ApiConfig;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FinishedViewModel extends ViewModel {
    private final MutableLiveData<List<Event>> _listEvent = new MutableLiveData<>();

    public LiveData<List<Event>> getListEvent() {
        return _listEvent;
    }

    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();

    public LiveData<Boolean> isLoading() {
        return _isLoading;
    }

    public static final String TAG = "UpcomingViewModel";

    public FinishedViewModel() {
        loadListEvent();
    }

    private void loadListEvent() {
        _isLoading.setValue(true);
        Call<ListEventResponse> client = ApiConfig.getApiService().getEvents("0");

        client.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ListEventResponse> call, @NonNull Response<ListEventResponse> response) {
                _isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    _listEvent.setValue(response.body().getListEvents());
                } else {
                    _listEvent.setValue(null);
                    assert response.body() != null;
                    Log.e(TAG, "onFailure: " + response.body().getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ListEventResponse> call, @NonNull Throwable t) {
                _isLoading.setValue(false);
                _listEvent.setValue(null);
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}