package com.ahmad.dicodingevent.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.ahmad.dicodingevent.data.local.entity.EventEntity;
import com.ahmad.dicodingevent.data.local.entity.EventRemoteUpdate;
import com.ahmad.dicodingevent.data.local.room.AppRoomDatabase;
import com.ahmad.dicodingevent.data.local.room.EventDao;
import com.ahmad.dicodingevent.data.remote.response.EventItem;
import com.ahmad.dicodingevent.data.remote.response.EventListResponse;
import com.ahmad.dicodingevent.data.remote.retrofit.ApiConfig;
import com.ahmad.dicodingevent.data.remote.retrofit.ApiService;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventRepository {
    private final ApiService apiService;
    private final EventDao eventDao;
    private final ExecutorService executorService;

    private final MediatorLiveData<Result> result = new MediatorLiveData<>();

    public EventRepository(Context context) {
        AppRoomDatabase db = AppRoomDatabase.getInstance(context);

        this.eventDao = db.eventDao();
        this.apiService = ApiConfig.getApiService();
        this.executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<Result> getAllEvents(String eventType) {
        result.setValue(new Result.Loading());

        if (eventType.equals("2")) {
            LiveData<List<EventEntity>> favoriteEvents = eventDao.getFavoriteEvents();
            result.addSource(favoriteEvents, events -> result.setValue(new Result.Success<>(events)));

            return result;
        }

        Call<EventListResponse> client = apiService.getEvents(eventType);

        client.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<EventListResponse> call, @NonNull Response<EventListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<EventItem> eventList = response.body().getListEvents();

                    executorService.execute(() -> {
                        for (EventItem eventItem : eventList) {
                            EventEntity eventEntity = new EventEntity(eventItem);
                            eventEntity.setActive(eventType.equals("1"));

                            if (eventDao.exists(eventItem.getId())) {
                                eventDao.update(new EventRemoteUpdate(eventEntity));
                            } else {
                                eventDao.insert(eventEntity);
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(@NonNull Call<EventListResponse> call, @NonNull Throwable t) {
                result.setValue(new Result.Error(t.getMessage()));
            }
        });

        LiveData<List<EventEntity>> localData = eventDao.getAllEvents(eventType.equals("1"));
        result.addSource(localData, events -> {
            if (!events.isEmpty()) {
                result.setValue(new Result.Success<>(events));
            }
        });

        return result;
    }

    public LiveData<EventEntity> getDetailEvent(int id) {
        return eventDao.getEventById(id);
    }

    public void update(EventEntity event) {
        executorService.execute(() -> eventDao.update(event));
    }
}
