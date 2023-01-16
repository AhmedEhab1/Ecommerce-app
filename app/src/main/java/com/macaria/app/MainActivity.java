package com.macaria.app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.transition.Fade;
import androidx.transition.Transition;

import androidx.transition.TransitionManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.macaria.app.data.Constants;
import com.macaria.app.databinding.ActivityMainBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements NavController.OnDestinationChangedListener{
    private ActivityMainBinding binding ;
    private NavHostFragment navHostFragment ;
    private NavController navController;
    private AppBarConfiguration mAppBarConfiguration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        init();
    }

    private void init() {
        saveDeviceID();
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.splashFragment)
                .build();

        navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }
        NavigationUI.setupWithNavController(binding.mainBottomNavView, navController);
        navController.addOnDestinationChangedListener(this);
    }

    @Override
    public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
        if (destination.getId() == R.id.homeFragment ||destination.getId() == R.id.favoriteFragment ||destination.getId() == R.id.profileFragment || destination.getId() == R.id.categoriesFragment) {
            hideBottomNavBar(true);
        } else {
            hideBottomNavBar(false);
        }
    }

    private void hideBottomNavBar(boolean show) {
        Transition transition = new Fade();
        transition.setDuration(400);
        transition.addTarget(binding.navLayout);
        TransitionManager.beginDelayedTransition(binding.navLayout, transition);
        binding.navLayout.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void saveDeviceID(){
        String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        Constants.DEVICE_ID = androidId ;
        Log.d("DEVICE_ID", "saveDeviceID: "+androidId);
    }
}