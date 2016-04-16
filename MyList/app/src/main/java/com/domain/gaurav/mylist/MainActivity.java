package com.domain.gaurav.mylist;

import android.content.Context;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;


public class MainActivity extends ActionBarActivity {

    String[] colourNames;
    String[] colorCodes;
    String currentColor;
    int color_pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colourNames = getResources().getStringArray(R.array.listArray);
        colorCodes = getResources().getStringArray(R.array.listValues);

        ListView lv = (ListView) findViewById(R.id.listView);

        ArrayAdapter aa = new ArrayAdapter(this, R.layout.activity_listview, colourNames);
        lv.setAdapter(aa);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();

                //Inspired from: http://stackoverflow.com/questions/8961071/android-changing-background-color-of-the-activity-main-view
                //Inspired from: http://developer.android.com/reference/android/graphics/Color.html
                //Assigning color to main activity background.
                View currentLayout = (View) findViewById(R.id.mainlayout);
                String hex = colorCodes[position].substring(2);
                String hash = "#" + hex;
                currentLayout.setBackgroundColor(Color.parseColor(hash));

                currentColor = colorCodes[position];
                color_pos = position;

            }
        });

        //Creating Menu
        registerForContextMenu(lv);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select The Action");
        menu.add(0, v.getId(), 0, "Write colour to SD Card");
        menu.add(0, v.getId(), 0, "Read colour from SD Card");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //Inspired from: http://developer.android.com/training/basics/data-storage/files.html

        if (item.getTitle() == "Write colour to SD Card") {

            //Writing to file. Inspired from: http://developer.android.com/training/basics/data-storage/files.html
            File dir = Environment.getExternalStorageDirectory();
            File file = new File(dir, "color.txt");

            //PrintWriter writer;
            try {
                FileOutputStream f = new FileOutputStream(file);
                BufferedOutputStream bi = new BufferedOutputStream(f);


                bi.close ();
                f.close();

                //writer = new PrintWriter(file);
                //writer.println(color_pos);
                //writer.print(color_pos);
            //    writer.println(currentColor);
                //writer.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Toast.makeText(getApplicationContext(), "Wrote to SD Card", Toast.LENGTH_LONG).show();

        } else if (item.getTitle() == "Read colour from SD Card") {

            View currentLayout = (View) findViewById(R.id.mainlayout);
            File dir = Environment.getExternalStorageDirectory();
            File file = new File(dir, "color.txt");
            Scanner in;

        //    if (file.exists()) {
                try{
                    in = new Scanner(file);
                    while(in.hasNextByte()){
                        int pos = in.nextInt();
                //        String input = in.next();

                    //    Log.d("DEBUG", pos);

                        if(pos != -1){
                            color_pos = pos;
                        //    currentColor = input;
                            String hex = colorCodes[color_pos].substring(2);
                            String hash = "#" + hex;
                            currentLayout.setBackgroundColor(Color.parseColor(hash));
                        }
                    }
                    in.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
         //   }

            Toast.makeText(getApplicationContext(), "Read from SD Card.", Toast.LENGTH_LONG).show();

        } else {
            return false;
        }
        return true;
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
