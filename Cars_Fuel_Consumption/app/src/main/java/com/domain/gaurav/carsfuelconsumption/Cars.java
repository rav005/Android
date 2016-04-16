package com.domain.gaurav.carsfuelconsumption;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//Displays list of cars which are provided after applying class Search filters on them.
public class Cars extends AppCompatActivity {
    private MyDbHandler db;
    private List<Car> search;
    private List<String> list;
    private ListView view;
    private String selected_type;
    private int selected_year;
    private String selected_make;
    private String selected_model;
    private String orderBy = "Year";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cars);

        db = MainActivity.db;

        //gets search filters selected in Search class.
        Intent intent = getIntent();
        selected_type = intent.getStringExtra("Type");
        selected_year = intent.getIntExtra("Year", 0);
        selected_make = intent.getStringExtra("Make");
        selected_model = intent.getStringExtra("Model");

        //gets all the cars from database after applying these passed in filters.
        search = db.findCars(selected_type, selected_year, selected_make, selected_model, orderBy);
        list = new ArrayList<String>(); //Holds minimum information to display for the list.

        setAdapter(); //Adds information to list and shows the list to users.

        //Adding on click listener so it can provide list which is ordered by year either ascending or descending.
        Button Year = (Button)findViewById(R.id.orderByYear);
        Year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderBy == "Year") {
                    orderBy = "Year DESC";
                } else {
                    orderBy = "Year";
                }
                search.clear();
                list.clear();
                search = db.findCars(selected_type, selected_year, selected_make, selected_model, orderBy);
                setAdapter();
            }
        });

        //Adding on click listener so it can provide list which is ordered by make either ascending or descending.
        Button Make = (Button)findViewById(R.id.orderByMake);
        Make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(orderBy == "Make"){
                    orderBy = "Make DESC";
                } else{
                    orderBy = "Make";
                }
                search.clear();
                list.clear();
                search = db.findCars(selected_type, selected_year, selected_make, selected_model, orderBy);
                setAdapter();
            }
        });

        //Adding on click listener so it can provide list which is ordered by model either ascending or descending.
        Button Model = (Button)findViewById(R.id.orderByModel);
        Model.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderBy == "Model") {
                    orderBy = "Model DESC";
                } else {
                    orderBy = "Model";
                }
                search.clear();
                list.clear();
                search = db.findCars(selected_type, selected_year, selected_make, selected_model, orderBy);
                setAdapter();
            }
        });

        //Setting up onItemClick listener so when user selects a car it can display all the information on that car.
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Car car = search.get(position);
                String type = car.getType();

                //Inspired from: http://stackoverflow.com/questions/14333449/passing-data-through-intent-using-serializable
                if(type.equals("Fuel_Cars")) {
                    Intent intent = new Intent(Cars.this, FuelCarDetail.class);
                    intent.putExtra("Car", (Serializable) car);
                    startActivity(intent);

                }else if(type.equals("Hybrid_Cars")){
                    Intent intent = new Intent(Cars.this, HybridCarDetail.class);
                    intent.putExtra("Car", car);
                    startActivity(intent);

                }else if(type.equals("Electric_Cars")){
                    Intent intent = new Intent(Cars.this, ElectricCarDetail.class);
                    intent.putExtra("Car", car);
                    startActivity(intent);

                }
            }
        });
    }

    //Adds little information to list and shows the list to users.
    private void setAdapter() {
        String type;
        int length = search.size();

        for(int i = 0; i < length; i++){
            Car c = search.get(i);
            type = c.getType();
            if(type.equals("Fuel_Cars")){
                FuelCar car = (FuelCar) c.getCar();
                String temp = String.valueOf(car.getYear());
                temp += " " + car.getMake() + " " + car.getModel() + " " + car.getTransmission();

                list.add(temp);
            }else if(type.equals("Hybrid_Cars")){
                HybridCar car = (HybridCar) c.getCar();
                String temp = String.valueOf(car.getYear());
                temp += " " + car.getMake() + " " + car.getModel() + " " + car.getclass();

                list.add(temp);
            }else if(type.equals("Electric_Cars")){
                ElectricCar car = (ElectricCar) c.getCar();
                String temp = String.valueOf(car.getYear());
                temp += " " + car.getMake() + " " + car.getModel() + " " + car.getclass();

                list.add(temp);
            }

        }

        view = (ListView)findViewById(R.id.carsList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Cars.this, R.layout.list_item, list);
        view.setAdapter(adapter);
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
