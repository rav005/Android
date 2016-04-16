package com.domain.gaurav.carsfuelconsumption;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Switch;

//This class gives user option if he/she wants to see mileage per liter or gallon, see distance in kilometers or miles,
// and more options but I didn't had time to do it.

public class Settings extends AppCompatActivity {

    //Inspired from: http://www.mysamplecode.com/2013/04/android-switch-button-example.html
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Settings");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Switch button = (Switch)findViewById(R.id.mySwitch);
        button.setChecked(true);


    }

}
