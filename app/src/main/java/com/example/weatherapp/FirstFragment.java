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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.fragment.NavHostFragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.example.weatherapp.Utils.ForecastTimestamp;
import com.example.weatherapp.Utils.SportRating;
import com.example.weatherapp.databinding.FragmentFirstBinding;


import com.example.weatherapp.Utils.APIRequest;
import com.example.weatherapp.Utils.Cache;
import com.example.weatherapp.Utils.Forecast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

public class FirstFragment extends Fragment{

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                // We use a String here, but any type that can be put in a Bundle is supported
                String result = bundle.getString("bundleKey");
                // Do something with the result
                try {
                    Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(result);
                    date = date1;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar cal = Calendar.getInstance();
                Date date2 = cal.getTime();
                long diff = date.getTime() - date2.getTime();
                day = (int) (diff / (1000 * 60 * 60 * 24));
            }
        });
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
                Forecast forecast = UpdateWeatherData();
                updateListView(view, forecast);
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
                Forecast forecast = UpdateWeatherData();
                updateListView(view, forecast);
            }
        });

        /** "+" Button shows calendar on click **/
        binding.buttonToday3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new CalendarFragment();
                newFragment.show(getFragmentManager(), "DatePicker");
            }
        });

        binding.buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Forecast forecast = UpdateWeatherData();
                updateListView(view, forecast);
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

        Forecast forecast = UpdateWeatherData();
        updateListView(view, forecast);

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
    public Forecast UpdateWeatherData(){
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        String defaultValue = getResources().getString(R.string.selected_city_default_value);
        String city = sharedPref.getString(getString(R.string.selected_city), defaultValue);
        if (city.length()<1){
            city = "kaunas";
        }

        APIRequest output = new APIRequest();
        String myUrl = String.format("https://jello-backend.herokuapp.com/forecasts/%s",city);   //String to place our result in
        //String myUrl = String.format("http://localhost:8080/forecasts/%s/long-term",city);   //String to place our result in
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
        precipitationTextView.setText(valueOf("Precipitation: " + forecast.getCurrentPrecipitation()) + " mm/h");



        Log.d("data", forecast.getCurrentConditionCode());
        Log.d("data", valueOf(forecast.getCurrentAirTemperature()));
        Log.d("data", result);

        return forecast;
    }

    public void updateListView(View view, Forecast forecast){
        ListView listView = (ListView)view.findViewById(R.id.sportPresentationListView);
        ListView listView_moderate = (ListView)view.findViewById(R.id.sportModerateConditions);
        ListView listView_bad = (ListView)view.findViewById(R.id.sportBadConditions);
        List<String> sportsList = Arrays.asList(getResources().getStringArray(R.array.sports_array));
        List<String> selectedSportList = new ArrayList<String>();

        List<String> moderateSportsList = new ArrayList<String>();
        List<String> badSportsList = new ArrayList<String>();

        for (int i = 0; i < sportsList.size(); ++i){
            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            if (sharedPref.getBoolean(sportsList.get(i), false)){
                String rating = forecast.getCurrentTimestampSportRating(sportsList.get(i).toLowerCase());
                if(rating.equals("good")){
                    selectedSportList.add(sportsList.get(i));
                } else if(rating.equals("moderate")){
                    moderateSportsList.add(sportsList.get(i));
                } else if(rating.equals("bad")){
                    badSportsList.add(sportsList.get(i));
                }

            }
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, selectedSportList);
        listView.setAdapter(arrayAdapter);

        ArrayAdapter arrayAdapterModerate = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, moderateSportsList);
        listView_moderate.setAdapter(arrayAdapterModerate);

        ArrayAdapter arrayAdapterBad = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, badSportsList);
        listView_bad.setAdapter(arrayAdapterBad);

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CheckedTextView textView = (CheckedTextView)view;
                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean(textView.getText().toString(), textView.isChecked());
                editor.commit();
            }
        });*/
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
}