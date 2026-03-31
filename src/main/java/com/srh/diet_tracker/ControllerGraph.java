package com.srh.diet_tracker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

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
    private final int CHART_RESOLUTION = 0;

    public ControllerGraph(){}

    public void makeGraph(boolean isCalories, boolean isSum, LocalDate olderDate, LocalDate newerDate){


        EntryDAO entryDAO = new EntryDAO();
        xAxis.setLabel("Zeit (Tage)");
        yAxis.setLabel("Kalorien (kcal)");

        XYChart.Series series = new XYChart.Series();
        series.setName("Summe Kalorien aller eingetragenen Zeit");
        xAxis.setTickLabelRotation(45);

        ArrayList<LocalDate> selectedDaysList= entryDAO.returnRegisteredDates(true,null,null);
        ArrayList<Double> sumCaloriesList= entryDAO.returnSUMCaloriesForDateRange(true,null,null);
        for (int i=0; i<selectedDaysList.size();i++){
            String day = Integer.toString(selectedDaysList.get(i).getDayOfMonth());
            series.getData().add(new XYChart.Data<>(day,sumCaloriesList.get(i)));
        }
        lineChart.getData().clear();
        lineChart.getData().add(series);

    }

    public void onReturnHomepage(ActionEvent actionEvent) {
        SceneManager.getInstance().loadScene(SceneType.HOMEPAGE, "Homepage", 900, 600);
    }

    public void onGraphMakeBtn(ActionEvent actionEvent) {
        EntryDAO entryDAO = new EntryDAO();
        ArrayList<Double> caloriesSumList = entryDAO.returnSUMCaloriesForDateRange(true,null,null);
        ArrayList<LocalDate> datesList = entryDAO.returnRegisteredDates(true, null, null);

        makeGraph(true, true, datesList.getFirst(), datesList.getLast());

    }
}






