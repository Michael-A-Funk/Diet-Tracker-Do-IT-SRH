package com.srh.diet_tracker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;

import java.time.LocalDate;
import java.util.ArrayList;

public class ControllerDayReview {



    //Controls
    @FXML
    Button saveEntryBtn;
    @FXML
    private Spinner<Integer> spinnerEntryNr;
    @FXML
    private RadioButton isSportRadioBtn = new RadioButton();
    @FXML
    private RadioButton isMealRadioBtn = new RadioButton();
    @FXML
    private TextField caloriesTextField = new TextField();
    @FXML
    private TextField sugarTextField = new TextField();
    @FXML
    private Spinner<Integer> hoursSpinner = new Spinner<>();
    @FXML
    private Spinner<Integer> minutesSpinner = new Spinner<>();
    @FXML
    private Spinner<Integer> secondsSpinner = new Spinner<>();;
    @FXML
    private Label sugarPercentageLabel = new Label();
    @FXML
    private Label caloriesPercentageLabel = new Label();
    @FXML
    private DatePicker datePicker = new DatePicker();
    @FXML
    private Label warningLabel = new Label();

    // Columns
    @FXML
    private TableColumn entryNrColumn;
    @FXML
    private TableColumn activityColumn;
    @FXML
    private TableColumn caloriesColumn;
    @FXML
    private TableColumn sugarColumn;
    @FXML
    private TableColumn timeColumn;


    // FRAGE : Müssen die Attribute sein?
    private EntryDAO entryDAO;
    private UserDAO userDAO;


    public ControllerDayReview(){
    }

    public void initialize (){
        spinnerEntryNr.valueProperty().addListener((obs, oldValue, newValue) ->
            {System.out.println("Spinner Method");
                EntryDAO entryDAO = new EntryDAO();
                int entryNr = spinnerEntryNr.getValue();
                LocalDate date = datePicker.getValue();
                ArrayList<Entry> entryList = entryDAO.returnEntriesDay(date);
                ControllerDayReview controllerDayReview = new ControllerDayReview();
                controllerDayReview.setFieldsByEntryNr(newValue-1,entryList, isSportRadioBtn, isMealRadioBtn, caloriesTextField,
                sugarTextField, hoursSpinner, minutesSpinner, secondsSpinner);});
    }


    public void onActionDatePicker(ActionEvent actionEvent) {
        LocalDate date = datePicker.getValue();
        EntryDAO entryDAO = new EntryDAO();
        ControllerDayReview controllerDayReview = new ControllerDayReview();
        ArrayList<Entry> entryList = entryDAO.returnEntriesDay(date);

        if (entryList == null || entryList.isEmpty()){
            warningLabel.setText("Kein Eintrag an gewählten Tag!\nKeine neue Einträge angezeigt.");
        }
        else {
            int entryNr = entryList.size()-1;
            isSportRadioBtn.setSelected(entryList.get(entryNr).isSport());
            isMealRadioBtn.setSelected(!entryList.get(entryNr).isSport());
            String calories = Double.toString(entryList.get(entryNr).getCalories());
            caloriesTextField.setText(calories);
            String sugar = Double.toString(entryList.get(entryNr).getSugar());
            sugarTextField.setText(sugar);

            spinnerEntryNr.setValueFactory(
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(
                            1,
                            entryList.size()
                    )
            );
            spinnerEntryNr.getValueFactory().setValue(entryList.size());
            hoursSpinner.setValueFactory(
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(
                            0,
                            23
                    )
            );
            hoursSpinner.getValueFactory().setValue(entryList.get(entryNr).getTime().getHour());
            minutesSpinner.setValueFactory(
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(
                            0,
                            59
                    )
            );
            minutesSpinner.getValueFactory().setValue(entryList.get(entryNr).getTime().getMinute());
            secondsSpinner.setValueFactory(
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(
                            0,
                            59
                    )
            );
            secondsSpinner.getValueFactory().setValue(entryList.get(entryNr).getTime().getSecond());
        }



        ObservableList<Entry> dayEntryList = FXCollections.observableArrayList();
        //dayEntryList.addAll(entryList);
    }

    public void onIsSportRadioBtn(ActionEvent actionEvent) {
        isMealRadioBtn.setSelected(false);
        //isSport = true;
    }

    public void onIsMealRadioBtn(ActionEvent actionEvent) {
        isMealRadioBtn.setSelected(true);
        //isSport = false;
    }

    public void onSaveEntryBtn(ActionEvent actionEvent) {
    }

    void setFieldsByEntryNr (int entryNr, ArrayList<Entry> entryList, RadioButton isSportRadioBtn,
                             RadioButton isMealRadioBtn, TextField caloriesTextField, TextField sugarTextField,
                             Spinner<Integer> hoursSpinner, Spinner<Integer> minutesSpinner, Spinner<Integer> second ) {

        if (entryList == null ||entryList.isEmpty()) {
            warningLabel.setText("Kein Eintrag an gewählten Tag!\nKeine neue Einträge angezeigt.");
        } else {
            isSportRadioBtn.setSelected(entryList.get(entryNr).isSport());
            isMealRadioBtn.setSelected(!entryList.get(entryNr).isSport());
            String calories = Double.toString(entryList.get(entryNr).getCalories());
            caloriesTextField.setText(calories);
            String sugar = Double.toString(entryList.get(entryNr).getSugar());
            sugarTextField.setText(sugar);
            hoursSpinner.setValueFactory(
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(
                            0,
                            23
                    )
            );
            hoursSpinner.getValueFactory().setValue(entryList.get(entryNr).getTime().getHour());
            minutesSpinner.setValueFactory(
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(
                            0,
                            59
                    )
            );
            minutesSpinner.getValueFactory().setValue(entryList.get(entryNr).getTime().getMinute());
            secondsSpinner.setValueFactory(
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(
                            0,
                            59
                    )
            );
            secondsSpinner.getValueFactory().setValue(entryList.get(entryNr).getTime().getSecond());
        }

    }


    // TEST METHODS

    /*public void showCaloriesSumByDate(){

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
            System.out.println("Zuckegewinn: " + entryList.get(i).getSugar());
            System.out.println("Datum: " + entryList.get(i).getDay());
            System.out.println("Uhrzeit: " + entryList.get(i).getTime());
            System.out.println("----------------------");

        }

    }/*




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
