package com.srh.diet_tracker;

import java.text.DateFormat;

public class Entry {
    private boolean isSport;
    private double calories;
    private double sugar;
    private DateFormat time;

    public Entry (boolean isSport,double calories, double sugar){
        this.isSport = isSport;
        this.calories = calories;
        this.sugar = sugar;
    }

    public boolean isSport() {
        return isSport;
    }

    public void setSport(boolean sport) {
        isSport = sport;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getSugar() {
        return sugar;
    }

    public void setSugar(double sugar) {
        this.sugar = sugar;
    }

    public DateFormat getTime() {
        return time;
    }

    /*public void setTime() {

    }*/
}


