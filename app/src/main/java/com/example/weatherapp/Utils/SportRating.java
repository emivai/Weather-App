package com.example.weatherapp.Utils;

import org.json.JSONException;
import org.json.JSONObject;

public class SportRating {
    public String sportType;
    public String rating;

    public SportRating(String sportType, String rating){
        this.sportType = sportType;
        this.rating = rating;
    }
    public SportRating(JSONObject data) throws JSONException {
        this.sportType = data.getString("sport");
        this.rating = data.getString("conditionRating");
    }
}
