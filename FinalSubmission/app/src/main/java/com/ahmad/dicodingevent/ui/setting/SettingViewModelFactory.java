package com.ahmad.dicodingevent.ui.setting;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class SettingViewModelFactory implements ViewModelProvider.Factory {

    private final SettingPreferences preferences;

    public SettingViewModelFactory(SettingPreferences preferences) {
        this.preferences = preferences;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SettingViewModel.class)) {
            return (T) new SettingViewModel(preferences);
        }

        throw new IllegalArgumentException("Unknown view model class");
    }
}
