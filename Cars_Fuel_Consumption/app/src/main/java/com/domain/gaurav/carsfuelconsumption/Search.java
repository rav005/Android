package com.domain.gaurav.carsfuelconsumption;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import java.util.List;

//Filter class which allows uses to select car type, year, make and model to find the car which user wanted.
// passes the result to Cars class so it can show list of these cars.
public class Search extends ActionBarActivity {
    MyDbHandler db;

    Spinner car_years;
    Spinner car_makes;
    Spinner car_models;
    Button search_button;

    String selected_type;
    String selected_make;
    String selected_model;
    int selected_year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        db = new MyDbHandler(this.getApplicationContext());
        db.getReadableDatabase();

        //Disabling every thing else unless user selects type of car he/she wishes to search.
        car_years = (Spinner) findViewById(R.id.year_spinner);
        car_years.setEnabled(false);

        car_makes = (Spinner) findViewById(R.id.make_spinner);
        car_makes.setEnabled(false);

        car_models = (Spinner) findViewById(R.id.model_spinner);
        car_models.setEnabled(false);

        search_button = (Button) findViewById(R.id.search_button);
        search_button.setEnabled(false);

        //Setting Spinner list for types.
        String[] car_types = db.getallTypes();
        ArrayAdapter<String> types_adapter = new ArrayAdapter<String>(this, R.layout.list_item, car_types);

        Spinner types = (Spinner) findViewById(R.id.type_spinner);
        types.setAdapter(types_adapter);

        //Listener on type of car, if user selects a type of car the listener will get all the unique years from database and
        // sets the list for year spinner.
        types.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                car_years.setEnabled(true);
                search_button.setEnabled(true);

                selected_type = parent.getItemAtPosition(pos).toString();

                List<Integer> years = db.getallYears(selected_type);
                ArrayAdapter<Integer> years_adapter = new ArrayAdapter<Integer>(Search.this, R.layout.list_item, years);
                car_years.setAdapter(years_adapter);

            }

            //if nothing is selected then all the other spinners will be disabled. It's a spinner so it will never get here but
            // the listener required overloaded function of this as well.
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                car_years.setEnabled(false);
                car_makes.setEnabled(false);
                car_models.setEnabled(false);
                search_button.setEnabled(false);
            }
        });

        //Inspired from: http://stackoverflow.com/questions/2709253/converting-a-string-to-an-integer-on-android
        //Listener on car years, if user selects a year the listener will get all the unique makes/car companies from the
        // database and set the list for make spinner.
        car_years.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView parent, View view, int position, long id) {
                car_makes.setEnabled(true);

                String selected = parent.getItemAtPosition(position).toString();
                selected_year = Integer.valueOf(selected);
                List<String> makes = db.getallMakes(selected_type, selected_year);

                ArrayAdapter<String> makes_adapter = new ArrayAdapter<String>(Search.this, R.layout.list_item, makes);
                car_makes.setAdapter(makes_adapter);
            }

            //if nothing is selected then all the car make and model spinner will be disabled. It's a spinner so it will never
            // get here but the listener required overloaded function of this as well.
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                car_makes.setEnabled(false);
                car_models.setEnabled(false);
            }
        });

        //Listener on car makes, if user selects a make the listener will get all the unique models on that make/company
        // from the database and sets the list for model spinner.
        car_makes.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView parent, View view, int position, long id) {
                car_models.setEnabled(true);
                selected_make = parent.getItemAtPosition(position).toString();

                List<String> models = db.getallModels(selected_type, selected_year, selected_make);

                if (models.size() == 0) {
                    models.add("No Models are avaliable.");
                    search_button.setEnabled(false);
                }

                ArrayAdapter<String> model_adapter = new ArrayAdapter<String>(Search.this, R.layout.list_item, models);
                car_models.setAdapter(model_adapter);
            }

            //if nothing is selected then all the car model spinner will be disabled. It's a spinner so it will never get here
            // but the listener required overloaded function of this as well.
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                car_models.setEnabled(false);
            }
        });

        //Listener on car makes model, if user selects a model then the listener will enable search button.
        car_models.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView parent, View view, int position, long id) {
                String model = parent.getItemAtPosition(position).toString();

                if(model.equals("No Models are avaliable.")){
                    search_button.setEnabled(false);
                } else {
                    search_button.setEnabled(true);
                }
            }
            //Does nothing but the listener required overloaded function of this listener.
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        //Inspired from: http://stackoverflow.com/questions/17963802/android-how-to-send-listview-item-to-another-activity-solved
        //Search button listener which calls Cars class and passes all the selection from above (type, year, make, model) so, cars
        // class can show the result from database using these selected filters.
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_model = (String) car_models.getSelectedItem();

                Intent intent = new Intent(Search.this, Cars.class);
                intent.putExtra("Type", selected_type);
                intent.putExtra("Year", selected_year);
                intent.putExtra("Make", selected_make);
                intent.putExtra("Model", selected_model);

                startActivity(intent);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //Inspired from: https://www.youtube.com/watch?v=4HPWV8DTyE4
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