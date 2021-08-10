package com.example.tipi_stock.ui.bookings.booking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.tipi_stock.R;
import com.example.tipi_stock.ui.bookings.SharedBookingViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static android.content.ContentValues.TAG;

/**
 * Booking fragment displays the booking recycler and existing bookings from the Room database
 *
 * Select functionality from Android Jetpack library classes are utilised
 * to achieve some of the required functionality for this class:
 *      https://developer.android.com/reference/androidx/fragment/app/Fragment
 */
public class BookingFragment extends Fragment implements BookingAdapter.OnBookingClickListener {

    private static BookingAdapter bookingAdapter;
    private static RecyclerView bookingRecycler;
    private SharedBookingViewModel sharedBookingViewModel;
    private Button sortButton;
    private View rootView;
    private MaterialToolbar topBar;
    Bundle dataBundle;


    // Required empty constructor
    public BookingFragment() {
        dataBundle = new Bundle();
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = inflater.inflate(R.layout.booking_fragment, null);
        sharedBookingViewModel = new ViewModelProvider(this).get(SharedBookingViewModel.class);

        // Hide default action bar for this fragment
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        bookingRecycler = rootView.findViewById(R.id.booking_recycler);
        topBar = rootView.findViewById(R.id.top_app_bar);
        bookingAdapter = new BookingAdapter(getActivity(), this);
        bookingRecycler.setHasFixedSize(true);
        bookingRecycler.setAdapter(bookingAdapter);
        bookingRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        sharedBookingViewModel = new ViewModelProvider(requireActivity()).get(SharedBookingViewModel.class);



        // Add a live data observer
        sharedBookingViewModel.getAllBookings().observe(getViewLifecycleOwner(), bookings -> bookingAdapter.setBookings(bookings));

        FloatingActionButton newBookingFab = rootView.findViewById(R.id.new_booking_fab);

        newBookingFab.setOnClickListener(thisView -> {
            dataBundle.clear();
            dataBundle.putString("type", "new");
            NavHostFragment.findNavController(this).navigate(
                    R.id.action_booking_nav_to_bookingFormFragment, dataBundle);
        });

        // Set on click listener for handling toolbar menu clicks
        topBar.setOnMenuItemClickListener(clickedItem -> {
            switch (clickedItem.getItemId()) {
                case R.id.ascending_item:
                    sharedBookingViewModel.sortDateAscending();
                    bookingAdapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "Bookings sorted date ascending!",
                            Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.descending_item:
                    sharedBookingViewModel.sortDateDescending();
                    bookingAdapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "Bookings sorted date descending!",
                            Toast.LENGTH_SHORT).show();
                    return true;
                default:
                    return super.onOptionsItemSelected(clickedItem);
            }
        });
    }

    /**
     *
     * @param position
     */
    @Override
    public void onBookingClicked(int position) {
        Log.d(TAG, "onBookingClicked: " + position);
        dataBundle.clear();
        dataBundle.putInt("position", position);
        dataBundle.putString("type", "edit");
        NavHostFragment.findNavController(this).navigate(
                R.id.action_booking_nav_to_bookingFormFragment, dataBundle);
    }
}