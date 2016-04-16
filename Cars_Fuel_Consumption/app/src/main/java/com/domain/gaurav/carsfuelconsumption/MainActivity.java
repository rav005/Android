package com.domain.gaurav.carsfuelconsumption;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public static MyDbHandler db; //static object so, other activities can use this object.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new MyDbHandler(MainActivity.this);

        //Handling search request, opens search activity.
        Button search = (Button)findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Search.class);
                startActivity(intent);
            }
        });

        ////Handling browse fuel cars request, opens browse activity.
        Button browseFuel = (Button)findViewById(R.id.fuel);
        browseFuel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Browse.class);
                intent.putExtra("Table", "Fuel_Cars");
                startActivity(intent);
            }
        });

        //Handling browse hybrid cars request, opens browse activity.
        Button browseHybrid = (Button)findViewById(R.id.hybrid);
        browseHybrid.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Browse.class);
                intent.putExtra("Table", "Hybrid_Cars");
                startActivity(intent);
            }
        });

        //Handling browse Electric cars request, opens browse activity.
        Button browseElectric = (Button)findViewById(R.id.electric);
        browseElectric.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Browse.class);
                intent.putExtra("Table", "Electric_Cars");
                startActivity(intent);
            }
        });

        //Handling Favourite button request, opens browse activity.
        Button favourite = (Button)findViewById(R.id.favourites);
        favourite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Browse.class);
                intent.putExtra("Table", "Favourite_Cars");
                startActivity(intent);
            }
        });

    }

    //Creates option menu.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //Inspired from: https://www.youtube.com/watch?v=4HPWV8DTyE4
    //Handles option menu events.
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
