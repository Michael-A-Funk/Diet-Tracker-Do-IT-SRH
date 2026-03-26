package com.srh.diet_tracker;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class ControllerDayReview {

    private EntryDAO entryDAO;
    private UserDAO userDAO;



    public ControllerDayReview(){}

    public void showCaloriesSumByDate(){

        // Actual Date only used for test purposes
        LocalDate date;
        date = LocalDate.now();

        EntryDAO entryDAO = new EntryDAO();
        System.out.println("Calories Sum for asked day: " + entryDAO.returnCaloriesSumByDate(date));
    }

    public void showSugarSumByDate(){

        // Actual Date only used for test purposes
        LocalDate date;
        date = LocalDate.now();

        EntryDAO entryDAO = new EntryDAO();
        System.out.println("Sugar Sum for asked day: "+ entryDAO.returnSugarSumByDate(date));
    }

    public void showTotalCaloriesSum(){

        EntryDAO entryDAO = new EntryDAO();
        System.out.println("Total Calories Sum: " + entryDAO.returnCaloriesTotalSum());
    }

    public void showTotalSugarSum(){

        EntryDAO entryDAO = new EntryDAO();
        System.out.println("Total Sugar Sum: "+ entryDAO.returnSugarTotalSum());
    }

    public void showCaloriesMean(){
        EntryDAO entryDAO = new EntryDAO();
        System.out.println("Calories Mean: "+ entryDAO.returnCaloriesTotalSum()/entryDAO.returnNumberOfDays());
    }

    public void showSugarMean(){
        EntryDAO entryDAO = new EntryDAO();
        System.out.println("Sugar Mean: "+ entryDAO.returnSugarTotalSum()/entryDAO.returnNumberOfDays());
    }

    public void representData() {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserData();
        System.out.println("Height " + user.getHeight() + "cm " + ", Age: " + user.getAge() + ", Weight: " + user.getWeight() +
                "kg" + ", Male:" + user.isMale() + ",Has Diabetes: " + user.hasDiabetes());

        // Actual Date only used for test purposes
        LocalDate date;
        date = LocalDate.now();

        EntryDAO entryDAO = new EntryDAO();

        double percentageCalories = (entryDAO.returnCaloriesSumByDate(date) / user.getBMR()) * 100;
        double percentageSugar = (entryDAO.returnCaloriesSumByDate(date) / 50) * 100;
        System.out.println("Du hast " + percentageCalories + " % von deinen Maximalen Tageskaloriengehalt erreicht.");
        System.out.println("Du hast " + percentageSugar + " % von deinen Maximalen Tageszuckergehalt erreicht.");

        ArrayList<Entry> entryList = entryDAO.returnEntriesDay(date);
        for (int i = 0; i < entryList.size(); i++) {
            System.out.println(i + "-Eintrag :");
            System.out.println("Sport: " + entryList.get(i).isSport());
            System.out.println("Kaloriengewinn: " + entryList.get(i).getCalories());
            System.out.println("Zuckegewinn " + entryList.get(i).getSugar());
            System.out.println("----------------------");

        }

    }

    //


    // Here will be, using UserDAO and EntryDAO represented and calculated things like:
    // - the Entries (represented)
    // - the maximum of Calories and Sugar of the day (represented)
    // - Sum of Calories and Sugar of the day (calculated)
    // - percentage achieved of Calories and Sugar of the day, relative to max calories/sugar (calculated)

    // waits for event from Button "Löschen".
    /*public void eliminateLastEntry(int id){
        EntryDAO entryDAO = new EntryDAO();
        entryDAO.deleteEntry(id);
        /*int lastId = entryDAO.getLastId();
        System.out.println("Resultat von getLastId" + )
        System.out.println("Letzter Eintrag wurde gelöscht");

    }*/

}
