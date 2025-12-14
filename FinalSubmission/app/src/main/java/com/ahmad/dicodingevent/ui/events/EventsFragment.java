package com.ahmad.dicodingevent.ui.events;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
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
import com.ahmad.dicodingevent.data.Result;
import com.ahmad.dicodingevent.data.local.entity.EventEntity;
import com.ahmad.dicodingevent.databinding.FragmentEventsBinding;
import com.ahmad.dicodingevent.helper.ViewModelFactory;
import com.ahmad.dicodingevent.ui.detail.DetailActivity;

import java.util.List;

public class EventsFragment extends Fragment {
    private FragmentEventsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentEventsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.rvEvents.setLayoutManager(new LinearLayoutManager(requireContext()));

        String eventType = "1";
        Bundle args = getArguments();
        if (args != null) {
            eventType = args.getString("eventType", "1");
        }

        if (eventType.equals("1")) {
            binding.tvHeading.setText(R.string.label_upcoming_events);
        } else if (eventType.equals("0")) {
            binding.tvHeading.setText(R.string.label_finished_events);
        } else {
            binding.tvHeading.setText(R.string.label_favorite_events);
        }

        ViewModelFactory factory = new ViewModelFactory(getActivity(), eventType);
        EventsViewModel eventsViewModel = new ViewModelProvider(this, factory).get(EventsViewModel.class);

        eventsViewModel.getAllEvents().observe(getViewLifecycleOwner(), this::setListEventData);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @SuppressLint("DefaultLocale")
    private void setListEventData(Result result) {
        if (result == null) {
            binding.tvEmpty.setText(R.string.failed_to_fetch);
            binding.tvEmpty.setVisibility(View.VISIBLE);
            binding.rvEvents.setVisibility(View.GONE);
        }

        if (result instanceof Result.Loading) {
            showLoading(true);
        } else if (result instanceof Result.Success) {
            showLoading(false);
            List<EventEntity> events = ((Result.Success<List<EventEntity>>) result).getData();
            if (events.isEmpty()) {
                binding.tvEmpty.setText(R.string.no_events_available);
                binding.tvEmpty.setVisibility(View.VISIBLE);
                binding.rvEvents.setVisibility(View.GONE);
            } else {
                binding.tvEmpty.setVisibility(View.GONE);
                binding.rvEvents.setVisibility(View.VISIBLE);

                EventsAdapter adapter = new EventsAdapter(events);
                binding.rvEvents.setAdapter(adapter);

                adapter.setOnItemClickCallback(event -> {
                    Intent intent = new Intent(getContext(), DetailActivity.class);
                    intent.putExtra(DetailActivity.EXTRA_EVENT_ID, event.getId());
                    startActivity(intent);
                });
            }
        } else if (result instanceof Result.Error) {
            showLoading(false);
            binding.tvEmpty.setText(String.format("%d%s", R.string.failed_to_fetch, ((Result.Error) result).getError()));
            binding.tvEmpty.setVisibility(View.VISIBLE);
            binding.rvEvents.setVisibility(View.GONE);
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