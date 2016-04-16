//Inspired from: http://stackoverflow.com/questions/11499574/toggle-button-using-two-image-on-different-state
package com.domain.gaurav.fragmentexample;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity {

    boolean replace = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        FragmentOne fragOne = new FragmentOne();
        FragmentTwo fragTwo = new FragmentTwo();

        transaction.add(R.id.fragView, fragOne, "Fragment1");
        transaction.add(R.id.fragView, fragTwo, "Fragment2");

    //    transaction.commit();

        Button myButton = (Button)findViewById(R.id.textView);

        myButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //reference to the activity fragment manager
                ImageView iv = (ImageView)findViewById(R.id.imageView2);
                ImageView iv2 = (ImageView)findViewById(R.id.imageView);


                if (replace == false) {
                //    transaction.add(R.id.fragView, One, "Frag1");
                 //   transaction.add(R.id.fragView, Two, "Frag2");
                    iv.setImageResource(R.drawable.car);
                    iv2.setImageResource(R.drawable.car2);

                    replace = true;
                } else {
                //    transaction.add(R.id.fragView, Two, "Frag2");
                //    transaction.add(R.id.fragView, One, "Frag1");
                    iv.setImageResource(R.drawable.car2);
                    iv2.setImageResource(R.drawable.car);

                    replace = false;
                }

            }

        });
        transaction.commit();
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
}
