package com.domain.gaurav.myfriends;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ContactInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        Intent intent = getIntent();
        String tempName = intent.getStringExtra("name");
        String tempEmail = intent.getStringExtra("email");
        String tempPhoneNumber = intent.getStringExtra("phone");

        TextView name = (TextView) findViewById(R.id.textView1);
        TextView email = (TextView) findViewById(R.id.textView2);
        TextView number = (TextView) findViewById(R.id.textView3);

        name.setText("Name: " + tempName);
        email.setText("Email: " + tempEmail);
        number.setText("Phone Number: " + tempPhoneNumber);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_info, menu);
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
