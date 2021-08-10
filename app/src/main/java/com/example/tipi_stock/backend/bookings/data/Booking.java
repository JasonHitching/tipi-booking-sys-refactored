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

    public int getId() {
        return id;
    }

    public String getStructureType() {
        return structureType;
    }

    public void setStructureType(String struct) {
        structureType = struct;
    }

    public void setCustomerAddress(String change) {
        customerAddress = customerAddress + change;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public String getCustomerAddress() { return customerAddress; }

    public double getCost() {
        return cost;
    }

    public LocalDate getBookingStartDate() {
        return bookingStartDate;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setBookingStartDate(LocalDate bookingStartDate) {
        this.bookingStartDate = bookingStartDate;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    @NonNull
    @Override
    public String toString() {
        return bookingStartDate.toString();
    }
}
