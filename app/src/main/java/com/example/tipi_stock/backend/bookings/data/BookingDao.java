package com.example.tipi_stock.backend.bookings.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Booking data access object (DAO) that provides abstract methods
 * for accessing and manipulating data in the bookings database
 */
@Dao
public interface BookingDao {

    /**
     * SQL query for obtaining all bookings currently stored in the database
     * @return list of bookings
     */
    @Query("SELECT * from booking_table")
    LiveData<List<Booking>> getAllBookings();

    /**
     * SQL insert for adding a booking to the booking database
     * @param booking booking to be added
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBooking(Booking booking);

    /**
     * SQL query for deleting all stored booking data
     */
    @Query("DELETE FROM booking_table")
    void deleteAllBookings();

    /**
     * SQL update for updating an existing booking
     * @param booking entity to be updated
     */
    @Update(entity = Booking.class)
    void updateBooking(Booking booking);
}

