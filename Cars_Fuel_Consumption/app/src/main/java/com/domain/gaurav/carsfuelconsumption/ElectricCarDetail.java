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

//Get's ElectricCar object from intent and shows it's data to user using it's activity.
public class ElectricCarDetail extends AppCompatActivity {

    private ElectricCar electricCar;
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
        setContentView(R.layout.activity_electric_car_detail);

        Intent intent = getIntent();
        Car car = (Car) intent.getSerializableExtra("Car");

        electricCar = (ElectricCar) car.getCar();


        yr = String.valueOf(electricCar.getYear());
        ma = electricCar.getMake();
        mo = electricCar.getModel();
        cls = electricCar.getclass();

        TextView Title = (TextView)findViewById(R.id.CarName);
        Title.setText(yr + " " +  ma + " " + mo + " " + cls);

        TextView Year = (TextView)findViewById(R.id.electric_year);
        Year.setText(yr);

        TextView Make = (TextView)findViewById(R.id.electric_make);
        Make.setText(ma);

        TextView  Model = (TextView)findViewById(R.id.electric_model);
        Model.setText(mo);

        TextView Class = (TextView)findViewById(R.id.electric_class);
        Class.setText(cls);

        TextView Motor = (TextView)findViewById(R.id.electric_motor);
        Motor.setText(String.valueOf(electricCar.getMotor()));

        TextView Transmission = (TextView)findViewById(R.id.electric_transmission);
        Transmission.setText(electricCar.getTransmission());

        TextView Fuel = (TextView)findViewById(R.id.electric_fueltype);
        Fuel.setText(electricCar.getFuel());

        TextView City = (TextView)findViewById(R.id.electric_city);
        City.setText(String.valueOf(electricCar.getCity_kwh()));

        TextView Highway = (TextView)findViewById(R.id.electric_highway);
        Highway.setText(String.valueOf(electricCar.getHighway_kwh()));

        TextView Combine = (TextView)findViewById(R.id.electric_combine);
        Combine.setText(String.valueOf(electricCar.getCombine_kwh()));

        TextView CO2 = (TextView)findViewById(R.id.electric_co2);
        CO2.setText(String.valueOf(electricCar.getCO2()));

        TextView Rechargetime = (TextView)findViewById(R.id.electric_rechargetime);
        Rechargetime.setText(String.valueOf(electricCar.getRecharge_time()));

        TextView Range = (TextView)findViewById(R.id.electric_range);
        Range.setText(String.valueOf(electricCar.getRange()));

        TextView CityLe = (TextView)findViewById(R.id.electric_cityLe);
        CityLe.setText(String.valueOf(electricCar.getCity()));

        TextView HighwayLe = (TextView)findViewById(R.id.electric_highwayLe);
        HighwayLe.setText(String.valueOf(electricCar.getHighway()));

        TextView CombineLe = (TextView)findViewById(R.id.electric_combineLe);
        CombineLe.setText(String.valueOf(electricCar.getCombine()));


        //Checking if this car has already been added to favourites, if already added then disabling add button.
        Button addToFav= (Button)findViewById(R.id.add);
        if(db.checkIfExists(electricCar.getTable(), electricCar.getId())){
            addToFav.setText("ADDED TO FAVOURITES");
            addToFav.setEnabled(false);
        }else{
            addToFav.setEnabled(true);
        }
    }

    //Handles adding car to favourite list event, it's a call back function.
    public void addToFavourite(View view){

        String table = electricCar.getTable();
        int Id = electricCar.getId();

        db.addRowToFavourite(table, Id);

        Button button = (Button)findViewById(R.id.add);
        button.setEnabled(false);

        Toast.makeText(ElectricCarDetail.this, "Car has been added to Favourites.", Toast.LENGTH_SHORT).show();
    }

    //Calls WebSearch class/activity and passes year, make, model, and class of current car.
    public void CallWebView(View view){

        Intent intent = new Intent(ElectricCarDetail.this, WebSearch.class);
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
