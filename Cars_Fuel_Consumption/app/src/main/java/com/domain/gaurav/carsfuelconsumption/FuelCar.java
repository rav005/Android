package com.domain.gaurav.carsfuelconsumption;

import java.io.Serializable;

/**
 * Created by gaurav on 09/08/15.
 */
//Holds all the information on Fuel car provided by database, implements Serializable so the object can be passed using intents.
//  getters and setters to get and set data.
public class FuelCar implements Serializable {
    private String Table;
    private int id;
    private int Year;
    private String Make;
    private String Model;
    private String Class;
    private float Engine;
    private int Cylinder;
    private String Transmission;
    private String Fuel;
    private float City;
    private float Highway;
    private float Combine;
    private int CO2;

    public FuelCar(String table, int id, int year, String make, String model, String Class, float engine, int cylinder, String transmission, String fuel,
                   float city, float highway, float combine, int co2){
        this.Table = table;
        this.id = id;
        this.Year = year;
        this.Make = make;
        this.Model = model;
        this.Class = Class;
        this.Engine = engine;
        this.Cylinder = cylinder;
        this.Transmission = transmission;
        this.Fuel = fuel;
        this.City = city;
        this.Highway = highway;
        this.Combine = combine;
        this.CO2 = co2;
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
        Make = model;
    }

    public String getclass() {
        return Class;
    }

    public void setclass(String aClass) {
        Class = aClass;
    }

    public String getTable() { return Table; }

    public void setTable(String table) { Table = table; }

    public float getEngine() {
        return Engine;
    }

    public void setEngine(float engine) {
        Engine = engine;
    }

    public int getCylinder() {
        return Cylinder;
    }

    public void setCylinder(int cylinder) {
        Cylinder = cylinder;
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
}
