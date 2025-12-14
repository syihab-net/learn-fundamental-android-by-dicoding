package com.ahmad.dicodingevent.ui.detail;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ahmad.dicodingevent.data.EventRepository;
import com.ahmad.dicodingevent.data.local.entity.EventEntity;

public class DetailViewModel extends ViewModel {
    private final EventRepository eventRepository;

    public DetailViewModel(Context context) {
        this.eventRepository = new EventRepository(context);
    }

    public LiveData<EventEntity> getDetailEvent(int id) {
        return eventRepository.getDetailEvent(id);
    }

    public void updateEvent(EventEntity event) {
        eventRepository.update(event);
    }
}
