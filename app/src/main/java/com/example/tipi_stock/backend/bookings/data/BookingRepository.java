package com.example.tipi_stock.backend.bookings.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * Class for interacting with the 'booking Room database'
 */
public class BookingRepository {

    private final BookingDao bookingDao;
    private final LiveData<List<Booking>> currentBookings;

    public BookingRepository(Application app) {
        BookingDatabase database = BookingDatabase.getDbInstance(app);
        bookingDao = database.bookingDao();
        currentBookings = bookingDao.getAllBookings();
    }

    public final LiveData<List<Booking>> getAllBookings() {
        return currentBookings;
    }

    /**
     * Insert a new booking into the database
     * @param newBooking booking object
     */
    public final void insertBooking(Booking newBooking) {
        BookingDatabase.databaseExecutor.execute(() -> bookingDao.insertBooking(newBooking));
    }

    public final void updateBooking(Booking updatedBooking) {
        BookingDatabase.databaseExecutor.execute(() -> bookingDao.updateBooking(updatedBooking));

    }
}


