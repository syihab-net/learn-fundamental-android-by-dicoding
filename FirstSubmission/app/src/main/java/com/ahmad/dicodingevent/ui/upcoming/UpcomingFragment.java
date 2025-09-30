package com.ahmad.dicodingevent.ui.upcoming;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmad.dicodingevent.R;
import com.ahmad.dicodingevent.adapter.EventsAdapter;
import com.ahmad.dicodingevent.data.response.Event;
import com.ahmad.dicodingevent.databinding.FragmentUpcomingBinding;
import com.ahmad.dicodingevent.ui.detail.DetailActivity;

import java.util.List;

public class UpcomingFragment extends Fragment {

    private FragmentUpcomingBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentUpcomingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        UpcomingViewModel upcomingViewModel = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory()).get(UpcomingViewModel.class);

        binding.rvEvents.setLayoutManager(new LinearLayoutManager(requireContext()));

        upcomingViewModel.getListEvent().observe(getViewLifecycleOwner(), this::setListEventData);
        upcomingViewModel.isLoading().observe(getViewLifecycleOwner(), this::showLoading);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private void setListEventData(List<Event> listEventsItems) {
        if (listEventsItems == null) {
            binding.tvEmpty.setText(R.string.failed_to_fetch);
            binding.tvEmpty.setVisibility(View.VISIBLE);
            binding.rvEvents.setVisibility(View.GONE);
            return;
        }

        if (listEventsItems.isEmpty()) {
            binding.tvEmpty.setText(R.string.no_event);
            binding.tvEmpty.setVisibility(View.VISIBLE);
            binding.rvEvents.setVisibility(View.GONE);
        } else {
            EventsAdapter adapter = new EventsAdapter(listEventsItems);
            binding.rvEvents.setAdapter(adapter);

            adapter.setOnItemClickCallback(event -> {
                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_EVENT_ID, event.getId());
                startActivity(intent);
            });
        }
    }

    private void showLoading(Boolean isLoading) {
        if (isLoading) {
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.progressBar.setVisibility(View.GONE);
        }
    }

}