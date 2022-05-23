package com.example.weatherapp.Utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ForecastTimestamp {
    public LocalDateTime forecastTimeUTC;
    public String conditionCode;
    public double airTemperature;
    public int windSpeed;
    public int windGust;
    public double precipitation;
    public List<SportRating> sportRatings;
    /*
    TODO
    Expand this class for forecast timestamp data and use it in parent
    Forecast class for saving forecast data for multiple timestamps
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ForecastTimestamp(JSONObject data, long days) throws JSONException {
        this.forecastTimeUTC = LocalDateTime.parse(data.getString("forecastTimeUtc"), DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss")).plusDays(days);
        this.conditionCode = data.getString("conditionCode");
        this.airTemperature = data.getDouble("airTemperature");
        this.windSpeed = data.getInt("windSpeed");
        this.windGust = data.getInt("windGust");
        this.precipitation = data.getDouble("totalPrecipitation");
        this.sportRatings = new ArrayList<SportRating>();

        JSONArray sports = data.getJSONArray("sports");

        for(int i = 0; i < sports.length(); i++){
            SportRating sportRating = new SportRating(sports.getJSONObject(i));
            this.sportRatings.add(sportRating);
        }

    }
}
