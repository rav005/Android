package com.domain.gaurav.carsfuelconsumption;

import java.io.Serializable;

/**
 * Created by gaurav on 10/08/15.
 */
//Holds all the information on Electric car provided by database, implements Serializable so the object can be passed using intents.
//  getters and setters to get and set data.
public class HybridCar implements Serializable {
    private String table;
    private int id;
    private int Year;
    private String Make;
    private String Model;
    private String Class;
    private int Motor;
    private float Engine;
    private int Cylinder;
    private String Transmission;
    private String Fuel;
    private String Consumption;
    private int CO2;
    private int Range, FullRange;
    private int Rechargetime;
    private String Fuel2;
    private float City;
    private float Highway;
    private float Combine;
    private int co2_emission2;

    public HybridCar(String table, int id, int year, String make, String model, String Class, int motor, float engine, int cylinder,
                       String transmission, String fuel, String consumption, int co2, int range, int fullRange, int rechargetime,
                       String fuel2, float city, float highway, float combine, int co2_emission2){

        this.table = table;
        this.id = id;
        this.Year = year;
        this.Make = make;
        this.Model = model;
        this.Class = Class;
        this.Motor = motor;
        this.Engine = engine;
        this.Cylinder = cylinder;
        this.Transmission = transmission;
        this.Fuel = fuel;
        this.Consumption = consumption;
        this.CO2 = co2;
        this.Range = range;
        this.FullRange = fullRange;
        this.Rechargetime = rechargetime;
        this.Fuel2 = fuel2;
        this.City = city;
        this.Highway = highway;
        this.Combine = combine;
        this.co2_emission2 = co2_emission2;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
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

    public String getConsumption() {
        return Consumption;
    }

    public void setConsumption(String consumption) {
        Consumption = consumption;
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

    public int getFullRange() {
        return FullRange;
    }

    public void setFullRange(int fullRange) {
        FullRange = fullRange;
    }

    public int getRechargetime() {
        return Rechargetime;
    }

    public void setRechargetime(int rechargetime) {
        Rechargetime = rechargetime;
    }

    public String getFuel2() {
        return Fuel2;
    }

    public void setFuel2(String fuel2) {
        Fuel2 = fuel2;
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

    public int getCo2_emission2() {
        return co2_emission2;
    }

    public void setCo2_emission2(int co2_emission2) {
        this.co2_emission2 = co2_emission2;
    }
}
