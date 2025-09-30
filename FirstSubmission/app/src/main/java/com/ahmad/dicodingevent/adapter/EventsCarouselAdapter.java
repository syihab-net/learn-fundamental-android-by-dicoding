package com.ahmad.dicodingevent.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmad.dicodingevent.data.response.Event;
import com.ahmad.dicodingevent.databinding.ItemCarouselEventBinding;
import com.ahmad.dicodingevent.databinding.ItemRowEventBinding;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EventsCarouselAdapter extends RecyclerView.Adapter<EventsCarouselAdapter.ViewHolder> {

    private final List<Event> listEvents;

    private EventsCarouselAdapter.OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(EventsCarouselAdapter.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public EventsCarouselAdapter(List<Event> listEvents) {
        this.listEvents = listEvents;
    }

    @NonNull
    @Override
    public EventsCarouselAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCarouselEventBinding binding = ItemCarouselEventBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new EventsCarouselAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsCarouselAdapter.ViewHolder holder, int position) {
        Event event = listEvents.get(position);

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date start, end;
        try {
            start = inputFormat.parse(event.getBeginTime());
            end = inputFormat.parse(event.getEndTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM yyyy", new Locale("id", "ID"));
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH.mm", Locale.getDefault());

        String datePart = dateFormat.format(start);
        String startTime = timeFormat.format(start);
        String endTimeFormatted = timeFormat.format(end);

        String finalResult = datePart + " pukul " + startTime + " - " + endTimeFormatted;

        holder.binding.tvTitle.setText(event.getName());
        holder.binding.tvDate.setText(finalResult);
        Glide.with(holder.binding.getRoot())
                .load(event.getImageLogo())
                .into(holder.binding.imageCover);

        holder.binding.getRoot().setOnClickListener(v -> onItemClickCallback.onItemClicked(listEvents.get(position)));
    }

    @Override
    public int getItemCount() {
        return listEvents.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemCarouselEventBinding binding;

        public ViewHolder(@NonNull ItemCarouselEventBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(Event event);
    }
}
