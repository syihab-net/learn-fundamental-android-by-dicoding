package com.ahmad.dicodingevent.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmad.dicodingevent.R;
import com.ahmad.dicodingevent.adapter.EventsAdapter;
import com.ahmad.dicodingevent.adapter.EventsCarouselAdapter;
import com.ahmad.dicodingevent.data.response.Event;
import com.ahmad.dicodingevent.databinding.FragmentHomeBinding;
import com.ahmad.dicodingevent.ui.detail.DetailActivity;

import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        HomeViewModel homeViewModel = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory()).get(HomeViewModel.class);

        binding.rvUpcoming.setLayoutManager(new LinearLayoutManager(requireContext(),
                RecyclerView.HORIZONTAL, false));
        binding.rvFinished.setLayoutManager(new LinearLayoutManager(requireContext()));

        homeViewModel.getListUpcomingEvent().observe(getViewLifecycleOwner(), this::setListUpcomingEventData);
        homeViewModel.getListFinishedEvent().observe(getViewLifecycleOwner(), this::setListFinishedEventData);
        homeViewModel.isLoadingUpcoming().observe(getViewLifecycleOwner(),
                isLoading -> showLoading(isLoading, binding.progressBarUpcoming));
        homeViewModel.isLoadingFinished().observe(getViewLifecycleOwner(),
                isLoading -> showLoading(isLoading, binding.progressBarFinished));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setListUpcomingEventData(List<Event> listEventsItems) {
        if (listEventsItems == null) {
            binding.tvUpcomingMessage.setText(R.string.failed_to_fetch);
            binding.tvUpcomingMessage.setVisibility(View.VISIBLE);
            binding.rvUpcoming.setVisibility(View.GONE);
            return;
        }

        if (listEventsItems.isEmpty()) {
            binding.tvUpcomingMessage.setText(R.string.no_event);
            binding.tvUpcomingMessage.setVisibility(View.VISIBLE);
            binding.rvUpcoming.setVisibility(View.GONE);
        } else {
            if (listEventsItems.size() > 5) {
                listEventsItems = listEventsItems.subList(0, 5);
            }

            EventsCarouselAdapter adapter = new EventsCarouselAdapter(listEventsItems);
            binding.rvUpcoming.setAdapter(adapter);

            adapter.setOnItemClickCallback(event -> {
                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_EVENT_ID, event.getId());
                startActivity(intent);
            });
        }
    }

    private void setListFinishedEventData(List<Event> listEventsItems) {
        if (listEventsItems == null) {
            binding.tvFinishedMessage.setText(R.string.failed_to_fetch);
            binding.tvFinishedMessage.setVisibility(View.VISIBLE);
            binding.rvFinished.setVisibility(View.GONE);
            return;
        }

        if (listEventsItems.isEmpty()) {
            binding.tvFinishedMessage.setText(R.string.no_event);
            binding.tvFinishedMessage.setVisibility(View.VISIBLE);
            binding.rvFinished.setVisibility(View.GONE);
        } else {
            if (listEventsItems.size() > 5) {
                listEventsItems = listEventsItems.subList(0, 5);
            }

            EventsAdapter adapter = new EventsAdapter(listEventsItems);
            binding.rvFinished.setAdapter(adapter);

            adapter.setOnItemClickCallback(event -> {
                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_EVENT_ID, event.getId());
                startActivity(intent);
            });
        }
    }

    private void showLoading(Boolean isLoading, ProgressBar progressBar) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}