package com.domain.gaurav.carsfuelconsumption;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//Get's FuelCar object from intent and shows it's data to user using it's activity.
public class FuelCarDetail extends AppCompatActivity {

    private FuelCar fuelCar;
    //Variables which are used later for webview.
    private String yr;
    private String ma;
    private String mo;
    private String cls;

    MyDbHandler db = MainActivity.db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Inspired from: http://stackoverflow.com/questions/2198410/how-to-change-title-of-activity-in-android
        setTitle("Car Details");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_car_detail);

        Intent intent = getIntent();
        Car car = (Car) intent.getSerializableExtra("Car");

        fuelCar = (FuelCar) car.getCar();


        yr = String.valueOf(fuelCar.getYear());
        ma = fuelCar.getMake();
        mo = fuelCar.getModel();
        cls = fuelCar.getclass();

        TextView Title = (TextView)findViewById(R.id.CarName);
        Title.setText(yr + " " +  ma + " " + mo);

        TextView Year = (TextView)findViewById(R.id.fuel_year);
        Year.setText(yr);

        TextView Make = (TextView)findViewById(R.id.fuel_make);
        Make.setText(ma);

        TextView  Model = (TextView)findViewById(R.id.fuel_model);
        Model.setText(mo);

        TextView Class = (TextView)findViewById(R.id.fuel_class);
        Class.setText(cls);

        TextView Engine = (TextView)findViewById(R.id.fuel_engine);
        Engine.setText(String.valueOf(fuelCar.getEngine()));

        TextView Cylinders = (TextView)findViewById(R.id.fuel_cylinders);
        Cylinders.setText(String.valueOf(fuelCar.getCylinder()));

        TextView Trans = (TextView)findViewById(R.id.fuel_transmission);
        Trans.setText(fuelCar.getTransmission());

        TextView FuelType = (TextView)findViewById(R.id.fuel_fueltype);
        FuelType.setText(fuelCar.getFuel());

        TextView City = (TextView)findViewById(R.id.fuel_city);
        City.setText(String.valueOf(fuelCar.getCity()));

        TextView Hwy = (TextView)findViewById(R.id.fuel_highway);
        Hwy.setText(String.valueOf(fuelCar.getHighway()));

        TextView Comb = (TextView)findViewById(R.id.fuel_combine);
        Comb.setText(String.valueOf(fuelCar.getCombine()));

        TextView CO2 = (TextView)findViewById(R.id.fuel_co2);
        CO2.setText(String.valueOf(fuelCar.getCO2()));

        //Checking if this car has already been added to favourites, if already added then disabling add button.
        Button addToFav= (Button)findViewById(R.id.add);
        if(db.checkIfExists(fuelCar.getTable(), fuelCar.getId())){
            addToFav.setText("ADDED TO FAVOURITES");
            addToFav.setEnabled(false);
        }else{
            addToFav.setEnabled(true);
        }
    }

    //Handles adding car to favourite list event, it's a call back function.
    public void addToFavourite(View view){

        String table = fuelCar.getTable();
        int Id = fuelCar.getId();

        db.addRowToFavourite(table, Id);

        Button button = (Button)findViewById(R.id.add);
        button.setEnabled(false);

        Toast.makeText(FuelCarDetail.this, "Car has been added to Favourites.", Toast.LENGTH_SHORT).show();
    }

    //Calls WebSearch class/activity and passes year, make, model, and class of current car.
    public void CallWebView(View view){

        Intent intent = new Intent(FuelCarDetail.this, WebSearch.class);
        intent.putExtra("Search", yr + "+" + ma + "+" + mo + "+" + cls);

        startActivity(intent);
    }

    //Creates a option menu for the app, same as main activity.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //Inspired from: https://www.youtube.com/watch?v=4HPWV8DTyE4
    //Handles option menu events, same as main activity.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        Intent intent;

        switch(item.getItemId()){
            case R.id.action_settings:
                intent = new Intent(this, Settings.class);
                startActivity(intent);
                break;
            case R.id.action_help:
                intent = new Intent(this, Help.class);
                startActivity(intent);
                break;
        }

        return true;
    }
}
