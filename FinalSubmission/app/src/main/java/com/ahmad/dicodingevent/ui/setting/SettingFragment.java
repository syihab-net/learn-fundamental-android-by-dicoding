package com.ahmad.dicodingevent.ui.setting;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmad.dicodingevent.databinding.FragmentSettingBinding;

public class SettingFragment extends Fragment {

    private FragmentSettingBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SettingPreferences preferences = SettingPreferences.getInstance(requireActivity().getApplicationContext());
        SettingViewModel settingViewModel = new ViewModelProvider(this, new SettingViewModelFactory(preferences))
                .get(SettingViewModel.class);

        settingViewModel
                .getThemeSettings()
                .observe(getActivity(), isDark -> binding.switchDarkMode.setChecked(isDark));

        binding.switchDarkMode
                .setOnCheckedChangeListener((buttonView, isChecked) -> settingViewModel.saveThemeSetting(isChecked));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}