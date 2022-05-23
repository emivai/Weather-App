package com.example.weatherapp.Utils;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import java.time.*;
import java.time.format.DateTimeFormatter;

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
    public int currentIndex;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Forecast(String jsonString, long days) throws JSONException {
        //https://stackoverflow.com/questions/18899232/how-to-parse-this-json-response-in-java
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject place = jsonObject.getJSONObject("place");
        this.city = place.getString("name");
        this.code = place.getString("code");
        this.administrativeDivision = place.getString("administrativeDivision");
        JSONArray forecastTimestamps = jsonObject.getJSONArray("forecastTimestamps");
        JSONObject currentForecast = forecastTimestamps.getJSONObject(0);

        LocalDateTime nowUTC = LocalDateTime.now().minusHours(2); //gets the current time in UTC

        this.timestamps = new ArrayList<ForecastTimestamp>();
        //Parsing list of forecast timestamps
        for(int i = 0; i < forecastTimestamps.length(); i++){
            ForecastTimestamp timestamp = new ForecastTimestamp(forecastTimestamps.getJSONObject(i), days);
            this.timestamps.add(timestamp);
            if(timestamp.forecastTimeUTC.compareTo(nowUTC) == -1){
                currentIndex = i; //gets the index of the current hour timestamp
            }
        }

        Log.d("DEBUG", "DATA");
    }

    public String getCurrentConditionCode(){
        return this.timestamps.get(currentIndex).conditionCode;
    }

    public double getCurrentAirTemperature(){
        return this.timestamps.get(currentIndex).airTemperature;
    }

    public List<SportRating> getCurrentTimestampRatings() { return this.timestamps.get(currentIndex).sportRatings; }

    public String getCurrentTimestampSportRating(String sportType) {
        ForecastTimestamp timestamp = this.timestamps.get(currentIndex);
        for(int i = 0; i < timestamp.sportRatings.size(); i++){
            if(timestamp.sportRatings.get(i).sportType.equals(sportType)){
                return timestamp.sportRatings.get(i).rating;
            }
        }
        return "bad";
    }

    public int getCurrentWindSpeed(){
        return this.timestamps.get(currentIndex).windSpeed;
    }

    public int getCurrentWindGust(){
        return this.timestamps.get(currentIndex).windGust;
    }

    public double getCurrentPrecipitation(){
        return this.timestamps.get(currentIndex).precipitation;
    }
}
