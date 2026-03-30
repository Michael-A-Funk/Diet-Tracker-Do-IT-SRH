package com.srh.diet_tracker;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;

import java.time.LocalDate;
import java.util.ArrayList;

public class ControllerGraph {
    @FXML
    private LineChart<Integer,String> graph;
    @FXML
    private DatePicker olderDatePicker;
    @FXML
    private DatePicker newerDatePicker;
    @FXML
    private RadioButton caloriesRadioBtn;
    @FXML
    private RadioButton sugarRadioBtn;
    @FXML
    private CheckBox meanCheckBox;
    @FXML
    private RadioButton sumRadioBtn;
    @FXML
    private RadioButton percentageRadioBtn;

    //First we have to use method returnRegisteredDays. The values of this array, will be used in a for cycle
    // to get corresponding sum of calories of each day, for further representation in Graph.

    // Here will be, using UserDAO and EntryDAO represented and calculated things like:
    // - Maximum of Calories and Sugar of the day (represented as line in graph)
    // - percentage achieved of Calories and Sugar of the day, relative to max calories/sugar, as graph (calculated)


    public void setGraph(){
    }

    public void initialize(){
        EntryDAO entryDAO = new EntryDAO();
        ArrayList<LocalDate> datesLists = new ArrayList<>();
        makeGraph(true,true, datesLists.getFirst(),datesLists.getLast());
    }


    public void makeGraph(boolean isCalories,boolean isSum,LocalDate olderDate, LocalDate newerDate){
        double olderDateDouble = olderDate.getDayOfYear();
        double newerDateDouble = newerDate.getDayOfYear();
        NumberAxis xAxis = new NumberAxis(olderDateDouble, newerDateDouble, 1);

    }

    // TEST methods

    /*public void representData() {
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
    }*/
}
