package com.srh.diet_tracker;


import java.time.LocalTime;

public class TableRow {

    private int entryNr;
    private String activity;
    private double calories;
    private double sugar;
    private LocalTime time;

    public TableRow (int entryNr, String activity, double calories, double sugar, LocalTime time){
        this.entryNr = entryNr;
        this.activity = activity;
        this.calories = calories;
        this.sugar = sugar;
        this.time = time;
    }

    public int getEntryNr() {
        return entryNr;
    }

    public String getActivity() {
        return activity;
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
}
