// AddCityFragment.java
package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {

    interface AddCityDialogListener {
        void addCity(City city);

        void updateCity(City city, int position);
    }

    private static final String ARG_CITY = "arg_city";
    private static final String ARG_POS = "arg_pos";

    public static AddCityFragment newInstance(@Nullable City city, int position) {
        AddCityFragment frag = new AddCityFragment();
        Bundle b = new Bundle();
        if (city != null) {
            b.putSerializable(ARG_CITY, city);
            b.putInt(ARG_POS, position);
        } else {
            b.putInt(ARG_POS, -1);
        }
        frag.setArguments(b);
        return frag;
    }

    private AddCityDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        Bundle args = getArguments();
        final City editingCity;
        final int position;

        if (args != null && args.containsKey(ARG_CITY)) {
            editingCity = (City) args.getSerializable(ARG_CITY);
            position = args.getInt(ARG_POS, -1);
        } else {
            editingCity = null;
            position = -1;
        }

        final boolean isEditing = editingCity != null;

        if (isEditing) {
            editCityName.setText(editingCity.getName());
            editProvinceName.setText(editingCity.getProvince());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle(isEditing ? "Edit city" : "Add a city")
                .setNegativeButton("Cancel", null)
                .setPositiveButton(isEditing ? "Save" : "Add", (dialog, which) -> {
                    String cityName = editCityName.getText().toString().trim();
                    String provinceName = editProvinceName.getText().toString().trim();
                    if (cityName.isEmpty() || provinceName.isEmpty()) return;

                    if (isEditing) {
                        editingCity.setName(cityName);
                        editingCity.setProvince(provinceName);
                        listener.updateCity(editingCity, position);
                    } else {
                        listener.addCity(new City(cityName, provinceName));
                    }
                })
                .create();
    }
}
