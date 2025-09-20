package com.ahmad.learnappbar;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ahmad.learnappbar.databinding.ActivityMenuBinding;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMenuBinding binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.searchView.setupWithSearchBar(binding.searchBar);
        binding.searchView.getEditText().setOnEditorActionListener((v, actionId, event) -> {
            CharSequence query = binding.searchView.getText();
            binding.searchBar.setText(query);
            binding.searchView.hide();

            Toast.makeText(this, query, Toast.LENGTH_SHORT).show();
            return true;
        });
    }
}