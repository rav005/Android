package com.example.gaurav.usinggooglemaps;

import android.app.FragmentTransaction;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


//Inspired from: http://zenit.senecac.on.ca/wiki/index.php/MAP524/DPS924_Lecture_9
// AND           http://www.vogella.com/tutorials/AndroidLocationAPI/article.html

public class MainActivity extends ActionBarActivity implements LocationListener, OnMapReadyCallback {

    private TextView latituteField;
    private TextView longitudeField;
    private TextView messageboard;
    private LocationManager locationManager;
    private String provider;
    private MapFragment mapFragment;
    private int longitude;
    private int latitute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        latituteField = (TextView) findViewById(R.id.TextView02);
        longitudeField = (TextView) findViewById(R.id.TextView04);
        messageboard = (TextView) findViewById(R.id.message_board);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);

        if(provider != null){
            messageboard.setText("Provider is: " + provider);
            locationManager.requestLocationUpdates(provider, 400, 1, this);

            mapFragment = MapFragment.newInstance();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.frame_layout, mapFragment);
            fragmentTransaction.commit();

            //mapFragment.getMapAsync(this);

        }else{
            messageboard.setText("Provider is null.");
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /* Request updates at startup */
    @Override
    protected void onResume() {
        super.onResume();

        if(provider != null){
            locationManager.requestLocationUpdates(provider, 400, 1, this);
        }else{
            messageboard.setText("Provider is null.");
        }
    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {

        latitute = (int) (location.getLatitude());
        longitude = (int) (location.getLongitude());

        latituteField.setText(String.valueOf(latitute));
        longitudeField.setText(String.valueOf(longitude));

        GoogleMap googleMap = mapFragment.getMap();
        googleMap.addMarker(new MarkerOptions().position(new LatLng(latitute, longitude)).title("Marker"));

        locationManager.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {



    }
}
