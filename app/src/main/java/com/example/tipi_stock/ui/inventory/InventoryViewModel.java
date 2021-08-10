package com.example.tipi_stock.ui.inventory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.tipi_stock.backend.structures.Tipi;

import java.util.Arrays;

public class InventoryViewModel extends AndroidViewModel {

    private boolean[] boolCheckedArray;
    private Tipi tipiObject;


    public InventoryViewModel(@NonNull Application app) {
        super(app);
        tipiObject = new Tipi();
        boolCheckedArray = new boolean[tipiObject.getComponents().length];
        Arrays.fill(boolCheckedArray, Boolean.FALSE);
    }

    public Tipi getTipiObject() {
        return tipiObject;
    }

    public boolean[] getBoolCheckedArray() {
        return boolCheckedArray;
    }
}
