package com.ahmad.myquote;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.ahmad.myquote.databinding.ItemQuoteBinding;

import java.util.ArrayList;

public class QuoteAdapter extends RecyclerView.Adapter<QuoteAdapter.ListViewHolder> {

    private ArrayList<String> listQuote;

    public QuoteAdapter (ArrayList<String> listQuote) {
        this.listQuote = listQuote;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemQuoteBinding binding = ItemQuoteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.binding.tvItem.setText(listQuote.get(position));
    }

    @Override
    public int getItemCount() {
        return listQuote.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        ItemQuoteBinding binding;
        public ListViewHolder(@NonNull ItemQuoteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
