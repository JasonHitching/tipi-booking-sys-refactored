package com.example.tipi_stock.ui.bookings.booking_forms;

import android.app.AlertDialog;
import android.content.Context;
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

    private MaterialDatePicker bookingDateSelector;
    private ImageButton calendarButton;
    private TextInputEditText dateText, structureText, firstNameText, lastNameText, costText, daysText,
                                firstLineAddress;
    private TextInputLayout dateTextLayout;
    private SharedBookingViewModel bookingViewModel;
    private Button submitButton;
    private Booking selectedBooking;

    @NonNull
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater,
                                   @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                                   @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.booking_form_fragment, null);

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
    public final void onViewCreated(@NonNull View view,
                                    @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        String editAddFlag = Objects.requireNonNull(getArguments()).get("type").toString();

        // If currently the edit flag is in 'edit' mode
        if (editAddFlag.equals("edit")) {
            editFormData(getArguments().getInt("position"));
            submitButton.setOnClickListener(view1 -> {
                String hireDays = Objects.requireNonNull(daysText.getText()).toString();
                String cost = Objects.requireNonNull(costText.getText()).toString();

                if (checkDoubleNumeric(cost) && checkIntegerNumeric(hireDays)) {
                    selectedBooking.setStructureType(Objects.requireNonNull(structureText.getText()).toString());
                    selectedBooking.setCost(Double.parseDouble(costText.getText().toString()));
                    selectedBooking.setNumberOfDays(Integer.parseInt(daysText.getText().toString()));
                    selectedBooking.setCustomerFirstName(Objects.requireNonNull(firstNameText.getText()).toString());
                    selectedBooking.setCustomerLastName(Objects.requireNonNull(lastNameText.getText()).toString());
                    selectedBooking.setCustomerAddress(Objects.requireNonNull(firstLineAddress.getText()).toString());
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
                    String replaceCommas = Objects.requireNonNull(dateText.getText()).toString().replaceAll(", ", " ");

                    double costVal = 0;
                    int numOfDays = 0;

                    try {
                        costVal = Double.parseDouble(Objects.requireNonNull(costText.getText()).toString());
                        numOfDays = Integer.parseInt(Objects.requireNonNull(daysText.getText().toString()));
                    } catch (NumberFormatException ex) {
                        Log.d(TAG, "onViewCreated: Parse Failed");
                    }

                    Booking newBooking = new Booking.BookingBuilder()
                            .withType(Objects.requireNonNull(structureText.getText()).toString())
                            .withFirstName(Objects.requireNonNull(firstNameText.getText()).toString())
                            .withLastName(Objects.requireNonNull(lastNameText.getText()).toString())
                            .withAddress(Objects.requireNonNull(firstLineAddress.getText()).toString())
                            .withCost(costVal)
                            .withDate(LocalDate.parse(replaceCommas.replace(" ", "-"), dateFormatter))
                            .withNumDays(numOfDays)
                            .build();

                    String result = bookingViewModel.createBooking(newBooking);

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
        calendarButton.setOnClickListener(dateComp -> bookingDateSelector.show(requireActivity().getSupportFragmentManager(),
                "DATE_PICKER"));

        // Respond to user selected date input
        bookingDateSelector.addOnPositiveButtonClickListener(selection -> {
            dateText.setText(bookingDateSelector.getHeaderText());
            System.out.println(bookingDateSelector.getHeaderText());
            dateTextLayout.setHint("Selected date");
        });

    }

    public final boolean checkDoubleNumeric(String input) {
        try {
            Double inputDouble = Double.valueOf(input);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public final boolean checkIntegerNumeric(String input) {
        try {
            Integer inputInteger = Integer.valueOf(input);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public final void editFormData(int arrayPos) {
        selectedBooking = bookingViewModel.getBooking(arrayPos);
        structureText.setText(selectedBooking.getStructureType());
        dateText.setText(selectedBooking.getBookingStartDate().toString());
        costText.setText(String.valueOf(selectedBooking.getCost()));
        daysText.setText(String.valueOf(selectedBooking.getNumberOfDays()));
        firstNameText.setText(selectedBooking.getCustomerFirstName());
        lastNameText.setText(selectedBooking.getCustomerLastName());
        firstLineAddress.setText(selectedBooking.getCustomerAddress());
    }

    public final boolean checkEmpty() {
        boolean notEmpty = true;
        if (Objects.requireNonNull(structureText.getText()).toString().isEmpty()) {
            structureText.setError("Field requires input!");
            notEmpty = false;
        }

        if (Objects.requireNonNull(dateText.getText()).toString().isEmpty()) {
            dateText.setError("Field requires input");
            notEmpty = false;
        }

        if (Objects.requireNonNull(costText.getText()).toString().isEmpty()) {
            costText.setError("Field requires input");
            notEmpty = false;
        }

        if (Objects.requireNonNull(daysText.getText()).toString().isEmpty()) {
            daysText.setError("Field requires input");
            notEmpty = false;
        }

        if (Objects.requireNonNull(firstNameText.getText()).toString().isEmpty()) {
            firstNameText.setError("Field requires input");
            notEmpty = false;
        }

        if (Objects.requireNonNull(lastNameText.getText()).toString().isEmpty()) {
            lastNameText.setError("Field requires input!");
            notEmpty = false;
        }

        if (Objects.requireNonNull(firstLineAddress.getText()).toString().isEmpty()){
            firstLineAddress.setError("Field requires input");
            notEmpty = false;
        }

        return notEmpty;
    }
}
