package com.example.gaurav.client;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    //Inspired from: http://www.grokkingandroid.com/android-tutorial-using-content-providers/
    public void getmyfriends(View v){
        ContentResolver resolver = getContentResolver();
        String URL = "content://com.domain.gaurav.myfriends.friends/myfriends";
        String[] projection = new String[]{"name"};
        Uri uri = Uri.parse(URL);

Log.i("DEBUG", "Worked till Uri is parsed.");
        Cursor c = resolver.query(uri, projection, null, null, null);
Log.i("DEBUG", "Queary is ran.");
        List<String> names =  new ArrayList<String>();

        if(c.moveToFirst()){
            do{
                names.add(c.getString(0));
            }while(c.moveToNext());
        }else{
Log.i("DEBUG", "In else statement.");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);
        ListView list = (ListView)findViewById(R.id.listView);
        list.setAdapter(adapter);
    }
}
