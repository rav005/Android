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

//Used to show all the cars in specified table. specified table is received from getting string extras from intent.
public class Browse extends AppCompatActivity {
    private MyDbHandler db;
    private List<Car> cars;
    private List<String> list;
    private ListView listview;
    private String table;
    private String orderBy = "Year";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        //Database connection
        db = MainActivity.db;

        Intent intent = getIntent();
        table = intent.getStringExtra("Table");

        cars = db.getallCarsfromTable(table, orderBy); //get's all the data from table, puts it in Car object.
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
                cars.clear();
                list.clear();
                cars = db.getallCarsfromTable(table, orderBy);
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
                cars.clear();
                list.clear();
                cars = db.getallCarsfromTable(table, orderBy);
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
                cars.clear();
                list.clear();
                cars = db.getallCarsfromTable(table, orderBy);
                setAdapter();
            }
        });

        //Disabling order by for favourites table because the table doesn't contain all the informaion on car to query order by.
        if(table.equals("Favourite_Cars")){
            Year.setEnabled(false);
            Make.setEnabled(false);
            Model.setEnabled(false);
        }

        //Setting up onItemClick listener so when user selects a car it can display all the information on that car.
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Car car = cars.get(position);
                String type = car.getType();

                //Inspired from: http://stackoverflow.com/questions/14333449/passing-data-through-intent-using-serializable
                if (type.equals("Fuel_Cars")) {
                    Intent intent = new Intent(Browse.this, FuelCarDetail.class);
                    intent.putExtra("Car", (Serializable) car);
                    startActivity(intent);

                }else if(type.equals("Hybrid_Cars")){
                    Intent intent = new Intent(Browse.this, HybridCarDetail.class);
                    intent.putExtra("Car", (Serializable) car);
                    startActivity(intent);

                }else if(type.equals("Electric_Cars")){
                    Intent intent = new Intent(Browse.this, ElectricCarDetail.class);
                    intent.putExtra("Car",(Serializable) car);
                    startActivity(intent);

                }
            }
        });
    }

    //Adds little information to list and shows the list to users.
    private void setAdapter() {
        String type;
        int length = cars.size();

        for(int i = 0; i < length; i++){
            Car c = cars.get(i);
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
                temp += " " + car.getMake() + " " + car.getModel() + " " + car.getTransmission();

                list.add(temp);
            }
        }

        if(table.equals("Favourite_Cars") && list.size() == 0){
            list.add("No Car has been added to Favourite List!");
        }

        listview = (ListView)findViewById(R.id.browseList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Browse.this, R.layout.list_item, list);
        listview.setAdapter(adapter);

    }

    //Creates a option menu for the app, same as main ativity.
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
