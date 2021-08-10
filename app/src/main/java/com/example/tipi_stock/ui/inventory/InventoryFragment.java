package com.example.tipi_stock.ui.inventory;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tipi_stock.R;
import com.example.tipi_stock.backend.structures.Tipi;
import com.example.tipi_stock.ui.bookings.SharedBookingViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Arrays;

public class InventoryFragment extends Fragment {

    View rootView;
    Button tipiComponentButton;
    InventoryViewModel inventoryViewModel;

    public InventoryFragment() {
    }


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rootView = inflater.inflate(R.layout.inventory_fragment, null);

        inventoryViewModel = new ViewModelProvider(this).get(InventoryViewModel.class);

        tipiComponentButton = rootView.findViewById(R.id.tipi_component_button);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        Context viewContext = view.getRootView().getContext();

        tipiComponentButton.setOnClickListener(tipiComponentButton -> {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(viewContext);
            dialogBuilder.setMultiChoiceItems(inventoryViewModel.getTipiObject().getComponents(),
                    inventoryViewModel.getBoolCheckedArray(),
                    (DialogInterface.OnMultiChoiceClickListener) (dialogInterface, i, b) -> {
                        System.out.println("Selected");
            });
            dialogBuilder.setPositiveButton("Ok", null);

            AlertDialog newDialog = dialogBuilder.create();
            newDialog.show();
        });
    }
}
