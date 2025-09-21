package com.ahmad.restaurantreview.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmad.restaurantreview.databinding.ItemReviewBinding;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private final ArrayList<String> listReview;
    public ReviewAdapter(ArrayList<String> listReview) {
        this.listReview = listReview;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemReviewBinding itemReviewBinding = ItemReviewBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(itemReviewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.tvItem.setText(listReview.get(position));
    }

    @Override
    public int getItemCount() {
        return listReview.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemReviewBinding binding;
        public ViewHolder(@NonNull ItemReviewBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
