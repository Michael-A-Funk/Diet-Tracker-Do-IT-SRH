package com.srh.diet_tracker;

public class User {
    private int age;
    private int height;
    private int weight;
    boolean isMale;
    boolean hasDiabetes;

    public User(int age, int height, int weight, boolean isMale, boolean hasDiabetes) {
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.isMale = isMale;
        this.hasDiabetes = hasDiabetes;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public boolean isHasDiabetes() {
        return hasDiabetes;
    }

    public void setHasDiabetes(boolean hasDiabetes) {
        this.hasDiabetes = hasDiabetes;
    }
}
