package com.ahmad.dicodingevent.ui.setting;

import androidx.annotation.NonNull;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class SettingPreferences {

    private final RxDataStore<Preferences> dataStore;

    private static final Preferences.Key<Boolean> THEME_KEY =
            PreferencesKeys.booleanKey("theme_setting");

    private static volatile SettingPreferences INSTANCE;

    private SettingPreferences(@NonNull RxDataStore<Preferences> dataStore) {
        this.dataStore = dataStore;
    }

    public static SettingPreferences getInstance(@NonNull android.content.Context context) {
        if (INSTANCE == null) {
            synchronized (SettingPreferences.class) {
                if (INSTANCE == null) {
                    RxDataStore<Preferences> ds =
                            new RxPreferenceDataStoreBuilder(context, "settings").build();
                    INSTANCE = new SettingPreferences(ds);
                }
            }
        }
        return INSTANCE;
    }

    public Flowable<Boolean> getThemeSetting() {
        return dataStore.data()
                .map(prefs -> {
                    Boolean v = prefs.get(THEME_KEY);
                    return v != null ? v : Boolean.FALSE;
                });
    }

    public Completable saveThemeSetting(boolean isDarkModeActive) {
        return dataStore.updateDataAsync(prefsIn -> {
            MutablePreferences mutable = prefsIn.toMutablePreferences();
            mutable.set(THEME_KEY, isDarkModeActive);
            return Single.just(mutable);
        }).ignoreElement();
    }
}
