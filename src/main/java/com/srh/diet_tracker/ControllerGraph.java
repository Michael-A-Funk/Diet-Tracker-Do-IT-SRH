package com.srh.diet_tracker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ControllerGraph {

    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    @FXML
    private LineChart<String,Double> lineChart;
    @FXML
    private CheckBox plotAllDays;
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
    @FXML
    private DatePicker olderDatePicker;
    @FXML
    private DatePicker newerDatePicker;
    @FXML
    private Button graphMakeBtn;

    private boolean isRepresentAllTime = true;
    private final int CHART_RESOLUTION = 0;

    public ControllerGraph(){}

    public void onReturnHomepage(ActionEvent actionEvent) {
        SceneManager.getInstance().loadScene(SceneType.HOMEPAGE, "Homepage", 600, 500);
    }

    public void onGraphMakeBtn(ActionEvent actionEvent) {
        EntryDAO entryDAO = new EntryDAO();
        ArrayList<Double> listDataToPlot = new ArrayList<>();
        ArrayList<LocalDate> selectedDaysList = new ArrayList<>();
        boolean isEmpty = false;
        // Checks if time selection is empty, to handle in if condition
        if (!plotAllDays.isSelected()) {
            selectedDaysList = entryDAO.returnRegisteredDates(false, olderDatePicker.getValue(), newerDatePicker.getValue());
            if (selectedDaysList.isEmpty()) {
                isEmpty = true;
            } else {
                isEmpty = false;
            }
            selectedDaysList.clear();
        }

        XYChart.Series series = new XYChart.Series();

        //IF PART FOR SUMS!
        //All Days - Sum of Calories
        if (caloriesRadioBtn.isSelected() && plotAllDays.isSelected() && sumRadioBtn.isSelected()) {
                selectedDaysList = entryDAO.returnRegisteredDates(true, null, null);
                listDataToPlot = entryDAO.returnSUMCaloriesForDateRange(true, null, null);
                yAxis.setLabel("Kalorien (kcal)");
                series.setName("Summe Kalorien aller eingetragenen Zeit");
        }
        //Specific Date Range - Sum of Calories
        else if (caloriesRadioBtn.isSelected() && !plotAllDays.isSelected() && sumRadioBtn.isSelected() &&
                !newerDatePicker.getValue().isBefore(olderDatePicker.getValue()) && !isEmpty) {
                selectedDaysList = entryDAO.returnRegisteredDates(false, olderDatePicker.getValue(), newerDatePicker.getValue());
                listDataToPlot = entryDAO.returnSUMCaloriesForDateRange(false, olderDatePicker.getValue(), newerDatePicker.getValue());
                yAxis.setLabel("Kalorien (kcal)");
                series.setName("Summe Kalorien vom " + olderDatePicker.getValue() + " bis zum " + newerDatePicker.getValue());
            } else if (sugarRadioBtn.isSelected() && !plotAllDays.isSelected() && sumRadioBtn.isSelected() &&
                !newerDatePicker.getValue().isBefore(olderDatePicker.getValue()) && !isEmpty) {
                selectedDaysList = entryDAO.returnRegisteredDates(false, olderDatePicker.getValue(), newerDatePicker.getValue());
                listDataToPlot = entryDAO.returnSUMSugarForDateRange(false, olderDatePicker.getValue(), newerDatePicker.getValue());
                yAxis.setLabel("Zucker (g)");
                series.setName("Summe Zucker vom " + olderDatePicker.getValue() + " bis zum " + newerDatePicker.getValue());
            } else if (sugarRadioBtn.isSelected() && plotAllDays.isSelected() && sumRadioBtn.isSelected() ) {
                selectedDaysList = entryDAO.returnRegisteredDates(true, null, null);
                listDataToPlot = entryDAO.returnSUMSugarForDateRange(true, null, null);
                yAxis.setLabel("Zucker (g)");
                series.setName("Summe Zucker aller eingetragenen Zeit");
                //IF PART FOR PERCENTAGE!
            } else if (plotAllDays.isSelected() && caloriesRadioBtn.isSelected() && percentageRadioBtn.isSelected()) {
                selectedDaysList = entryDAO.returnRegisteredDates(true, null, null);
                listDataToPlot = returnPercentageList(true, true);
                series.setName("Prozent Kalorien (relativ zu Grenzwert) aller eingetragenen Zeit");
            } else if (plotAllDays.isSelected() && sugarRadioBtn.isSelected() && percentageRadioBtn.isSelected()) {
                selectedDaysList = entryDAO.returnRegisteredDates(true, null, null);
                listDataToPlot = returnPercentageList(false, true);
                series.setName("Prozent Zucker (relativ zu Grenzwert) aller eingetragenen Zeit");
            } else if (!plotAllDays.isSelected() && caloriesRadioBtn.isSelected() && percentageRadioBtn.isSelected() &&
                !newerDatePicker.getValue().isBefore(olderDatePicker.getValue()) && !isEmpty) {
                selectedDaysList = entryDAO.returnRegisteredDates(false, olderDatePicker.getValue(), newerDatePicker.getValue());
                listDataToPlot = returnPercentageList(true, false);
                series.setName("Prozent Kalorien (relativ zu Grenzwert) aller von gewählten Zeitraum");
            } else if (!plotAllDays.isSelected() && sugarRadioBtn.isSelected() && percentageRadioBtn.isSelected() &&
                        !newerDatePicker.getValue().isBefore(olderDatePicker.getValue()) && !isEmpty){
                selectedDaysList = entryDAO.returnRegisteredDates(false, olderDatePicker.getValue(), newerDatePicker.getValue());
                listDataToPlot = returnPercentageList(false, false);
                series.setName("Prozent Zucker (relativ zu Grenzwert) aller von gewählten Zeitraum");
            } else if (!plotAllDays.isSelected() && newerDatePicker.getValue().isBefore(olderDatePicker.getValue())){
                series.setName("Anfangsdatum ist früher als Beginnsdatum!");
            } else {
                series.setName("Kein daten in ausgewählten Zeitraum!");
            }


            xAxis.setLabel("Zeit (Tage)");

            xAxis.setTickLabelRotation(45);

            //ArrayList<LocalDate> selectedDaysList= entryDAO.returnRegisteredDates(true,null,null);
            //ArrayList<Double> sumCaloriesList= entryDAO.returnSUMCaloriesForDateRange(true,null,null);
            for (int i = 0; i < selectedDaysList.size(); i++) {
                String day = Integer.toString(selectedDaysList.get(i).getDayOfMonth());
                series.getData().add(new XYChart.Data<>(day, listDataToPlot.get(i)));
            }
            lineChart.getData().clear();
            lineChart.getData().add(series);

        /*else {

        }*/

    }

    ArrayList<Double> returnPercentageList(boolean isCalories, boolean isAllDates) {

        EntryDAO entryDAO = new EntryDAO();
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserData();

        ArrayList<LocalDate> dayList;
        if (isAllDates){
            dayList = entryDAO.returnRegisteredDates(true, null, null);
         } else {
            dayList = entryDAO.returnRegisteredDates(false, olderDatePicker.getValue(), newerDatePicker.getValue());
        }


        ArrayList<Double> percentageList = new ArrayList<>();
        double percentage;
        for (int i = 0 ; i<dayList.size();i++){
            if (isCalories) {
                percentage = Math.floor((entryDAO.returnCaloriesSumByDate(dayList.get(i)) / user.getBMR()) * 100);
            }
            else {
                percentage =  Math.floor(entryDAO.returnSugarSumByDate(dayList.get(i)) / 50 * 100);
            }
            percentageList.add(percentage);
        }
        return percentageList;

    }

    public void onCaloriesRadioBtn(ActionEvent actionEvent) {
        sugarRadioBtn.setSelected(false);
    }

    public void onSugarRadioBtn(ActionEvent actionEvent) {
        caloriesRadioBtn.setSelected(false);
    }

    public void onSumRadioBtn(ActionEvent actionEvent) {
        percentageRadioBtn.setSelected(false);
    }

    public void onPercentageRadioBtn(ActionEvent actionEvent) {
        sumRadioBtn.setSelected(false);
    }

    public void onPlotAllDays(ActionEvent actionEvent) {
        isRepresentAllTime = !isRepresentAllTime;
        olderDatePicker.setDisable(isRepresentAllTime);
        newerDatePicker.setDisable(isRepresentAllTime);
    }
}






