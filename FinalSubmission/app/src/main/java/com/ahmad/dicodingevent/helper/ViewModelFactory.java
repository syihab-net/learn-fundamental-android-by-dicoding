package com.ahmad.dicodingevent.helper;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ahmad.dicodingevent.ui.detail.DetailViewModel;
import com.ahmad.dicodingevent.ui.events.EventsViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final Context context;
    private final String eventType;

    public ViewModelFactory(Context context, String eventType) {
        this.context = context;
        this.eventType = eventType;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(EventsViewModel.class)) {
            return (T) new EventsViewModel(context, eventType);
        } else if (modelClass.isAssignableFrom(DetailViewModel.class)) {
            return (T) new DetailViewModel(context);
        }

        throw new IllegalArgumentException("Unknown viewModel class");
    }
}
