package com.srh.diet_tracker;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;

public class GraphRepresentation extends App{

    @Override
    public void start(Stage stage) {
        EntryDAO entryDAO = new EntryDAO();
        ArrayList<LocalDate> datesLists = entryDAO.returnRegisteredDates(true,null,null);
        makeGraph(true,true, datesLists.getFirst(),datesLists.getLast(),stage);

    }


    public void makeGraph(boolean isCalories, boolean isSum, LocalDate olderDate, LocalDate newerDate, Stage stage){
        double olderDateDouble = olderDate.getDayOfYear();
        double newerDateDouble = newerDate.getDayOfYear();
        NumberAxis xAxis = new NumberAxis(olderDateDouble, newerDateDouble, 1);
        xAxis.setLabel("Tage");

        EntryDAO entryDAO = new EntryDAO();
        NumberAxis yAxis = new NumberAxis(0,entryDAO.getMaxCalories(),5);
        yAxis.setLabel("Kalorien (kcal)");

        LineChart linechart  = new LineChart(xAxis,yAxis);

        XYChart.Series series = new XYChart.Series();
        series.setName("Summe Kalorien aller eingetragenen Zeit");

        ArrayList<LocalDate> selectedDaysList= entryDAO.returnRegisteredDates(true,null,null);
        ArrayList<Double> sumCaloriesList= entryDAO.returnSUMCaloriesForDateRange(true,null,null);
        for (int i=0; i<selectedDaysList.size();i++){
            int dayOfYear = selectedDaysList.get(i).getDayOfYear();
            series.getData().add(new XYChart.Data<>(dayOfYear,sumCaloriesList.get(i)));
        }

        linechart.getData().add(series);

        Group root = new Group(linechart);

        Scene scene = new Scene (root, 600,400);

        //Setting title to the Stage
        stage.setTitle("Line Chart");

        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();

    }

}






