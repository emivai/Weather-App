package com.example.weatherapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.Fragment;

import com.example.weatherapp.R;

public class Cache extends Fragment {
    public void SaveCity(String city){
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        String defaultValue = getResources().getString(R.string.selected_city_default_value);
        editor.putString(getString(R.string.selected_city), city);
        editor.apply();
    }
}
