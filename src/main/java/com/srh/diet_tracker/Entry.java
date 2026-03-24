package com.srh.diet_tracker;

import java.time.LocalDate;
import java.time.LocalTime;

public class Entry {
    private boolean isSport;
    private double calories;
    private double sugar;
    private LocalDate day;
    private LocalTime time;

    public Entry (boolean isSport,double calories, double sugar, LocalDate day, LocalTime time){
        this.isSport = isSport;
        this.calories = calories;
        this.sugar = sugar;
        this.day = day;
        this.time = time;
    }

    public boolean isSport() {
        return isSport;
    }

        public double getCalories() {
        return calories;
    }

     public double getSugar() {
        return sugar;
    }

    public LocalTime getTime() {
        return time;
    }

    public LocalDate getDay() {
        return day;
    }
}


