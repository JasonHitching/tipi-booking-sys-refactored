package com.example.tipi_stock;

import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.View;

import com.example.tipi_stock.databinding.ActivityMainBinding;
import com.example.tipi_stock.ui.bookings.SharedBookingViewModel;
import com.example.tipi_stock.ui.bookings.booking.BookingAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navView;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.tipi_stock.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navView = findViewById(R.id.nav_view);

        FirebaseAuth firebaseAuthenticator = FirebaseAuth.getInstance();

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.home_nav, R.id.booking_nav, R.id.inventory_nav, R.id.logout_nav)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // ViewModel available to all fragments hosted by this main activity.
        SharedBookingViewModel bookingViewModel = new ViewModelProvider(this).get(SharedBookingViewModel.class);

        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
    }

    // Function for hiding bottom navigation when required
    public final void hideNavigation(boolean hide) {
        if (hide) {
            Objects.requireNonNull(this.getSupportActionBar()).hide();
            navView.setVisibility(View.GONE);
        } else {
            Objects.requireNonNull(this.getSupportActionBar()).show();
            navView.setVisibility(View.VISIBLE);
        }
    }

}