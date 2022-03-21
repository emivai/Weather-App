package com.example.weatherapp;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.weatherapp.databinding.FragmentSecondBinding;
import com.example.weatherapp.Utils.APIRequest;
import com.example.weatherapp.Utils.Forecast;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String city = getArguments().getString("city");


        APIRequest output = new APIRequest();
        String myUrl = String.format("https://jello-backend.herokuapp.com/forecasts/%s/long-term",city);   //String to place our result in
        String result = "<REPLACE>";   //Instantiate new instance of our class
        APIRequest getRequest = new APIRequest();   //Perform the doInBackground method, passing in our url
        try {
            result = getRequest.execute(myUrl).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Forecast forecast = null;
        try {
            forecast = new Forecast(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final TextView cityTextView = (TextView) view.findViewById(R.id.forecast_city_name);
        cityTextView.setText(forecast.city);

        final TextView conditionTextView = (TextView) view.findViewById(R.id.forecast_weather_condition);
        conditionTextView.setText(forecast.getCurrentConditionCode());

        final TextView airTemperatureTextView = (TextView) view.findViewById(R.id.forecast_temperature);
        airTemperatureTextView.setText(String.valueOf(forecast.getCurrentAirTemperature()) + "°C");

        final TextView windSpeedTextView = (TextView) view.findViewById(R.id.forecast_wind_speed);
        windSpeedTextView.setText(String.valueOf(forecast.getCurrentWindSpeed()) + "(" + String.valueOf(forecast.getCurrentWindGust()) + ") m/s");

        final TextView precipitationTextView = (TextView) view.findViewById(R.id.forecast_precipitation);
        precipitationTextView.setText(String.valueOf(forecast.getCurrentPrecipitation()) + " mm/h");



        Log.d("data", forecast.getCurrentConditionCode());
        Log.d("data", String.valueOf(forecast.getCurrentAirTemperature()));
        Log.d("data", result);

        /*binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}