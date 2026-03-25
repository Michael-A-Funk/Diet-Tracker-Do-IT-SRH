package com.srh.diet_tracker;

public class User {

    private int height;
    private int age;
    private int weight;
    boolean isMale;
    boolean hasDiabetes;

    public User(int height, int age, int weight,  boolean isMale, boolean hasDiabetes) {
        this.height = height;
        this.age = age;
        this.weight = weight;
        this.isMale = isMale;
        this.hasDiabetes = hasDiabetes;
    }


    public int getHeight() {
        return height;
    }

    public int getAge() {
        return age;
    }


    public int getWeight() {
        return weight;
    }


    public boolean isMale() {
        return isMale;
    }


    public boolean hasDiabetes() {
        return hasDiabetes;
    }


    public double bmr(){
        double bmr = 0;
        return bmr;


        // Must query sum calories consumed and burned separately.
        //
    }

}
