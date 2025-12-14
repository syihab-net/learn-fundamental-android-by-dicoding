package com.ahmad.dicodingevent.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmad.dicodingevent.data.local.entity.EventEntity;
import com.ahmad.dicodingevent.databinding.ItemRowEventBinding;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    private final List<EventEntity> listEventItems;

    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    public EventsAdapter(List<EventEntity> listEventItems) {
        this.listEventItems = listEventItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRowEventBinding binding = ItemRowEventBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EventEntity eventItem = listEventItems.get(position);

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date start, end;
        try {
            start = inputFormat.parse(eventItem.getBeginTime());
            end = inputFormat.parse(eventItem.getEndTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM yyyy", new Locale("id", "ID"));
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH.mm", Locale.getDefault());

        String datePart = dateFormat.format(start);
        String startTime = timeFormat.format(start);
        String endTimeFormatted = timeFormat.format(end);

        String finalResult = datePart + " pukul " + startTime + " - " + endTimeFormatted;

        holder.binding.tvName.setText(eventItem.getName());
        holder.binding.tvDateAndOwner.setText(String.format("%s By %s", finalResult, eventItem.getOwnerName()));
        Glide.with(holder.binding.getRoot())
                .load(eventItem.getImageLogo())
                .into(holder.binding.imageIcon);

        holder.binding.cardView.setOnClickListener(v -> onItemClickCallback.onItemClicked(listEventItems.get(position)));
    }

    @Override
    public int getItemCount() {
        return listEventItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemRowEventBinding binding;

        public ViewHolder(@NonNull ItemRowEventBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(EventEntity eventItem);
    }
}
