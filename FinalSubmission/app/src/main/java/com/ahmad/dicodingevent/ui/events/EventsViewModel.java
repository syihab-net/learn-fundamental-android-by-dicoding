package com.ahmad.dicodingevent.ui.events;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ahmad.dicodingevent.data.EventRepository;
import com.ahmad.dicodingevent.data.Result;

public class EventsViewModel extends ViewModel {
    private final String eventType;
    private final EventRepository eventRepository;

    public EventsViewModel(Context context, String eventType) {
        this.eventType = eventType;
        this.eventRepository = new EventRepository(context);
    }

    public LiveData<Result> getAllEvents() {
        return eventRepository.getAllEvents(eventType);
    }
}