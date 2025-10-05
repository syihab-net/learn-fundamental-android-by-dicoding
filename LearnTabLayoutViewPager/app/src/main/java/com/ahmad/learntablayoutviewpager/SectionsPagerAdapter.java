package com.ahmad.learntablayoutviewpager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class SectionsPagerAdapter extends FragmentStateAdapter {
    public SectionsPagerAdapter (AppCompatActivity activity) {
        super(activity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new HomeFragment();
            case 1:
                fragment = new ProfileFragment();
            default:
                fragment = new HomeFragment();
        }

        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
