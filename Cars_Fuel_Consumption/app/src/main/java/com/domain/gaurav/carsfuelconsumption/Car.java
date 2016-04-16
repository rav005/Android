package com.domain.gaurav.carsfuelconsumption;

import java.io.Serializable;

/**
 * Created by gaurav on 10/08/15.
 */
//Inspired from: http://www.easyinfogeek.com/2014/01/android-tutorial-two-methods-of-passing.html
// AND http://stackoverflow.com/questions/14333449/passing-data-through-intent-using-serializable

//Template class so it can hold any car passed into it and type provides what kind of car was passed in.
public class Car<T> implements Serializable {
    private T car;
    private String type;

    public Car(T obj, String type){
        this.car = obj;
        this.type = type;
    }
    public T getCar() {
        return car;
    }

    public String getType() {
        return type;
    }


}
