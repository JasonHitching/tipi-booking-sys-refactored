package com.example.tipi_stock.backend.bookings.data;

import androidx.room.TypeConverter;

import java.time.LocalDate;
import java.util.Date;

/**
 * Class for converting a Long value to a Date and vice versa
 * The Room persistence library knows how to persist Long objects but not Date objects
 */
public class DateConverter {

    /**
     * Type converter for converting a timestamp to a Date object
     * @param stringToConvert
     * @return a new Date object
     */
    @TypeConverter
    public static LocalDate convertFromTimestamp(String stringToConvert) {
        if (stringToConvert == null) {
            return null;
        } else {
            return LocalDate.parse(stringToConvert);
        }
    }

    /**
     * Type converter for converting a date to a timestamp
     * @param dateToConvert
     * @return converted date
     */
    @TypeConverter
    public static String convertDateToTimestamp(LocalDate dateToConvert) {
        if (dateToConvert == null) {
            return null;
        } else {
            return dateToConvert.toString();
        }
    }
}
