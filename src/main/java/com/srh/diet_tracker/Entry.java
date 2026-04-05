package com.srh.diet_tracker;

import java.time.LocalDate;
import java.time.LocalTime;

public class Entry {
    private int id;
    private String activity;
    private double calories;
    private double sugar;
    private LocalDate day;
    private LocalTime time;


    public Entry (int id, String activity, double calories, double sugar, LocalDate day, LocalTime time){
        this.activity = activity;
        this.setCalories(calories);
        this.setSugar(sugar);
        this.day = day;
        this.time = time;
        this.id = id;
    }

    // one query (getLastEntry to be used in ControllerEntry) doesnt need id. This querying spares time.
    public Entry (String activity, double calories, double sugar, LocalDate day, LocalTime time){
        this.activity = activity;
        this.setCalories(calories);
        this.setSugar(sugar);
        this.day = day;
        this.time = time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    // "Empty" Constructor is needed in EntryDAO
    public Entry (){}

    public String getActivity () {
        return activity;
    }

    public double getCalories() {return calories;}

     public double getSugar() {
        return sugar;
    }

    public LocalTime getTime() {
        return time;
    }

    public LocalDate getDay() {
        return day;
    }

    public int getId(){return id;}

    public void setId(int id) {
        this.id = id;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public void setSugar(double sugar) {
        //Forciert das bei Sport Zuckereintrag immer 0 ist (kein Zucker eintrag wenn Sport),
        // und bei Essen immer größer oder gleich 0 ist
        if (this.activity.equals("sport")){
            this.sugar = 0;
        } else{
            this.sugar = Math.abs(sugar);
        }

    }
}


