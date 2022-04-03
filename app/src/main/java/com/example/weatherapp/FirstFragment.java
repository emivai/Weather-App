package com.example.weatherapp;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;

import com.example.weatherapp.databinding.FragmentFirstBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class FirstFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    private FragmentFirstBinding binding;
    private String city;
    private long day;
    private ImageView weatherMainBlock;
    private boolean blockExpanded;
    private TextView weatherWindText;
    private TextView weatherPrecipationText;
    private TextView weatherVisibilityText;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        day = 0;
        weatherMainBlock = (ImageView)view.findViewById(R.id.weather_main_block);
        weatherWindText = (TextView)view.findViewById(R.id.text_wind);
        weatherVisibilityText = (TextView)view.findViewById(R.id.text_visibility);
        weatherPrecipationText = (TextView)view.findViewById(R.id.text_precipation);
        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("city", city);
                bundle.putLong("day", day);
                NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_SecondFragment, bundle);
            }
        });

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
            }
        });

        /** Tommorow button sets day to tommorow on click **/
        binding.buttonToday2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                day = 1;
            }
        });

        /**Today button sets day to today on click **/
        binding.buttonToday3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                showCalendar();
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

        EditText edittext = (EditText) view.findViewById(R.id.edit_text_city);
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
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showCalendar(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(),
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        String othr = "year" + '-' + "month" + '-' + "day";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date today = new Date();
        Date date = new Date();
        try {
            date =  formatter.parse(othr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long diffInMillies = Math.abs(date.getTime() - today.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        this.day = diff;
    }
}