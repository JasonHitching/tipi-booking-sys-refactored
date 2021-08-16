package com.example.tipi_stock.backend.bookings.data;



import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

/**
 * Entity model of a structure booking
 */
@Entity(tableName = "booking_table")
public class Booking {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String structureType, customerFirstName, customerLastName, customerAddress;

    public double cost;

    public LocalDate bookingStartDate;

    public int numberOfDays;

    public Booking() {}

    /**
     * Inner class for building a booking object
     */
    public static class BookingBuilder {
        private String structureType, customerFirstName, customerLastName, customerAddress;
        private double cost;
        private LocalDate bookingStartDate;
        int numberOfDays;

        public final BookingBuilder withType(String structureType) {
            this.structureType = structureType;
            return this;
        }

        public final BookingBuilder withFirstName(String customerFirstName) {
            this.customerFirstName = customerFirstName;
            return this;
        }

        public final BookingBuilder withLastName(String customerLastName) {
            this.customerLastName = customerLastName;
            return this;
        }

        public final BookingBuilder withAddress(String customerAddress) {
            this.customerAddress = customerAddress;
            return this;
        }

        public final BookingBuilder withCost(double cost) {
            this.cost = cost;
            return this;
        }

        public final BookingBuilder withDate(LocalDate bookingStartDate) {
            this.bookingStartDate = bookingStartDate;
            return this;
        }

        public final BookingBuilder withNumDays(int numberOfDays) {
            this.numberOfDays = numberOfDays;
            return this;
        }

        public Booking build() {
            Booking newBooking = new Booking();
            newBooking.structureType = this.structureType;
            newBooking.customerFirstName = this.customerFirstName;
            newBooking.customerLastName = this.customerLastName;
            newBooking.customerAddress = this.customerAddress;
            newBooking.cost = this.cost;
            newBooking.bookingStartDate = this.bookingStartDate;
            newBooking.numberOfDays = this.numberOfDays;

            return newBooking;
        }
    }

    public final int getId() {
        return id;
    }

    public final String getStructureType() {
        return structureType;
    }

    public final void setStructureType(String struct) {
        structureType = struct;
    }

    public final void setCustomerAddress(String change) {
        customerAddress += change;
    }

    public final String getCustomerFirstName() {
        return customerFirstName;
    }

    public final String getCustomerLastName() {
        return customerLastName;
    }

    public final String getCustomerAddress() { return customerAddress; }

    public final double getCost() {
        return cost;
    }

    public final LocalDate getBookingStartDate() {
        return bookingStartDate;
    }

    public final int getNumberOfDays() {
        return numberOfDays;
    }

    public final void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public final void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public final void setCost(double cost) {
        this.cost = cost;
    }

    public final void setBookingStartDate(LocalDate bookingStartDate) {
        this.bookingStartDate = bookingStartDate;
    }

    public final void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    @NonNull
    @Override
    public final String toString() {
        return bookingStartDate.toString();
    }
}
