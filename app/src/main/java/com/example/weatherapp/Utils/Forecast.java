package com.example.weatherapp.Utils;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Forecast {
    /*
    TODO
    Expand Class to contain more data for forecast

    OBJECT EXAMPLE
    https://jello-backend.herokuapp.com/forecasts/vilnius/long-term
     */
    public String city;
    public String code;
    public String administrativeDivision;
    public List<ForecastTimestamp> timestamps;
    public double temperature;
    public String weatherType;

    public Forecast(String jsonString) throws JSONException {
        //https://stackoverflow.com/questions/18899232/how-to-parse-this-json-response-in-java
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject place = jsonObject.getJSONObject("place");
        this.city = place.getString("name");
        this.code = place.getString("code");
        this.administrativeDivision = place.getString("administrativeDivision");
        JSONArray forecastTimestamps = jsonObject.getJSONArray("forecastTimestamps");
        JSONObject currentForecast = forecastTimestamps.getJSONObject(0);

        this.timestamps = new ArrayList<ForecastTimestamp>();
        //Parsing list of forecast timestamps
        for(int i = 0; i < forecastTimestamps.length(); i++){
            ForecastTimestamp timestamp = new ForecastTimestamp(forecastTimestamps.getJSONObject(i));
            this.timestamps.add(timestamp);
        }

        Log.d("DEBUG", "DATA");
    }

    public String getCurrentConditionCode(){
        return this.timestamps.get(0).conditionCode;
    }

    public double getCurrentAirTemperature(){
        return this.timestamps.get(0).airTemperature;
    }
}
