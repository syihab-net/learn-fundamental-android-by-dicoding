package com.ahmad.dicodingevent.ui.home;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ahmad.dicodingevent.data.response.Event;
import com.ahmad.dicodingevent.data.response.ListEventResponse;
import com.ahmad.dicodingevent.data.retrofit.ApiConfig;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {
    private final MutableLiveData<List<Event>> _listUpcomingEvent = new MutableLiveData<>();
    private final MutableLiveData<List<Event>> _listFinishedEvent = new MutableLiveData<>();
    private final MutableLiveData<Boolean> _isLoadingUpcoming = new MutableLiveData<>();
    private final MutableLiveData<Boolean> _isLoadingFinished = new MutableLiveData<>();
    public static final String TAG = "HomeViewModel";

    public HomeViewModel() {
        loadListEventUpcoming();
        loadListEventFinished();
    }

    public LiveData<Boolean> isLoadingUpcoming() {
        return _isLoadingUpcoming;
    }

    public LiveData<Boolean> isLoadingFinished() {
        return _isLoadingFinished;
    }

    public LiveData<List<Event>> getListUpcomingEvent() {
        return _listUpcomingEvent;
    }

    public LiveData<List<Event>> getListFinishedEvent() {
        return _listFinishedEvent;
    }

    private void loadListEventUpcoming() {
        _isLoadingUpcoming.setValue(true);
        Call<ListEventResponse> client = ApiConfig.getApiService().getEvents("1");

        client.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ListEventResponse> call, @NonNull Response<ListEventResponse> response) {
                _isLoadingUpcoming.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    _listUpcomingEvent.setValue(response.body().getListEvents());
                } else {
                    _listUpcomingEvent.setValue(null);
                    Log.e(TAG, "onFailure: " + response.body().getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ListEventResponse> call, @NonNull Throwable t) {
                _isLoadingUpcoming.setValue(false);
                _listUpcomingEvent.setValue(null);
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void loadListEventFinished() {
        _isLoadingFinished.setValue(true);
        Call<ListEventResponse> client = ApiConfig.getApiService().getEvents("0");

        client.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ListEventResponse> call, @NonNull Response<ListEventResponse> response) {
                _isLoadingFinished.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    _listFinishedEvent.setValue(response.body().getListEvents());
                } else {
                    _listUpcomingEvent.setValue(null);
                    assert response.body() != null;
                    Log.e(TAG, "onFailure: " + response.body().getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ListEventResponse> call, @NonNull Throwable t) {
                _isLoadingFinished.setValue(false);
                _listFinishedEvent.setValue(null);
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}