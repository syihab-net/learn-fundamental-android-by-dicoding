package com.ahmad.dicodingevent.ui.setting;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class SettingViewModel extends ViewModel {

    private final SettingPreferences preferences;

    private final MutableLiveData<Boolean> _theme = new MutableLiveData<>(false);

    public LiveData<Boolean> getThemeSettings() {
        return _theme;
    }

    private final CompositeDisposable disposables = new CompositeDisposable();

    public SettingViewModel(SettingPreferences preferences) {
        this.preferences = preferences;

        disposables.add(
                preferences.getThemeSetting()
                        .distinctUntilChanged()
                        .subscribe(_theme::postValue, throwable -> {
                        })
        );
    }

    public void saveThemeSetting(boolean isDarkModeActive) {
        disposables.add(
                preferences.saveThemeSetting(isDarkModeActive)
                        .subscribe(() -> {
                        }, throwable -> { /* handle error */ })
        );
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }
}