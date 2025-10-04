package com.ahmad.learnnavigation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahmad.learnnavigation.databinding.FragmentDetailCategoryBinding;

public class DetailCategoryFragment extends Fragment {

    private FragmentDetailCategoryBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDetailCategoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String dataName = DetailCategoryFragmentArgs.fromBundle(requireArguments()).getName();
        Long dataStock = DetailCategoryFragmentArgs.fromBundle(requireArguments()).getStock();

        binding.tvCategoryName.setText(dataName);
        binding.tvCategoryDescription.setText(String.format("Stock : %s", dataStock));

        binding.btnHome.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_detailCategoryFragment_to_homeFragment));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}