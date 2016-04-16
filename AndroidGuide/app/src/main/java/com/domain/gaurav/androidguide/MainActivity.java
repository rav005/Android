//Inspiration from: http://developer.android.com/guide/topics/ui/menus.html
//Inspiration from: http://stackoverflow.com/questions/14207392/android-button-click-go-to-another-xml-page
package com.domain.gaurav.androidguide;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b1 = (Button) findViewById(R.id.Terminology);

        final Intent myIntent = new Intent(this, Terminology.class);

        b1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startActivity(myIntent);
            }
        });
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

        switch (item.getItemId())
        {
            case R.id.action_help:
                //do something..
                Intent help = new Intent(MainActivity.this, help_activity.class);
                startActivity(help);
                return true;
            case R.id.action_about:
                //do something..
                Intent about = new Intent(MainActivity.this, about_activity.class);
                startActivity(about);
                return true;
          /*  case R.id.Terminology:
                Intent i = new Intent(MainActivity.this, Terminology.class);
                i.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                startActivity(i);
            */
            default:
                return super.onOptionsItemSelected(item);
        }//END of SWITCH statement
    }
}
