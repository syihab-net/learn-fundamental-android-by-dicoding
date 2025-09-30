package com.ahmad.dicodingevent.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmad.dicodingevent.data.response.Event;
import com.ahmad.dicodingevent.databinding.ItemRowEventBinding;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    private final List<Event> listEvents;

    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public EventsAdapter(List<Event> listEvents) {
        this.listEvents = listEvents;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRowEventBinding binding = ItemRowEventBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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

        holder.binding.tvName.setText(event.getName());
        holder.binding.tvDateAndOwner.setText(String.format("%s By %s", finalResult, event.getOwnerName()));
        Glide.with(holder.binding.getRoot())
                .load(event.getImageLogo())
                .into(holder.binding.imageIcon);

        holder.binding.cardView.setOnClickListener(v -> onItemClickCallback.onItemClicked(listEvents.get(position)));
    }

    @Override
    public int getItemCount() {
        return listEvents.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemRowEventBinding binding;

        public ViewHolder(@NonNull ItemRowEventBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(Event event);
    }
}
