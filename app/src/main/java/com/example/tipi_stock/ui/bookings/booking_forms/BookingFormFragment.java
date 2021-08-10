package com.example.tipi_stock.ui.bookings.booking_forms;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.tipi_stock.R;
import com.example.tipi_stock.backend.bookings.data.Booking;
import com.example.tipi_stock.ui.bookings.SharedBookingViewModel;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static android.content.ContentValues.TAG;

/**
 * Fragment used to display an XML form for inputting a new structure booking
 *
 * Select functionality from Android Jetpack library classes are utilised
 * to achieve some of the required functionality for this class:
 *     https://developer.android.com/reference/androidx/fragment/app/Fragment
 */
public class BookingFormFragment extends Fragment {

    private View rootView;
    private MaterialDatePicker bookingDateSelector;
    private ImageButton calendarButton;
    private TextInputEditText dateText, structureText, firstNameText, lastNameText, costText, daysText,
                                firstLineAddress;
    private TextInputLayout dateTextLayout;
    private SharedBookingViewModel bookingViewModel;
    private Button submitButton;
    private String editAddFlag;
    private Booking selectedBooking;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = inflater.inflate(R.layout.booking_form_fragment, null);

        bookingDateSelector = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select booking date")
                .build();
        
        // Locate all input fields and assign them to relevant variables
        dateTextLayout = rootView.findViewById(R.id.desired_date);
        dateText = rootView.findViewById(R.id.selected_date);
        structureText = rootView.findViewById(R.id.structure_type_text);
        firstNameText = rootView.findViewById(R.id.customer_first_name);
        lastNameText = rootView.findViewById(R.id.customer_last_name);
        costText = rootView.findViewById(R.id.booking_cost);
        daysText = rootView.findViewById(R.id.num_days_text);
        firstLineAddress = rootView.findViewById(R.id.address_text);

        // Handle form buttons
        submitButton = rootView.findViewById(R.id.submit_form_button);
        calendarButton = rootView.findViewById(R.id.calander_button);

        // Reference the shared view model for both BookingFormFragment and BookingForm
        bookingViewModel = new ViewModelProvider(requireActivity()).get(SharedBookingViewModel.class);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        editAddFlag = getArguments().get("type").toString();

        // If currently the edit flag is in 'edit' mode
        if (editAddFlag.equals("edit")) {
            editFormData(getArguments().getInt("position"));
            submitButton.setOnClickListener(view1 -> {
                String hireDays = daysText.getText().toString();
                String cost = costText.getText().toString();

                if (checkDoubleNumeric(cost) && checkIntegerNumeric(hireDays)) {
                    selectedBooking.setStructureType(structureText.getText().toString());
                    selectedBooking.setCost(Double.parseDouble(costText.getText().toString()));
                    selectedBooking.setNumberOfDays(Integer.parseInt(daysText.getText().toString()));
                    selectedBooking.setCustomerFirstName(firstNameText.getText().toString());
                    selectedBooking.setCustomerLastName(lastNameText.getText().toString());
                    selectedBooking.setCustomerAddress(firstLineAddress.getText().toString());
                    bookingViewModel.updateBooking(selectedBooking);
                    NavHostFragment.findNavController(this).popBackStack();
                } else {
                    Toast.makeText(getActivity(), "Invalid cost or days input, is it a number?",
                            Toast.LENGTH_LONG).show();
                }
                
            });
        } else {
            Log.d(TAG, "onViewCreated: new booking mode");
            // On click listener monitoring the form submit button
            submitButton.setOnClickListener(submit -> {

                if (checkEmpty()) {
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM-d-yyyy");
                    String replaceCommas = dateText.getText().toString().replaceAll(", ", " ");

                    String result = bookingViewModel.createBooking(
                            Objects.requireNonNull(structureText.getText()).toString(),
                            Objects.requireNonNull(firstNameText.getText()).toString(),
                            Objects.requireNonNull(lastNameText.getText()).toString(),
                            Objects.requireNonNull(firstLineAddress.getText()).toString(),
                            Objects.requireNonNull(costText.getText()).toString(),
                            LocalDate.parse(replaceCommas.replace(" ", "-"), dateFormatter),
                            Objects.requireNonNull(daysText.getText()).toString());

                    // If the booking wasn't a success, display a dialog window with the reason
                    if (!result.equals("success")) {
                        // Obtain the view context to be used to display the dialog
                        Context viewContext = view.getRootView().getContext();

                        AlertDialog.Builder incorrectInput = new AlertDialog.Builder(viewContext);
                        incorrectInput.setMessage(result);
                        incorrectInput.setPositiveButton("Okay", (dialogInterface, i) -> {});
                        incorrectInput.create().show();
                    } else {
                        NavHostFragment.findNavController(this).popBackStack();
                    }
                } else {
                    Toast.makeText(getActivity(), "Empty fields, please fill all of them!",
                            Toast.LENGTH_LONG).show();
                }
            });
        }

        // Display MaterialDatePicker instance when image button clicked
        calendarButton.setOnClickListener(dateComp -> {
            bookingDateSelector.show(requireActivity().getSupportFragmentManager(),
                    "DATE_PICKER");
        });

        // Respond to user selected date input
        bookingDateSelector.addOnPositiveButtonClickListener(selection -> {
            dateText.setText(bookingDateSelector.getHeaderText());
            System.out.println(bookingDateSelector.getHeaderText());
            dateTextLayout.setHint("Selected date");
        });

    }

    public boolean checkDoubleNumeric(String input) {
        try {
            Double inputDouble = new Double(input);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public boolean checkIntegerNumeric(String input) {
        try {
            Integer inputInteger = new Integer(input);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public void editFormData(int arrayPos) {
        selectedBooking = bookingViewModel.getBooking(arrayPos);
        structureText.setText(selectedBooking.getStructureType());
        dateText.setText(selectedBooking.getBookingStartDate().toString());
        costText.setText(String.valueOf(selectedBooking.getCost()));
        daysText.setText(String.valueOf(selectedBooking.getNumberOfDays()));
        firstNameText.setText(selectedBooking.getCustomerFirstName());
        lastNameText.setText(selectedBooking.getCustomerLastName());
        firstLineAddress.setText(selectedBooking.getCustomerAddress());
    }

    public boolean checkEmpty() {
        boolean notEmpty = true;
        if (structureText.getText().toString().isEmpty()) {
            structureText.setError("Field requires input!");
            notEmpty = false;
        }

        if (dateText.getText().toString().isEmpty()) {
            dateText.setError("Field requires input");
            notEmpty = false;
        }

        if (costText.getText().toString().isEmpty()) {
            costText.setError("Field requires input");
            notEmpty = false;
        }

        if (daysText.getText().toString().isEmpty()) {
            daysText.setError("Field requires input");
            notEmpty = false;
        }

        if (firstNameText.getText().toString().isEmpty()) {
            firstNameText.setError("Field requires input");
            notEmpty = false;
        }

        if (lastNameText.getText().toString().isEmpty()) {
            lastNameText.setError("Field requires input!");
            notEmpty = false;
        }

        if (firstLineAddress.getText().toString().isEmpty()){
            firstLineAddress.setError("Field requires input");
            notEmpty = false;
        }

        return notEmpty;
    }
}
