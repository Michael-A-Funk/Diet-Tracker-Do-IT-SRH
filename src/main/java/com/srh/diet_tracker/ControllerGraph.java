package com.srh.diet_tracker;

import java.time.LocalDate;
import java.util.ArrayList;

public class ControllerGraph {

    //First we have to use method returnRegisteredDays. The values of this array, will be used in a for cycle
    // to get corresponding sum of calories of each day, for further representation in Graph.

    // Here will be, using UserDAO and EntryDAO represented and calculated things like:
    // - Maximum of Calories and Sugar of the day (represented as line in graph)
    // - percentage achieved of Calories and Sugar of the day, relative to max calories/sugar, as graph (calculated)


    public void representData() {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserData();
        EntryDAO entryDAO = new EntryDAO();
        LocalDate forAllDates = LocalDate.parse("2000-12-01");
        LocalDate olderDate = LocalDate.parse("2026-03-24");
        LocalDate newerDate = LocalDate.parse("2026-03-25");
        ArrayList<LocalDate> dateList = entryDAO.returnRegisteredDates(false, newerDate, olderDate);

        for (int i=0;i<dateList.size();i++){
            System.out.println("Datum: " + dateList.get(i) + ", Summe Kalorien: " +
                                + entryDAO.returnCaloriesSumByDate(dateList.get(i)) + ", Prozent von Max Kalorien:" +
                                + ((entryDAO.returnCaloriesSumByDate(dateList.get(i)) / user.getBMR()) * 100) +
                                + entryDAO.returnCaloriesSumByDate(dateList.get(i)) + ", Summe Zucker: " +
                                + entryDAO.returnSugarSumByDate(dateList.get(i)) + ", Prozent von Max Zucker: " +
                                + (entryDAO.returnSugarSumByDate(dateList.get(i)) / 50) * 100);
        }
    }
}
