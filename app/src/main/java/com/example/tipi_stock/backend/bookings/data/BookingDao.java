package com.example.tipi_stock.backend.bookings.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
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
     * SQL insert for adding a booking to the booking databse
     * @param booking booking to be added
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertBooking(Booking booking);

    /**
     * SQL query for deleting all stored booking data
     */
    @Query("DELETE FROM booking_table")
    void deleteAllBookings();

    @Update(entity = Booking.class)
    public  void updateBooking(Booking booking);
}
