package com.macaria.app.ui.homeScreen;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.macaria.app.R;
import com.macaria.app.databinding.FragmentHomeScreenBinding;

public class HomeScreenFragment extends Fragment {
    private FragmentHomeScreenBinding binding;
    private NavHostFragment navHostFragment ;
    private NavController navController;


    public HomeScreenFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeScreenBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        navHostFragment =
                (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }
        NavigationUI.setupWithNavController(binding.mainBottomNavView, navController);
    }


}