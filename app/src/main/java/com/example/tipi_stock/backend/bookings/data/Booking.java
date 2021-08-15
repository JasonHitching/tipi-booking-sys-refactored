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

    public String structureType;

    public String customerFirstName;

    public String customerLastName;

    public String customerAddress;

    public double cost;

    public LocalDate bookingStartDate;

    public int numberOfDays;

    public Booking(String structureType, String customerFirstName, String customerLastName,
                   String customerAddress, double cost, LocalDate bookingStartDate, int numberOfDays) {
        this.structureType = structureType;
        this.customerFirstName = customerFirstName;
        this.customerLastName = customerLastName;
        this.customerAddress = customerAddress;
        this.cost = cost;
        this.bookingStartDate = bookingStartDate;
        this.numberOfDays = numberOfDays;
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
