package com.domain.gaurav.androidguide;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;


public class Terminology extends ActionBarActivity {

    AutoCompleteTextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terminology);

        textview();
        printDefination();
    }
    //Inspired by nsbajaj
    private void textview() {
        textview = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView_terminology);
        String name[] = getResources().getStringArray(R.array.name);
        ArrayAdapter<String> adapter = new ArrayAdapter<String> (this,android.R.layout.simple_dropdown_item_1line,name);

        //Binding adapter to textview
        textview.setAdapter(adapter);
    }
    private void printDefination() {
        textview = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView_terminology);
        textview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                search();
            }
        });
    }
    private void search() {
        String[] name = getResources().getStringArray(R.array.name);
        String[] def = getResources().getStringArray(R.array.def);
        String input = textview.getText().toString().trim(); //Grabs the value, selected by the user
        for(int i = 0; i < name.length; i++){
            if(input.equals(name[i])){
                EditText text = (EditText) findViewById(R.id.edit);
                text.setText(def[i]);
                break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_terminology, menu);
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
