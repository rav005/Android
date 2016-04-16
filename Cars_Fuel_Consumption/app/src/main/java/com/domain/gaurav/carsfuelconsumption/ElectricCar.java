package com.domain.gaurav.carsfuelconsumption;

import java.io.Serializable;

/**
 * Created by gaurav on 10/08/15.
 */
//Holds all the information on Electric car provided by database, implements Serializable so the object can be passed using intents.
//  getters and setters to get and set data.
public class ElectricCar implements Serializable {
    private String Table;
    private int id;
    private int Year;
    private String Make;
    private String Model;
    private String Class;
    private int Motor;
    private String Transmission;
    private String Fuel;
    private float City_kwh;
    private float Highway_kwh;
    private float Combine_kwh;
    private float City;
    private float Highway;
    private float Combine;
    private int CO2;
    private int Range;
    private int Recharge_time;

    public ElectricCar(String table, int id, int Year, String Make, String Model, String Class, int motor, String Transmission,
                       String  Fuel, float City_kwh, float Highway_kwh, float Combine_kwh, float City, float Highway,float Combine,
                       int CO2, int Range, int Rechargetime){
        this.Table = table;
        this.id = id;
        this.Year = Year;
        this.Make = Make;
        this.Model = Model;
        this.Class = Class;
        this.Motor = motor;
        this.Transmission = Transmission;
        this.Fuel = Fuel;
        this.City_kwh = City_kwh;
        this.Highway_kwh = Highway_kwh;
        this.Combine_kwh = Combine_kwh;
        this.City = City;
        this.Highway = Highway;
        this.Combine = Combine;
        this.CO2 = CO2;
        this.Range = Range;
        this.Recharge_time = Rechargetime;
    }

    public String getTable() {
        return Table;
    }

    public void setTable(String table) {
        Table = table;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }

    public String getMake() {
        return Make;
    }

    public void setMake(String make) {
        Make = make;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }


    public String getclass() {
        return Class;
    }

    public void setclass(String aClass) {
        Class = aClass;
    }

    public int getMotor() {
        return Motor;
    }

    public void setMotor(int motor) {
        Motor = motor;
    }

    public String getTransmission() {
        return Transmission;
    }

    public void setTransmission(String transmission) {
        Transmission = transmission;
    }

    public String getFuel() {
        return Fuel;
    }

    public void setFuel(String fuel) {
        Fuel = fuel;
    }

    public float getCity_kwh() {
        return City_kwh;
    }

    public void setCity_kwh(float city_kwh) {
        City_kwh = city_kwh;
    }

    public float getHighway_kwh() {
        return Highway_kwh;
    }

    public void setHighway_kwh(float highway_kwh) {
        Highway_kwh = highway_kwh;
    }

    public float getCombine_kwh() {
        return Combine_kwh;
    }

    public void setCombine_kwh(float combine_kwh) {
        Combine_kwh = combine_kwh;
    }

    public float getCity() {
        return City;
    }

    public void setCity(float city) {
        City = city;
    }

    public float getHighway() {
        return Highway;
    }

    public void setHighway(float highway) {
        Highway = highway;
    }

    public float getCombine() {
        return Combine;
    }

    public void setCombine(float combine) {
        Combine = combine;
    }

    public int getCO2() {
        return CO2;
    }

    public void setCO2(int CO2) {
        this.CO2 = CO2;
    }

    public int getRange() {
        return Range;
    }

    public void setRange(int range) {
        Range = range;
    }

    public int getRecharge_time() {
        return Recharge_time;
    }

    public void setRecharge_time(int recharge_time) {
        Recharge_time = recharge_time;
    }
}
