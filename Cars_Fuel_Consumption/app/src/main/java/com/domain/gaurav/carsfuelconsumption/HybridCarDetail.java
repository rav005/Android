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

//Get's HybridCar object from intent and shows it's data to user using it's activity.
public class HybridCarDetail extends AppCompatActivity {

    private HybridCar hybridCar;
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
        setContentView(R.layout.activity_hybrid_car_detail);

        Intent intent = getIntent();
        Car car = (Car) intent.getSerializableExtra("Car");

        hybridCar = (HybridCar) car.getCar();


        yr = String.valueOf(hybridCar.getYear());
        ma = hybridCar.getMake();
        mo = hybridCar.getModel();
        cls = hybridCar.getclass();

        TextView Title = (TextView)findViewById(R.id.CarName);
        Title.setText(yr + " " +  ma + " " + mo);

        TextView Year = (TextView)findViewById(R.id.hybrid_year);
        Year.setText(yr);

        TextView Make = (TextView)findViewById(R.id.hybrid_make);
        Make.setText(ma);

        TextView  Model = (TextView)findViewById(R.id.hybrid_model);
        Model.setText(mo);

        TextView Class = (TextView)findViewById(R.id.hybrid_class);
        Class.setText(cls);

        TextView Motor = (TextView)findViewById(R.id.hybrid_motor);
        Motor.setText(String.valueOf(hybridCar.getMotor()));

        TextView Engine = (TextView)findViewById(R.id.hybrid_engine);
        Engine.setText(String.valueOf(hybridCar.getEngine()));

        TextView Cylinder = (TextView)findViewById(R.id.hybrid_cylinders);
        Cylinder.setText(String.valueOf(hybridCar.getCylinder()));

        TextView Transmission = (TextView)findViewById(R.id.hybrid_transmission);
        Transmission.setText(hybridCar.getTransmission());

        TextView Fuel1 = (TextView)findViewById(R.id.hybrid_fueltype1);
        Fuel1.setText(hybridCar.getFuel());

        TextView Fuel2 = (TextView)findViewById(R.id.hybrid_fueltype2);
        Fuel2.setText(hybridCar.getFuel2());

        TextView Rechargetime = (TextView)findViewById(R.id.hybrid_recharge);
        Rechargetime.setText(String.valueOf(hybridCar.getRechargetime()));

        TextView combinedLe = (TextView)findViewById(R.id.hybrid_combinedLE);
        combinedLe.setText(hybridCar.getConsumption());

        TextView combinedCO2 = (TextView)findViewById(R.id.hybrid_combinedCO2);
        combinedCO2.setText(String.valueOf(hybridCar.getCO2()));

        TextView ElectricalRange = (TextView)findViewById(R.id.hybrid_electricalRange);
        ElectricalRange.setText(String.valueOf(hybridCar.getRange()));

        TextView City = (TextView)findViewById(R.id.hybrid_city);
        City.setText(String.valueOf(hybridCar.getCity()));

        TextView Highway = (TextView)findViewById(R.id.hybrid_highway);
        Highway.setText(String.valueOf(hybridCar.getHighway()));

        TextView Combine = (TextView)findViewById(R.id.hybrid_combine);
        Combine.setText(String.valueOf(hybridCar.getCombine()));

        TextView CO2 = (TextView)findViewById(R.id.hybrid_co2);
        CO2.setText(String.valueOf(hybridCar.getCo2_emission2()));

        TextView FullRange = (TextView)findViewById(R.id.hybrid_combinedRange);
        FullRange.setText(String.valueOf(hybridCar.getFullRange()));

        //Checking if this car has already been added to favourites, if already added then disabling add button.
        Button addToFav = (Button)findViewById(R.id.add);
        if(db.checkIfExists(hybridCar.getTable(), hybridCar.getId())){
            addToFav.setText("ADDED TO FAVOURITES");
            addToFav.setEnabled(false);
        }else{
            addToFav.setEnabled(true);
        }
    }

    //Handles adding car to favourite list event, it's a call back function.
    public void addToFavourite(View view){

        String table = hybridCar.getTable();
        int Id = hybridCar.getId();

        db.addRowToFavourite(table, Id);

        Button button = (Button)findViewById(R.id.add);
        button.setEnabled(false);

        Toast.makeText(HybridCarDetail.this, "Car has been added to Favourites.", Toast.LENGTH_SHORT).show();
    }

    //Calls WebSearch class/activity and passes year, make, model, and class of current car.
    public void CallWebView(View view){

        Intent intent = new Intent(HybridCarDetail.this, WebSearch.class);
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
