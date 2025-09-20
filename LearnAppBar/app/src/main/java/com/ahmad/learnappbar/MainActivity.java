package com.ahmad.learnappbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ahmad.learnappbar.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu1) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new MenuFragment())
                    .addToBackStack(null)
                    .commit();

            return true;
        } else if (item.getItemId() == R.id.menu2) {
            Intent menuIntent = new Intent(this, MenuActivity.class);
            startActivity(menuIntent);
            return true;
        } else {
            return true;
        }
    }
}