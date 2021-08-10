package com.example.tipi_stock.ui.bookings.booking_forms;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tipi_stock.R;
import com.example.tipi_stock.backend.bookings.data.Booking;
import com.example.tipi_stock.ui.bookings.SharedBookingViewModel;
import com.google.android.material.textfield.TextInputEditText;

import static android.content.ContentValues.TAG;

public class BookingEditFragment extends Fragment {

    private SharedBookingViewModel bookingViewModel;
    private TextInputEditText structureText;
    private Button button222;

    private View rootView;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.booking_edit_fragment, null);
        bookingViewModel = new ViewModelProvider(requireActivity()).get(SharedBookingViewModel.class);
        structureText = rootView.findViewById(R.id.structure_type_text);
        button222 = rootView.findViewById(R.id.submit_form_button);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        Booking booking = bookingViewModel.getBooking(getArguments().getInt("position"));

        button222.setOnClickListener(view1 -> {
            booking.setStructureType("STRUCTTTTTT");
            bookingViewModel.updateBooking(booking);
        });

        Log.d(TAG, "onViewCreated: " + getArguments().getInt("position"));
    }
}
