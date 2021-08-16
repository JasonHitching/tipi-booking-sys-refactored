package com.example.tipi_stock.backend.bookings.data;

import androidx.room.TypeConverter;

import java.time.LocalDate;

/**
 * Class for converting a Long value to a Date and vice versa
 * The Room persistence library knows how to persist Long objects but not Date objects
 */
public class DateConverter {

    /**
     * Type converter for converting a String to a Date object
     * @param stringToConvert
     * @return a new Date object
     */
    @TypeConverter
    public static LocalDate convertFromString(String stringToConvert) {
        if (stringToConvert == null) {
            return null;
        } else {
            return LocalDate.parse(stringToConvert);
        }
    }

    /**
     * Type converter for converting a date to a String for Room storage
     *
     * @param dateToConvert date to convert
     * @return converted date string version of date
     */
    @TypeConverter
    public static String convertDateToString(LocalDate dateToConvert) {
        if (dateToConvert == null) {
            return null;
        } else {
            return dateToConvert.toString();
        }
    }
}
