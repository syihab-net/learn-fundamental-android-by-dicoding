package com.ahmad.dicodingevent;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.ahmad.dicodingevent.databinding.ActivityMainBinding;
import com.ahmad.dicodingevent.ui.setting.SettingPreferences;
import com.ahmad.dicodingevent.ui.setting.SettingViewModel;
import com.ahmad.dicodingevent.ui.setting.SettingViewModelFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SettingPreferences preferences = SettingPreferences.getInstance(getApplicationContext());
        SettingViewModel settingViewModel = new ViewModelProvider(this, new SettingViewModelFactory(preferences))
                .get(SettingViewModel.class);

        settingViewModel.getThemeSettings().observe(this, isDark -> {
            if (isDark) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

}