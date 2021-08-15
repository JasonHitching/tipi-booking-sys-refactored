package com.example.tipi_stock.ui.bookings.booking_forms;

import android.os.Bundle;
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

import java.util.Objects;

public class BookingEditFragment extends Fragment {

    private SharedBookingViewModel bookingViewModel;
    private Button button222;

    @NonNull
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.booking_edit_fragment, null);
        bookingViewModel = new ViewModelProvider(requireActivity()).get(SharedBookingViewModel.class);
        TextInputEditText structureText = rootView.findViewById(R.id.structure_type_text);
        button222 = rootView.findViewById(R.id.submit_form_button);
        return rootView;
    }

    @Override
    public final void onViewCreated(@NonNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        Booking booking = bookingViewModel.getBooking(Objects.requireNonNull(getArguments()).getInt("position"));

        button222.setOnClickListener(view1 -> {
            booking.setStructureType("STRUCTTTTTT");
            bookingViewModel.updateBooking(booking);
        });
    }
}
