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


    public double getBMR(){
        double bmr;
        if (isMale) {
            bmr= 88.362 + (13.397 * this.weight)+(4.799 * this.height) - (5.677 * this.age);
        }
        else{
            bmr=447.593 + (9.247 * this.weight) + (3.098 * this.height) - (4.330 * this.age);
        }
        return bmr;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public void setHasDiabetes(boolean hasDiabetes) {
        this.hasDiabetes = hasDiabetes;
    }
}
