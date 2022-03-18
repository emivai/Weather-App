package com.example.weatherapp.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class ForecastTimestamp {
    public String conditionCode;
    public double airTemperature;
    /*
    TODO
    Expand this class for forecast timestamp data and use it in parent
    Forecast class for saving forecast data for multiple timestamps
     */
    public ForecastTimestamp(JSONObject data) throws JSONException {
        this.conditionCode = data.getString("conditionCode");
        this.airTemperature = data.getDouble("airTemperature");
    }
}
