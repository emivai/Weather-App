package com.example.weatherapp;

import static java.lang.String.valueOf;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.example.weatherapp.databinding.FragmentFirstBinding;


import com.example.weatherapp.Utils.APIRequest;
import com.example.weatherapp.Utils.Cache;
import com.example.weatherapp.Utils.Forecast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

public class FirstFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    private FragmentFirstBinding binding;
    private String city;
    private long day;
    private Date date;
    private ImageView weatherMainBlock;
    private boolean blockExpanded;
    private TextView weatherWindText;
    private TextView weatherPrecipationText;
    private TextView weatherVisibilityText;
    private View fragmentView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        day = 0;
        date = new Date();
        weatherMainBlock = (ImageView)view.findViewById(R.id.weather_main_block);
        weatherWindText = (TextView)view.findViewById(R.id.text_wind);
        weatherVisibilityText = (TextView)view.findViewById(R.id.text_visibility);
        weatherPrecipationText = (TextView)view.findViewById(R.id.text_precipation);

        /*binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("city", city);
                bundle.putLong("day", day);
                NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_SecondFragment, bundle);
            }
        });*/

        binding.buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_settingsFragment);
            }
        });

        /**Today button sets day to today on click **/
        binding.buttonToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                day = 0;
                date = new Date();
                UpdateWeatherData();
            }
        });

        /** Tommorow button sets day to tommorow on click **/
        binding.buttonToday2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                day = 1;
                Date dt = new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(dt);
                c.add(Calendar.DAY_OF_MONTH, 1);
                date = c.getTime();
                UpdateWeatherData();
            }
        });

        /** "+" Button shows calendar on click **/
        binding.buttonToday3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCalendar();
                UpdateWeatherData();
            }
        });

        binding.buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateWeatherData();
            }
        });

        binding.buttonWeatherExpand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.someshape);
                //Bitmap bMapScaled = Bitmap.createScaledBitmap(bMap, 150, 100, true);


                //Button button = view.findViewById(R.id.button_settings);
                //Matrix blockMatrix = weatherMainBlock.getImageMatrix();
                /*int height = weatherMainBlock.getHeight();
                blockMatrix.setTranslate(1.2f, 1.0f);
                weatherMainBlock.setImageMatrix(blockMatrix);*/
                //int height = weatherMainBlock.getLayoutParams().height;
                //weatherMainBlock.getLayoutParams().height = 200;
                //Matrix imageMatrix = weatherMainBlock.getImageMatrix();
                //imageMatrix.setRotate(45);
                //weatherMainBlock.setImageMatrix(imageMatrix);

                if(blockExpanded){
                    ViewGroup.LayoutParams layoutParams = weatherMainBlock.getLayoutParams();
                    layoutParams.height -= 350;
                    weatherMainBlock.setLayoutParams(layoutParams);

                    weatherWindText.setVisibility(View.INVISIBLE);
                    weatherPrecipationText.setVisibility(View.INVISIBLE);
                    weatherVisibilityText.setVisibility(View.INVISIBLE);
                } else {
                    ViewGroup.LayoutParams layoutParams = weatherMainBlock.getLayoutParams();
                    layoutParams.height += 350;
                    weatherMainBlock.setLayoutParams(layoutParams);

                    weatherWindText.setVisibility(View.VISIBLE);
                    weatherPrecipationText.setVisibility(View.VISIBLE);
                    weatherVisibilityText.setVisibility(View.VISIBLE);
                }


                blockExpanded = !blockExpanded;
                //weatherMainBlock.setMinimumHeight(height + 100);
            }

        });

        /*SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refreshLayout);

        // Refresh  the layout
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.d("REFRESH", "Refreshed");
                        // This line is important as it explicitly
                        // refreshes only once
                        // If "true" it implicitly refreshes forever
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );*/

        UpdateWeatherData();

        /*
        // Load a bitmap from the drawable folder

Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.my_image);

// Resize the bitmap to 150x100 (width x height)

Bitmap bMapScaled = Bitmap.createScaledBitmap(bMap, 150, 100, true);

// Loads the resized Bitmap into an ImageView

ImageView image = findViewById(R.id.test_image);

image.setImageBitmap(bMapScaled);
         */

        //NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_SecondFragment

        /*EditText edittext = (EditText) view.findViewById(R.id.edit_text_city);
        edittext.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction()==KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    String city = edittext.getText().toString();
                    Bundle bundle = new Bundle();
                    bundle.putString("city", city);
                    NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_SecondFragment, bundle);
                    return true;
                } else {
                    city = edittext.getText().toString();
                }
                return false;
            }
        });*/
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void UpdateWeatherData(){
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        String defaultValue = getResources().getString(R.string.selected_city_default_value);
        String city = sharedPref.getString(getString(R.string.selected_city), defaultValue);

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
            forecast = new Forecast(result, day);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //final TextView cityTextView = (TextView) fragmentView.findViewById(R.id.forecast_city_name);
        //cityTextView.setText(forecast.city);

        //final TextView conditionTextView = (TextView) getView().findViewById(R.id.forecast_weather_condition);
        //conditionTextView.setText(forecast.getCurrentConditionCode());

        DateFormat formatter = new SimpleDateFormat("EEEE");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        final TextView dayTextView = (TextView) getView().findViewById(R.id.text_day);
        String day1 = valueOf(day);
        dayTextView.setText( formatter.format(date).substring(0,3) + ", " + day1);

        final TextView airTemperatureTextView = (TextView) getView().findViewById(R.id.text_temperature);
        airTemperatureTextView.setText(valueOf(forecast.getCurrentAirTemperature()) + " °C");

        final TextView windSpeedTextView = (TextView) getView().findViewById(R.id.text_wind);
        windSpeedTextView.setText(valueOf("Wind: " + forecast.getCurrentWindSpeed()) + "(" + valueOf(forecast.getCurrentWindGust()) + ") m/s");

        final TextView precipitationTextView = (TextView) getView().findViewById(R.id.text_precipation);
        precipitationTextView.setText(valueOf("Precipation: " + forecast.getCurrentPrecipitation()) + " mm/h");



        Log.d("data", forecast.getCurrentConditionCode());
        Log.d("data", valueOf(forecast.getCurrentAirTemperature()));
        Log.d("data", result);
    }

    /*@RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onResume()
    {
        super.onResume();
        UpdateWeatherData();
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //Calendar setup
    private void showCalendar(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(),
                R.style.AppTheme_DatePickerDialog,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );

        //Can't choose day earlier than today
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

        //Can choose up to a week forward from today
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, +6);
        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());

        datePickerDialog.show();
    }

    //When day selection is confirmed this method determines difference
    //between that day and today (needed for API request)
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//        String othr = "year" + '-' + "month" + '-' + "day";
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        Date today = new Date();
//        Date date = new Date();
//        this.date = date;
//        try {
//            date =  formatter.parse(othr);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        long diffInMillies = Math.abs(date.getTime() - today.getTime());
//        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        this.day = 2;
    }
}