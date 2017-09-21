package com.example.noblenotebooklouis.challenge3;

/**
 * Created by Noble Notebook Louis on 20-Sep-17.
 * Data class of the sensors of the device while holding it in hand.
 * It contains Accelerometer, Linear Acceleration, Gravity and Magnetism.
 */

public class Data {

    Coordinate a, l, g, m;

    /**
     *  Data object (Accelerometer, Linear Acceleration, Gravity, Magnetic Field).
     *
     */

    public Data(Coordinate accelerometer, Coordinate linearAcceleration, Coordinate gravity, Coordinate magneticField) {
        a = accelerometer;
        l = linearAcceleration;
        g = gravity ;
        m = magneticField;
    }

    public Coordinate getA() {
        return a;
    }

    public Coordinate getL() {
        return l;
    }

    public Coordinate getG() {
        return g;
    }

    public Coordinate getM() {
        return m;
    }
}
