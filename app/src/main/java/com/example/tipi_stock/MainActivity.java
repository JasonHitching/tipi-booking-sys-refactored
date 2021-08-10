package com.example.tipi_stock;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.View;

import com.example.tipi_stock.databinding.ActivityMainBinding;
import com.example.tipi_stock.ui.bookings.SharedBookingViewModel;
import com.example.tipi_stock.ui.bookings.booking.BookingAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    // ViewModel available to all fragments hosted by this main activity.
    private SharedBookingViewModel bookingViewModel;
    private static BookingAdapter bookingAdapter;
    BottomNavigationView navView;
    private FirebaseAuth firebaseAuthenticator;

    PowerManager.WakeLock sysWakeLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navView = findViewById(R.id.nav_view);

        firebaseAuthenticator = FirebaseAuth.getInstance();

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.home_nav, R.id.booking_nav, R.id.inventory_nav, R.id.logout_nav)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        bookingViewModel = new ViewModelProvider(this).get(SharedBookingViewModel.class);

        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        sysWakeLock = powerManager.newWakeLock(PowerManager.PROXIMITY_SCREEN_OFF_WAKE_LOCK, "Tipi:tipitWakeLock");
    }

    // Function for hiding bottom navigation when required
    public void hideNavigation(boolean hide) {
        if (hide == true) {
            this.getSupportActionBar().hide();
            navView.setVisibility(View.GONE);
        } else {
            this.getSupportActionBar().show();
            navView.setVisibility(View.VISIBLE);
        }
    }

}