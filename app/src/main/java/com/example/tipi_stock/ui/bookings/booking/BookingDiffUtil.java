package com.example.tipi_stock.ui.bookings.booking;

import androidx.recyclerview.widget.DiffUtil;

import com.example.tipi_stock.backend.bookings.data.Booking;

import java.util.List;

public class BookingDiffUtil extends DiffUtil.Callback {

    private final List<Booking> oldList;
    private final List<Booking> newList;

    public BookingDiffUtil(List<Booking> oldList, List<Booking> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public final int getOldListSize() {
        return oldList != null ? oldList.size() : 0;
    }

    @Override
    public final int getNewListSize() {
        return newList != null ? newList.size() : 0;
    }

    /**
     * Method used by DiffUtil to determine if two list items are the same
     * The Room database primary key is used to compare between the two.
     */
    @Override
    public final boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
    }

    @Override
    public final boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
    }
}


