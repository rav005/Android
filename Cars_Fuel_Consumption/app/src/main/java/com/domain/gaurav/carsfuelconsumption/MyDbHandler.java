package com.domain.gaurav.carsfuelconsumption;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


//Inspired from: http://stackoverflow.com/questions/10598137/rawqueryquery-selectionargs
//MyDbHandler class deals with inserting or retreving records from SQL Database.
public class MyDbHandler extends SQLiteOpenHelper {
    private static final String DB_Name = "Cars.db";
    private static final String TABLE_Fuel = "Fuel_Cars";
    private static final String TABLE_Hybrid = "Hybrid_Cars";
    private static final String TABLE_Electric = "Electric_Cars";
    private static final String Table_Favourite = "Favourite_Cars";

    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase database;

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Fuel);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Hybrid);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Electric);
        db.execSQL("DROP TABLE IF EXISTS " + Table_Favourite);
        onCreate(db);
    }

    //MyDbHandler constructor calls SQLiteOpenHelper class constructor and calls function to copy databse to phone.
    public MyDbHandler(Context context) {
        super(context, DB_Name, null, DATABASE_VERSION);

        try {
            copyDataBase(context);

            database = this.getWritableDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Inspired from: http://stackoverflow.com/questions/16354154/copy-sqlite-database-from-assets-folder
    //Copies database file to phones local directory if it does not exists.
    private void copyDataBase(Context context) throws IOException {

        InputStream dbInput = context.getAssets().open(DB_Name);

        // Creates databases folder if it does not exist
        String destPath = "/data/data/" + context.getPackageName() + "/databases/";

        File destPathFile =  new File(destPath);
        if (!destPathFile.exists()) {
            destPathFile.mkdirs();

            String outFile = destPath + DB_Name;
            OutputStream dbOutput = new FileOutputStream(outFile);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = dbInput.read(buffer)) > 0) {
                dbOutput.write(buffer, 0, length);
            }

            dbOutput.flush();
            dbOutput.close();
        }

        dbInput.close();
    }

    //Returns all different kind of cars held in application, used for search activity.
    public String[] getallTypes() {
        String[] temp = {"Fuel intake", "Hybrid", "Electrical"};
        return temp;
    }

    //Returns unique years from table provided in parameter.
    public List<Integer> getallYears(String type) {

        List<Integer> years = new ArrayList<Integer>();
        Cursor cursor;

        if(type == "Fuel intake"){
            cursor = database.rawQuery("SELECT DISTINCT Year from Fuel_Cars where ? <> ?", new String[]{"id", "0"});
        }else if(type == "Hybrid"){
            cursor = database.rawQuery("SELECT DISTINCT Year from Hybrid_Cars where ? <> ?", new String[]{"id", "0"});
        }else{
            cursor = database.rawQuery("SELECT DISTINCT Year from Electric_Cars where ? <> ?", new String[]{"id", "0"});
        }

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int value = cursor.getInt(0);
            years.add(value);
            cursor.moveToNext();
        }

        // make sure to close the cursor
        cursor.close();
        return years;
    }

    //Returns unique car makes/companies stored in provided table whose year matches by the provided year.
    public List<String> getallMakes(String selected_type, int selected_year){
        List<String> makes = new ArrayList<String>();
        Cursor cursor;

        if(selected_type == "Fuel intake"){
            cursor = database.rawQuery("SELECT DISTINCT Make from Fuel_Cars where year = " + selected_year, null);
        }else if(selected_type == "Hybrid"){
            cursor = database.rawQuery("SELECT DISTINCT Make from Hybrid_Cars where year = " + selected_year, null);
        }else{
            cursor = database.rawQuery("SELECT DISTINCT Make from Electric_Cars where year = " + selected_year, null);
        }

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String value = cursor.getString(0);
            makes.add(value);
            cursor.moveToNext();
        }

        // make sure to close the cursor
        cursor.close();
        return makes;
    }

    //Inspired from: http://stackoverflow.com/questions/10600670/sqlitedatabase-query-method
    //Retuns unique models from provided table which are filtered by the provided year and make.
    public List<String> getallModels(String selected_type, int selected_year, String selected_make){
        List<String> models = new ArrayList<String>();
        Cursor cursor;
        String year = String.valueOf(selected_year);

        String[] whereargs = new String[]{year, selected_make};

        if(selected_type == "Fuel intake") {
            cursor = database.rawQuery("Select DISTINCT Model from Fuel_Cars where year = ? AND make = ?", whereargs);
        }else if(selected_type == "Hybrid"){
            cursor = database.rawQuery("Select DISTINCT Model from Hybrid_Cars where year = ? AND make = ?", whereargs);
        }else{
            cursor = database.rawQuery("Select DISTINCT Model from Electric_Cars where year = ? AND make = ?", whereargs);
        }

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String value = cursor.getString(0);
            models.add(value);
            cursor.moveToNext();
        }

        // make sure to close the cursor
        cursor.close();
        return models;
    }

    //Returns list of cars from provided table which matches provided year, make, and model. Also it orders by a column which is proved.
    //Car is a template object so it can return FuelCar, ElectricCar, or HybridCar.
    public List<Car> findCars(String selected_type, int selected_year, String selected_make, String selected_model, String orderBy){
        List<Car> result = new ArrayList<Car>();
        Cursor cursor;
        String year = String.valueOf(selected_year);
        String[] whereargs = new String[]{year, selected_make, selected_model, orderBy};

        //Common car variables.
        String table;
        int id;
        int Year;
        String Make;
        String Model;
        String Class;
        float Engine;
        int Cylinder;
        String Transmission;
        String Fuel;
        float City;
        float Highway;
        float Combine;
        int CO2;

        if(selected_type.equals("Fuel intake")) {

            table = "Fuel_Cars";
            cursor = database.rawQuery("Select * from Fuel_Cars where year = ? AND make = ? AND model = ? ORDER BY ?", whereargs);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                id = cursor.getInt(0);
                Year = cursor.getInt(1);
                Make = cursor.getString(2);
                Model = cursor.getString(3);
                Class = cursor.getString(4);
                Engine = cursor.getFloat(5);
                Cylinder = cursor.getInt(6);
                Transmission = cursor.getString(7);
                Fuel = cursor.getString(8);
                City = cursor.getFloat(9);
                Highway = cursor.getFloat(10);
                Combine = cursor.getFloat(11);
                CO2 = cursor.getInt(12);

                FuelCar fuelcar = new FuelCar(table, id, Year, Make, Model, Class, Engine, Cylinder, Transmission, Fuel, City, Highway, Combine, CO2);
                Car car = new Car(fuelcar, table);
                result.add(car);
                cursor.moveToNext();
            }

        }else if(selected_type.equals("Hybrid")){
            //Specific variables for Hybrid Cars
            int motor;
            String Consumption;
            int co2_emission2;
            int range, fullRange;
            int rechargetime;
            String Fuel2;

            table = "Hybrid_Cars";
            cursor = database.rawQuery("Select * from Hybrid_Cars where year = ? AND make = ? AND model = ? ORDER BY ?", whereargs);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                id = cursor.getInt(0);
                Year = cursor.getInt(1);
                Make = cursor.getString(2);
                Model = cursor.getString(3);
                Class = cursor.getString(4);
                motor = cursor.getInt(5);
                Engine = cursor.getFloat(6);
                Cylinder = cursor.getInt(7);
                Transmission = cursor.getString(8);
                Fuel = cursor.getString(9);
                Consumption = cursor.getString(10);
                CO2 = cursor.getInt(11);
                range = cursor.getInt(12);
                rechargetime = cursor.getInt(13);
                Fuel2 = cursor.getString(14);
                City = cursor.getFloat(15);
                Highway = cursor.getFloat(16);
                Combine = cursor.getFloat(17);
                co2_emission2 = cursor.getInt(18);
                fullRange = cursor.getInt(19);

                HybridCar hybridCar = new HybridCar(table, id, Year, Make, Model, Class, motor, Engine, Cylinder, Transmission,
                        Fuel, Consumption, CO2, range, fullRange, rechargetime, Fuel2, City, Highway, Combine, co2_emission2);
                Car car = new Car(hybridCar, table);
                result.add(car);
                cursor.moveToNext();
            }

        }else{
            int motor, Range, Rechargetime;
            float City_kwh, Highway_kwh, Combine_kwh;

            table = "Electric_Cars";
            cursor = database.rawQuery("Select * from Electric_Cars where year = ? AND make = ? AND model = ? ORDER BY ?", whereargs);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                id = cursor.getInt(0);
                Year = cursor.getInt(1);
                Make = cursor.getString(2);
                Model = cursor.getString(3);
                Class = cursor.getString(4);
                motor = cursor.getInt(5);
                Transmission = cursor.getString(6);
                Fuel = cursor.getString(7);
                City_kwh = cursor.getFloat(8);
                Highway_kwh = cursor.getFloat(9);
                Combine_kwh = cursor.getFloat(10);
                City = cursor.getFloat(11);
                Highway = cursor.getFloat(12);
                Combine = cursor.getFloat(13);
                CO2 = cursor.getInt(14);
                Range = cursor.getInt(15);
                Rechargetime = cursor.getInt(16);

                ElectricCar electricCar = new ElectricCar(table, id, Year, Make, Model, Class, motor, Transmission, Fuel, City_kwh,
                        Highway_kwh, Combine_kwh, City, Highway, Combine, CO2, Range, Rechargetime);
                Car car = new Car(electricCar, table);
                result.add(car);
                cursor.moveToNext();
            }
        }


        // make sure to close the cursor
        cursor.close();
        return result;
    }

    //Returns all the cars from provided table ordered by the column provided.
    //Car is a template object so it can return FuelCar, ElectricCar, or HybridCar.
    public List<Car> getallCarsfromTable(String table, String orderBy){
        List<Car> result = new ArrayList<Car>();
        Cursor cursor;

        //Common car variables.
        int id;
        int Year;
        String Make;
        String Model;
        String Class;
        float Engine;
        int Cylinder;
        String Transmission;
        String Fuel;
        float City;
        float Highway;
        float Combine;
        int CO2;

        if(table.equals("Fuel_Cars")) {
            cursor = database.rawQuery("Select * from Fuel_Cars ORDER BY " + orderBy, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                id = cursor.getInt(0);
                Year = cursor.getInt(1);
                Make = cursor.getString(2);
                Model = cursor.getString(3);
                Class = cursor.getString(4);
                Engine = cursor.getFloat(5);
                Cylinder = cursor.getInt(6);
                Transmission = cursor.getString(7);
                Fuel = cursor.getString(8);
                City = cursor.getFloat(9);
                Highway = cursor.getFloat(10);
                Combine = cursor.getFloat(11);
                CO2 = cursor.getInt(12);

                FuelCar fuelcar = new FuelCar(table, id, Year, Make, Model, Class, Engine, Cylinder, Transmission, Fuel, City, Highway, Combine, CO2);
                Car car = new Car(fuelcar, table);
                result.add(car);
                cursor.moveToNext();
            }

            cursor.close();

        }else if(table.equals("Hybrid_Cars")){
            //Specific variables for Hybrid Cars
            int motor;
            String Consumption;
            int co2_emission2;
            int range, fullRange;
            int rechargetime;
            String Fuel2;

            cursor = database.rawQuery("Select * from Hybrid_Cars ORDER BY " + orderBy, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                id = cursor.getInt(0);
                Year = cursor.getInt(1);
                Make = cursor.getString(2);
                Model = cursor.getString(3);
                Class = cursor.getString(4);
                motor = cursor.getInt(5);
                Engine = cursor.getFloat(6);
                Cylinder = cursor.getInt(7);
                Transmission = cursor.getString(8);
                Fuel = cursor.getString(9);
                Consumption = cursor.getString(10);
                CO2 = cursor.getInt(11);
                range = cursor.getInt(12);
                rechargetime = cursor.getInt(13);
                Fuel2 = cursor.getString(14);
                City = cursor.getFloat(15);
                Highway = cursor.getFloat(16);
                Combine = cursor.getFloat(17);
                co2_emission2 = cursor.getInt(18);
                fullRange = cursor.getInt(19);

                HybridCar hybridCar = new HybridCar(table, id, Year, Make, Model, Class, motor, Engine, Cylinder, Transmission,
                        Fuel, Consumption, CO2, range, fullRange, rechargetime, Fuel2, City, Highway, Combine, co2_emission2);
                Car car = new Car(hybridCar, table);
                result.add(car);
                cursor.moveToNext();
            }

        }else if(table.equals("Electric_Cars")){
            int motor, Range, Rechargetime;
            float City_kwh, Highway_kwh, Combine_kwh;

            cursor = database.rawQuery("Select * from Electric_Cars ORDER BY " + orderBy, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                id = cursor.getInt(0);
                Year = cursor.getInt(1);
                Make = cursor.getString(2);
                Model = cursor.getString(3);
                Class = cursor.getString(4);
                motor = cursor.getInt(5);
                Transmission = cursor.getString(6);
                Fuel = cursor.getString(7);
                City_kwh = cursor.getFloat(8);
                Highway_kwh = cursor.getFloat(9);
                Combine_kwh = cursor.getFloat(10);
                City = cursor.getFloat(11);
                Highway = cursor.getFloat(12);
                Combine = cursor.getFloat(13);
                CO2 = cursor.getInt(14);
                Range = cursor.getInt(15);
                Rechargetime = cursor.getInt(16);

                ElectricCar electricCar = new ElectricCar(table, id, Year, Make, Model, Class, motor, Transmission, Fuel, City_kwh,
                        Highway_kwh, Combine_kwh, City, Highway, Combine, CO2, Range, Rechargetime);
                Car car = new Car(electricCar, table);
                result.add(car);
                cursor.moveToNext();
            }

            cursor.close();
        }else if(table.equals("Favourite_Cars")){
            cursor = database.rawQuery("Select * from Favourite_Cars", null);
            int Id;
            String search_table;
            int size = cursor.getCount();

            if(size > 0) {
                cursor.moveToFirst();

                do {
                    search_table = cursor.getString(1);
                    Id = cursor.getInt(2);
                    Car car = getCarbyId(search_table, Id);
                    result.add(car);
                 } while (cursor.moveToNext());
            }

            cursor.close();
        }

        return result;
    }

    //returns a car object from provided table whose id matches by provided one. Used for getting car object from Favourites table.
    //Car is a template object so it can return FuelCar, ElectricCar, or HybridCar.
    private Car getCarbyId(String table, int id){
        Car c;
        Cursor cursor;

        //Common car variables.
        int newid;
        int Year;
        String Make;
        String Model;
        String Class;
        float Engine;
        int Cylinder;
        String Transmission;
        String Fuel;
        float City;
        float Highway;
        float Combine;
        int CO2;
        if(table.equals("Fuel_Cars")){
            cursor = database.rawQuery("Select * from Fuel_Cars where id = ' " + id + " ' ", null);

            cursor.moveToFirst();

            newid = cursor.getInt(0);
            Year = cursor.getInt(1);
            Make = cursor.getString(2);
            Model = cursor.getString(3);
            Class = cursor.getString(4);
            Engine = cursor.getFloat(5);
            Cylinder = cursor.getInt(6);
            Transmission = cursor.getString(7);
            Fuel = cursor.getString(8);
            City = cursor.getFloat(9);
            Highway = cursor.getFloat(10);
            Combine = cursor.getFloat(11);
            CO2 = cursor.getInt(12);

            FuelCar fuelcar = new FuelCar(table, cursor.getInt(0), cursor.getInt(1), Make, Model, Class, Engine, Cylinder, Transmission, Fuel, City,
                    Highway, Combine, CO2);
            c = new Car(fuelcar, table);

        }else if(table.equals("Hybrid_Cars")){
            cursor = database.rawQuery("Select * from Hybrid_Cars where id = ' " + id + " ' ", null);
            cursor.moveToFirst();

            id = cursor.getInt(0);
            Year = cursor.getInt(1);
            Make = cursor.getString(2);
            Model = cursor.getString(3);
            Class = cursor.getString(4);
            int motor = cursor.getInt(5);
            Engine = cursor.getFloat(6);
            Cylinder = cursor.getInt(7);
            Transmission = cursor.getString(8);
            Fuel = cursor.getString(9);
            String Consumption = cursor.getString(10);
            CO2 = cursor.getInt(11);
            int range = cursor.getInt(12);
            int rechargetime = cursor.getInt(13);
            String Fuel2 = cursor.getString(14);
            City = cursor.getFloat(15);
            Highway = cursor.getFloat(16);
            Combine = cursor.getFloat(17);
            int  co2_emission2 = cursor.getInt(18);
            int fullRange = cursor.getInt(19);

            HybridCar hybridCar = new HybridCar(table, id, Year, Make, Model, Class, motor, Engine, Cylinder, Transmission,
                    Fuel, Consumption, CO2, range, fullRange, rechargetime, Fuel2, City, Highway, Combine, co2_emission2);
            c = new Car(hybridCar, table);

        }else {
            cursor = database.rawQuery("Select * from Electric_Cars where id = ' " + id + " ' ", null);
            cursor.moveToFirst();

            id = cursor.getInt(0);
            Year = cursor.getInt(1);
            Make = cursor.getString(2);
            Model = cursor.getString(3);
            Class = cursor.getString(4);
            int motor = cursor.getInt(5);
            Transmission = cursor.getString(6);
            Fuel = cursor.getString(7);
            float City_kwh = cursor.getFloat(8);
            float Highway_kwh = cursor.getFloat(9);
            float Combine_kwh = cursor.getFloat(10);
            City = cursor.getFloat(11);
            Highway = cursor.getFloat(12);
            Combine = cursor.getFloat(13);
            CO2 = cursor.getInt(14);
            int Range = cursor.getInt(15);
            int Rechargetime = cursor.getInt(16);

            ElectricCar electricCar = new ElectricCar(table, id, Year, Make, Model, Class, motor, Transmission, Fuel, City_kwh,
                    Highway_kwh, Combine_kwh, City, Highway, Combine, CO2, Range, Rechargetime);
            c = new Car(electricCar, table);
        }

        cursor.close();
        return c;
    }

    //Inspired from: http://stackoverflow.com/questions/2616130/android-insert-into-sqlite-database
    //Adds provided data to Favourites table, provided table and ID is used for which table does this record come for and
    //      whats the ID of that record.
    public void addRowToFavourite(String table, int ID){

        ContentValues values = new ContentValues();
        values.put("Table_Name", table);
        values.put("Row_ID", ID);
        database.insert("Favourite_Cars", null, values);
    }

    //Checks if provided data(Car) is already added in the Favourite table or not,
    public boolean checkIfExists(String table, int ID){
        Cursor c = database.rawQuery("select * from Favourite_Cars where Table_Name = '" + table + "' AND Row_ID = " + ID, null);

        if(c.getCount() > 0){
            return true;
        } else{
            return false;
        }
    }
}
