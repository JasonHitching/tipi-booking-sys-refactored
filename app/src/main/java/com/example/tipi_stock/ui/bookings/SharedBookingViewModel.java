package com.example.tipi_stock.ui.bookings;

import android.app.Application;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tipi_stock.backend.bookings.data.Booking;
import com.example.tipi_stock.backend.bookings.data.BookingRepository;
import com.example.tipi_stock.ui.bookings.booking.BookingAdapter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Class for providing data to the user interface, decouples frontend from backend logic
 * Instances of 'ViewModel' survive configuration changes.
 *
 * Select functionality from Android Jetpack library classes are utilised
 * to achieve some of the required functionality for this class:
 *      https://developer.android.com/reference/android/arch/lifecycle/AndroidViewModel
 */
public class SharedBookingViewModel extends AndroidViewModel {

    private BookingRepository roomRepo;

    // Current live data of the Booking entities stored in the Room database
    private LiveData<List<Booking>> currentBookings;


    public SharedBookingViewModel(Application app) {
        super(app);
        roomRepo = new BookingRepository(app);
        currentBookings = roomRepo.getAllBookings();
    }

    public LiveData<List<Booking>> getAllBookings() {
        return currentBookings;
    }

    public Booking getBooking(int pos) {
        return currentBookings.getValue().get(pos);
    }

    /**
     * Sort recycler view cards by date in ascending order
     */
    public void sortDateAscending() {
        // obtain booking list size for the loop iterations
        int bookingsSize = currentBookings.getValue().size();

        for (int i = 0; i < bookingsSize - 1; i++) {
            for (int j = 0; j < bookingsSize - i - 1; j++) {
                LocalDate startDate1 = currentBookings.getValue().get(j).getBookingStartDate();
                LocalDate startDate2 = currentBookings.getValue().get(j + 1).getBookingStartDate();
                if (startDate1.isAfter(startDate2)) {
                    Booking tempBooking = currentBookings.getValue().get(j);
                    // Replace positions
                    currentBookings.getValue().set(j, currentBookings.getValue().get(j + 1));
                    currentBookings.getValue().set(j + 1, tempBooking);
                }
            }
        }
        currentBookings.getValue().get(0).customerFirstName = "";
    }

    /**
     * Sort the recycler view cards by date in descending order
     */
    public void sortDateDescending() {
        // obtain booking list size for the loop iterations
        int bookingsSize = currentBookings.getValue().size();

        for (int i = 0; i < bookingsSize - 1; i++) {
            for (int j = 0; j < bookingsSize - i - 1; j++) {
                LocalDate startDate1 = currentBookings.getValue().get(j).getBookingStartDate();
                LocalDate startDate2 = currentBookings.getValue().get(j + 1).getBookingStartDate();
                if (startDate2.isAfter(startDate1)) {
                    Booking tempBooking = currentBookings.getValue().get(j);
                    // Replace positions
                    currentBookings.getValue().set(j, currentBookings.getValue().get(j + 1));
                    currentBookings.getValue().set(j + 1, tempBooking);
                }
            }
        }
        currentBookings.getValue().get(0).customerFirstName = "";
    }

    /**
     * Method invoked from the booking form fragment to insert a new booking
     * into the relevant booking Room database.
     *
     * @param structType Type of structure for the booking
     * @param firstName  Customers first name
     * @param lastName   Customers surname
     * @param address    Customers address
     * @param cost       Total booking cost
     * @param startDate  Date that the booking starts
     * @param noOfDays   Number of days the structure will be booked for
     */
    public String createBooking(String structType, String firstName, String lastName, String address,
                                String cost, LocalDate startDate, String noOfDays) {

        double costVal;
        int numOfDays;

        try {
            costVal = new Double(cost);
            numOfDays = new Integer(noOfDays);
        } catch (NumberFormatException ex) {
            return "Invalid number input, check cost and days";
        }

        LocalDate todaysDate = LocalDate.now();

        // Check that the start date hasn't already passed
        if (startDate.isBefore(todaysDate)) {
            return "Booking date entered is in the past";
        }
//        } else if (startDate.getYear() > LocalDate.now().getDayOfYear() + 2) {
//            return String.format("Only bookings accepted for %d and %d",
//                    LocalDate.now().getYear(), LocalDate.now().getYear() + 1);
//            // Check that the start date hasn't already passed
//        } else if (startDate.isBefore(todaysDate)) {
        //    return "Booking date entered is in the past";
      //  }
        else if (checkExisting(startDate, structType)) {
            return "Identical booking exists on that date";
        } else {
            Booking newBooking = new Booking(structType, firstName, lastName, address, costVal, startDate, numOfDays);
            roomRepo.insertBooking(newBooking);
        }
        return "success";
    }

    /**
     * Function for checking whether an identical booking exists.
     *
     * @param startDate
     * @param structureName
     * @return
     */
    public boolean checkExisting(LocalDate startDate, String structureName) {
        // Check existing bookings to see if they match desired booking
        for (Booking booking : Objects.requireNonNull(currentBookings.getValue())) {
            if (booking.getBookingStartDate().equals(startDate)
                    && structureName.equals(booking.getStructureType())) {
                return true;
            }
        }
        return false;
    }

    public void updateBooking(Booking booking) {
        roomRepo.updateBooking(booking);
    }
}
